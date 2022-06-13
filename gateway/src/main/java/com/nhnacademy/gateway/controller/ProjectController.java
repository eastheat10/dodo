package com.nhnacademy.gateway.controller;

import com.nhnacademy.gateway.dto.request.project.AddProjectMemberRequest;
import com.nhnacademy.gateway.dto.request.project.CreateProjectRequest;
import com.nhnacademy.gateway.dto.response.project.ProjectResponse;
import com.nhnacademy.gateway.dto.response.task.TaskListResponse;
import com.nhnacademy.gateway.dto.response.user.UserResponse;
import com.nhnacademy.gateway.service.task.TaskService;
import com.nhnacademy.gateway.service.user.UserService;
import com.nhnacademy.gateway.service.task.ProjectService;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final RedisTemplate<String, String> redisTemplate;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/list")
    public ModelAndView userProject(HttpSession httpSession) {

        ModelAndView mav = new ModelAndView("project/list");

        String username = (String) redisTemplate.opsForHash().get(httpSession.getId(), "username");

        log.info("session username = {}", username);

        List<ProjectResponse> projects = projectService.findProjectByUsername(username);

        mav.addObject("projects", projects);

        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView project(@PathVariable Long id) {

        ModelAndView mav = new ModelAndView("project/project");

        ProjectResponse project = projectService.findProject(id);
        List<TaskListResponse> tasks = taskService.findTaskList(id);

        mav.addObject("project", project);
        mav.addObject("tasks", tasks);

        return mav;
    }

    @GetMapping("/create")
    public ModelAndView projectForm(CreateProjectRequest createRequest) {
        return new ModelAndView("project/project-form");
    }

    @GetMapping("/{id}/members")
    public ModelAndView addMembers() {

        ModelAndView mav = new ModelAndView("project/add-member-form");

        List<UserResponse> users = userService.findUsers();

        mav.addObject("users", users);

        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createProject(@ModelAttribute @Valid CreateProjectRequest createRequest,
                                      BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("project/project-form");
        }

        String id = (String) redisTemplate.opsForHash().get(session.getId(), "id");
        String username = (String) redisTemplate.opsForHash().get(session.getId(), "username");

        createRequest.setAdminId(Long.valueOf(id));
        createRequest.setAdminUsername(username);

        projectService.createProject(createRequest);

        return new ModelAndView("redirect:list");
    }

    @PostMapping("/{id}/members")
    public ModelAndView addMembers(@PathVariable Long id,
                                   @ModelAttribute AddProjectMemberRequest addRequest,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/projects/" + id + "/members");
        }

        projectService.addMembers(addRequest);

        return new ModelAndView("redirect:/projects/" + id);
    }

    @GetMapping("/{id}/dormant")
    public ModelAndView makeDormantProject(@PathVariable Long id, Principal principal) {

        projectService.makeDormantProject(id);

        return new ModelAndView("redirect:/projects/" + principal.getName());
    }

    @GetMapping("/{id}/end")
    public ModelAndView makeEndProject(@PathVariable Long id, Principal principal) {

        projectService.makeEndProject(id);

        return new ModelAndView("redirect:/projects/" + principal.getName());
    }
}
