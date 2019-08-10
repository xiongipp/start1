package com.xxh.start1.controller;

import com.xxh.start1.cache.TagCache;
import com.xxh.start1.dto.QuestionDTO;
import com.xxh.start1.mapper.QuestionMapper;
import com.xxh.start1.mapper.UserMapper;
import com.xxh.start1.model.Question;
import com.xxh.start1.model.User;
import com.xxh.start1.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class publishController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public  String edit(@PathVariable(name = "id") Long id,
                        Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }
    @GetMapping("/publish")
    public  String publish(Model model)
    {
        model.addAttribute("tags",TagCache.get());
        return "publish";

    }
    @PostMapping("/publish")
    public  String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            @RequestParam("id")Long id,
            HttpServletRequest request,
            Model model)

    {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",TagCache.get());

        if(title==null||title=="")
        {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null||description=="")
        {
            model.addAttribute("error","问题描述必须写上");
            return "publish";
        }
        if(tag==null||tag=="")
        {
            model.addAttribute("error","标签也是必填选项");
            return "publish";
        }
        User user=(User) request.getSession().getAttribute("user");
        if(user==null)
        {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
       Question question=new Question();
       question.setTitle(title);
       question.setDescription(description);
       question.setTag(tag);
       question.setCreator(user.getId());
       question.setGmtCreate(System.currentTimeMillis());
       question.setGmtModified(question.getGmtCreate());
       question.setId(id);
       questionService.createOrUpdate(question);



        return "redirect:/";
    }
}
