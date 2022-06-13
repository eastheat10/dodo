package com.nhnacademy.gateway.controller;

import com.nhnacademy.gateway.dto.request.milestone.CreateMilestoneRequest;
import com.nhnacademy.gateway.dto.request.milestone.ModifyMileStoneRequest;
import com.nhnacademy.gateway.dto.response.milestone.MilestoneResponse;
import com.nhnacademy.gateway.service.MilestoneService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projects/{projectId}/milestones")
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping("/create")
    public ModelAndView create(@PathVariable Long projectId,
                               @ModelAttribute("milestone") CreateMilestoneRequest createRequest) {

        ModelAndView mav = new ModelAndView("milestone/milestone-form");
        mav.addObject("projectId", projectId);
        mav.addObject("url", "create");

        return mav;
    }

    @PostMapping("/create")
    public ModelAndView doCreateMilestone(@PathVariable Long projectId,
                                          @ModelAttribute @Valid
                                          CreateMilestoneRequest createRequest,
                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("milestone/milestone-form");
        }

        milestoneService.createMilestone(createRequest);

        return new ModelAndView("redirect:/projects/" + projectId);
    }

    @GetMapping("/{id}")
    public ModelAndView findMilestone(@PathVariable Long id) {

        MilestoneResponse milestone = milestoneService.findMilestone(id);

        ModelAndView mav = new ModelAndView("milestone/milestone");
        mav.addObject("milestone", milestone);

        return mav;
    }

    @GetMapping("/list")
    public ModelAndView milestoneList(@PathVariable Long projectId) {

        ModelAndView mav = new ModelAndView("milestone/list");

        List<MilestoneResponse> milestones =
            milestoneService.findMilestonesByProjectId(projectId);

        mav.addObject("projectId", projectId);
        mav.addObject("milestones", milestones);

        return mav;
    }

    @GetMapping("/{id}/modify")
    public ModelAndView modify(@PathVariable Long id) {

        ModelAndView mav = new ModelAndView("milestone/milestone-form");

        mav.addObject("milestone", milestoneService.findMilestone(id));
        mav.addObject("url", "modify");

        return mav;
    }

    @PostMapping("/{id}/modify")
    public ModelAndView doModify(@PathVariable Long projectId,
                                 @PathVariable Long id,
                                 @ModelAttribute("milestone") @Valid
                                 ModifyMileStoneRequest modifyRequest,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("milestone/milestone-form");
        }

        milestoneService.modifyMilestone(modifyRequest);

        return new ModelAndView("redirect:/projects/" + projectId + "/milestones/" + id);
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long projectId, @PathVariable Long id) {

        milestoneService.deleteMilestone(id);

        return new ModelAndView("redirect:/projects/" + projectId + "/milestones/list");
    }
}
