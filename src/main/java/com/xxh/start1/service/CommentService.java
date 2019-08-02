package com.xxh.start1.service;
import com.xxh.start1.enums.CommentTypeENum;
import com.xxh.start1.exception.CustomizeErrorCode;
import com.xxh.start1.exception.CustomizeException;
import com.xxh.start1.mapper.CommentMapper;
import com.xxh.start1.mapper.QuestionExtMapper;
import com.xxh.start1.mapper.QuestionMapper;
import com.xxh.start1.model.Comment;
import com.xxh.start1.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0){
           throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        } if(comment.getType()==null||!CommentTypeENum.isExist(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
        }

        if(comment.getType()== CommentTypeENum.COMMENT.getType()){
            //回复评论
            Comment dbComment=commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment==null){
                throw  new  CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question question=questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCountView(question);
        }



    }
}
