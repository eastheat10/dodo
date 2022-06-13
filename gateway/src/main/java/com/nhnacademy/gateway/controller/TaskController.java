package com.nhnacademy.gateway.controller;

import com.nhnacademy.gateway.dto.request.task.CreateTaskRequest;
import com.nhnacademy.gateway.dto.request.task.ModifyTaskRequest;
import com.nhnacademy.gateway.dto.response.task.TaskResponse;
import com.nhnacademy.gateway.service.MilestoneService;
import com.nhnacademy.gateway.service.TagService;
import com.nhnacademy.gateway.service.task.TaskService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final MilestoneService milestoneService;
    private final TagService tagService;
    private final RedisTemplate<String, String> redisTemplate;

    @GetMapping("/create")
    public ModelAndView create(@PathVariable Long projectId,
                               @ModelAttribute("task") CreateTaskRequest createRequest,
                               HttpSession httpSession) {

        ModelAndView mav = new ModelAndView("task/task-form");
        String username = (String) redisTemplate.opsForHash().get(httpSession.getId(), "username");

        mav.addObject("username", username);
        mav.addObject("milestones", milestoneService.findMilestonesByProjectId(projectId));
        mav.addObject("tagList", tagService.findTagsByProjectId(projectId));
        mav.addObject("projectId", projectId);
        mav.addObject("url", "create");

        return mav;
    }

    @PostMapping("/create")
    public ModelAndView doCreate(@PathVariable Long projectId,
                                 @ModelAttribute("task") @Valid CreateTaskRequest createRequest,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("task/task-form");
            mav.addObject("url", "create");
            return mav;
        }

        taskService.createTask(createRequest);

        return new ModelAndView("redirect:/projects/" + projectId);
    }


    @GetMapping("/{id}")
    public ModelAndView findTask(@PathVariable Long projectId, @PathVariable Long id,
                                 HttpSession httpSession) {

        TaskResponse task = taskService.findTask(id);

        String username = (String) redisTemplate.opsForHash().get(httpSession.getId(), "username");

        ModelAndView mav = new ModelAndView("task/task");
        mav.addObject("username", username);
        mav.addObject("projectId", projectId);
        mav.addObject("task", task);

        return mav;
    }

    @GetMapping("/{id}/modify")
    public ModelAndView modify(@PathVariable Long projectId, @PathVariable Long id,
                               HttpSession httpSession) {

        ModelAndView mav = new ModelAndView("task/task-form");

        mav.addObject("task", taskService.findTask(id));
        mav.addObject("url", "modify");
        mav.addObject("projectId", projectId);
        mav.addObject("milestones", milestoneService.findMilestonesByProjectId(projectId));
        mav.addObject("tagList", tagService.findTagsByProjectId(projectId));


        return mav;
    }

    @PostMapping("/{id}/modify")
    public ModelAndView doModify(@PathVariable Long projectId, @PathVariable Long id,
                                 @ModelAttribute("task") @Valid ModifyTaskRequest modifyRequest,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("task/task-form");
            mav.addObject("url", "modify");
            return mav;
        }

        taskService.modifyTask(modifyRequest);

        return new ModelAndView("redirect:/projects/" + projectId + "/tasks/" + id);
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long projectId, @PathVariable Long id) {

        taskService.deleteTask(id);

        return new ModelAndView("redirect:/projects/" + projectId);
    }
}
