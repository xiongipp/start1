package com.xxh.start1.controller;

import com.xxh.start1.dto.NotificationDTO;
import com.xxh.start1.dto.PaginationDTO;
import com.xxh.start1.enums.NotificationTypeEnum;
import com.xxh.start1.model.User;
import com.xxh.start1.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id,
                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO=notificationService.read(id,user);
       if(NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType()||NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
           return "redirect:/question/"+notificationDTO.getOuterid();
       }else {
           return "redirect:/";
       }

    }
}
