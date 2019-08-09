package com.xxh.start1.controller;

import com.xxh.start1.dto.CommentDTO;
import com.xxh.start1.dto.QuestionDTO;
import com.xxh.start1.enums.CommentTypeENum;
import com.xxh.start1.mapper.QuestionMapper;
import com.xxh.start1.model.Question;
import com.xxh.start1.service.CommentService;
import com.xxh.start1.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Max;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public  String question(@PathVariable(name = "id") Long id,
    Model model){
       QuestionDTO questionDTO= questionService.getById(id);
        List<QuestionDTO> relatedQuestinos=questionService.selectRelated(questionDTO);
       List<CommentDTO> comments= commentService.listByTargetId(id, CommentTypeENum.QUESTION);
       //累加阅读数
        questionService.incView(id);
       model.addAttribute("question",questionDTO);
       model.addAttribute("comments",comments);
       model.addAttribute("relatedQuestions",relatedQuestinos);

        return "question";
    }
}
