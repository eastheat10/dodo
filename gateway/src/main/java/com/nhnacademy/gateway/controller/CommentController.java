package com.nhnacademy.gateway.controller;

import com.nhnacademy.gateway.dto.request.comment.CreateCommentRequest;
import com.nhnacademy.gateway.dto.request.comment.ModifyCommentRequest;
import com.nhnacademy.gateway.service.CommentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ModelAndView create(@PathVariable Long projectId, @PathVariable Long taskId,
                               @ModelAttribute @Valid CreateCommentRequest createRequest,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

        }

        commentService.addComment(createRequest);

        return new ModelAndView("redirect:/projects/" + projectId + "/tasks/" + taskId);
    }

    @PostMapping("/modify")
    public ModelAndView modify(@PathVariable Long projectId, @PathVariable Long taskId,
                               @ModelAttribute @Valid ModifyCommentRequest modifyRequest,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

        }

        commentService.modifyComment(modifyRequest);

        return new ModelAndView("redirect:/proejects/" + projectId + "/tasks/" + taskId);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long projectId, @PathVariable Long taskId,
                               @PathVariable Long id) {
        commentService.delete(id);

        return new ModelAndView("redirect:/proejects/" + projectId + "/tasks/" + taskId);
    }
}