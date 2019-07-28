package com.xxh.start1.controller;

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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class Index {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public  String hello(HttpServletRequest request,
                         Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    System.out.println("拿到用户的token了!");
                    String token = cookie.getValue();
                    System.out.println(token);
                    User user = userMapper.findBodyToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        System.out.println("用户存在");
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questionList=questionService.list();
        model.addAttribute("question",questionList);
        return "hello1";
    }
}
