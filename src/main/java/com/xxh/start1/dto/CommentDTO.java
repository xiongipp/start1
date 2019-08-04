package com.xxh.start1.dto;

import com.xxh.start1.model.User;
import lombok.Data;

@Data
public class CommentDTO {

        private Long id;
        private Long parentId;
        private Integer type;
        private Integer commentator;
        private Long gmtCreate;
        private Long gmtModified;
        private Long likeCount;
        private String content;
        private User user;
}
