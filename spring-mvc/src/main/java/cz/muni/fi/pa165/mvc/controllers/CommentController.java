package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.CommentCreateDTO;
import cz.muni.fi.pa165.dto.CommentUpdateDTO;
import cz.muni.fi.pa165.facade.CommentFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

    @Inject
    private CommentFacade commentFacade;

    @PostMapping(value = "/new")
    public String postCreate(Model model, @Valid @ModelAttribute("comment") CommentCreateDTO comment, @RequestParam(required = false) Long timelineId){
        LOG.debug("post new comment");

        commentFacade.createComment(comment);

        return redirect(timelineId);
    }

    @GetMapping(value = "/update/{id}")
    public String getUpdate(Model model,
                            @PathVariable Long id,
                            @RequestParam(required = false) Long timelineId){
        LOG.debug("get comment update");

        var commentOpt = commentFacade.findById(id);
        if (commentOpt.isEmpty()){
            LOG.debug("Comment with id {} does not exist", id);
            model.addAttribute("comment_not_found", "Comment does not exist");
            return "/comment/update";
        }

        var comment = commentOpt.get();
        var commentUpdate = new CommentUpdateDTO();
        commentUpdate.setId(comment.getId());
        commentUpdate.setText(comment.getText());

        model.addAttribute("comment", commentUpdate);
        model.addAttribute("timelineId", timelineId);

        return "/comment/update";
    }

    @PostMapping(value = "/update")
    public String postUpdate(Model model,
                            @Valid @ModelAttribute("comment") CommentUpdateDTO comment, @RequestParam(required = false) Long timelineId){
        LOG.debug("post comment update");

        commentFacade.updateComment(comment);

        return redirect(timelineId);
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(Model model, @PathVariable Long id, @RequestParam(required = false) Long timelineId){
        LOG.debug("delete comment");

        var comment  = commentFacade.findById(id);
        if (comment.isEmpty()){
            LOG.debug("Comment with id {} does not exist", id);
            model.addAttribute("comment_not_found", "Comment does not exist");
        } else {
            commentFacade.deleteComment(id);
        }

        return redirect(timelineId);
    }

    private String redirect(Long timelineId){
        if (timelineId == null){
            return "redirect:/home";
        }
        return "redirect:/timeline/" + timelineId;
    }
}
