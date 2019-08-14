package com.xxh.start1.service;
import com.xxh.start1.dto.CommentDTO;
import com.xxh.start1.enums.CommentTypeENum;
import com.xxh.start1.enums.NotificationStatusEnum;
import com.xxh.start1.enums.NotificationTypeEnum;
import com.xxh.start1.exception.CustomizeErrorCode;
import com.xxh.start1.exception.CustomizeException;
import com.xxh.start1.mapper.*;
import com.xxh.start1.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private  UserMapper usermapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private  NotifiedMapper notifiedMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {

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
            //回复问题
            Question question=questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            System.out.println("开始插入");
            commentMapper.insert(comment);
            //增加评论数
            Comment ParentComment = new Comment();
            ParentComment.setId(comment.getParentId());
            System.out.println("设置评论ID成功");
            ParentComment.setCommentCount(1);
            System.out.println("设置评论数成功");
            commentExtMapper.incCommentCount(ParentComment);
            System.out.println("增加评论数成功");
            //创建通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT,question.getId());
        }else{
            //回复问题
            Question question=questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //增加评论数
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
            //创建通知
            createNotify(comment, question.getCreator(),commentator.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION,question.getId());
        }

    }
    //创建通知
    private void createNotify(Comment comment, Integer receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        Notified notified = new Notified();
        notified.setOuterid(outerId);
        notified.setNotifier(comment.getCommentator());
        notified.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notified.setReceiver(receiver);
        notified.setGmtCreate(System.currentTimeMillis());
        notified.setType(notificationType.getType());
        notified.setNotifierName(notifierName);
        notified.setOuterTitle(outerTitle);
        notifiedMapper.insert(notified);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeENum type) {
        CommentExample commentExample=new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment>comments= commentMapper.selectByExample(commentExample);
        if(comments.size()==0){
            return new ArrayList<>();
        }
        //获取去重评论人
       Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论人转化为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users=usermapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
