package com.nhnacademy.gateway.controller;

import com.nhnacademy.gateway.dto.request.tag.CreateTagRequest;
import com.nhnacademy.gateway.dto.request.tag.ModifyTagRequest;
import com.nhnacademy.gateway.dto.response.tag.TagResponse;
import com.nhnacademy.gateway.service.TagService;
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
@RequestMapping("/projects/{projectId}/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/create")
    public ModelAndView create(@PathVariable Long projectId,
                               @ModelAttribute("tag") CreateTagRequest createRequest) {

        ModelAndView mav = new ModelAndView("tag/tag-form");
        mav.addObject("projectId", projectId);
        mav.addObject("url", "create");

        return mav;
    }

    @PostMapping("/create")
    public ModelAndView doCreate(@PathVariable Long projectId,
                                 @ModelAttribute("tag") @Valid CreateTagRequest createRequest,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("tag/tag-form");
            mav.addObject("url", "create");
            return mav;
        }

        tagService.createTag(createRequest);

        return new ModelAndView("redirect:/projects/" + projectId + "/tags");
    }

    @GetMapping("/{id}")
    public ModelAndView findTag(@PathVariable Long projectId, @PathVariable Long id) {

        ModelAndView mav = new ModelAndView("tag/tag");
        mav.addObject("tag", tagService.findTag(id));
        mav.addObject("projectId", projectId);

        return mav;
    }

    @GetMapping
    public ModelAndView tagList(@PathVariable Long projectId) {

        ModelAndView mav = new ModelAndView("tag/list");

        List<TagResponse> tags = tagService.findTagsByProjectId(projectId);

        mav.addObject("projectId", projectId);
        mav.addObject("tags", tags);

        return mav;
    }

    @GetMapping("/{id}/modify")
    public ModelAndView modify(@PathVariable Long projectId, @PathVariable Long id) {

        ModelAndView mav = new ModelAndView("tag/tag-form");

        mav.addObject("projectId", projectId);
        mav.addObject("tag", tagService.findTag(id));
        mav.addObject("url", "modify");

        return mav;
    }

    @PostMapping("/{id}/modify")
    public ModelAndView doModify(@PathVariable Long projectId, @PathVariable Long id,
                                 @ModelAttribute("tag") @Valid ModifyTagRequest modifyRequest,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("tag/tag-form");
            mav.addObject("url", "modify");
            return mav;
        }

        tagService.modifyTag(modifyRequest);

        return new ModelAndView("redirect:/projects/" + projectId + "/tags/" + id);
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long projectId, @PathVariable Long id) {

        tagService.deleteTag(id);

        return new ModelAndView("redirect:/projects/" + projectId + "/tags");
    }
}
