package com.xxh.start1.controller;

import com.xxh.start1.dto.CommentDTO;
import com.xxh.start1.dto.ResultDTO;
import com.xxh.start1.exception.CustomizeErrorCode;
import com.xxh.start1.model.Comment;
import com.xxh.start1.model.User;
import com.xxh.start1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public ResultDTO post(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorof(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okof();
    }
}
