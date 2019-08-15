package com.xxh.start1.controller;

import com.xxh.start1.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileController {
    @RequestMapping("/file/upload")
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/image/ad.png");
        return fileDTO;
    }
}
