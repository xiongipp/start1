package com.xxh.start1.mapper;

import com.xxh.start1.model.Comment;
import com.xxh.start1.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
int incCommentCount(Comment comment);
}