package com.xxh.start1.service;

import com.xxh.start1.dto.PaginationDTO;
import com.xxh.start1.dto.QuestionDTO;
import com.xxh.start1.exception.CustomizeErrorCode;
import com.xxh.start1.exception.CustomizeException;
import com.xxh.start1.mapper.QuestionExtMapper;
import com.xxh.start1.mapper.QuestionMapper;
import com.xxh.start1.mapper.UserMapper;
import com.xxh.start1.model.Question;
import com.xxh.start1.model.QuestionExample;
import com.xxh.start1.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    public PaginationDTO list(Integer page, Integer size) {
        //size*(page-1)
        Integer totalpage;
        PaginationDTO paginationDTO=new PaginationDTO();
        //数据库中总问题数
        Integer totalcount=(int)questionMapper.countByExample(new QuestionExample());
        //这两个if算出总页数totalpage!
        if(totalcount%size==0){
            totalpage=totalcount/size;
        }else
        {
            totalpage=totalcount/size+1;
        }
        Integer offset=size*(page-1);
        //控制在页面列出的问题的条数
        List<Question> questions= questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questions){
           User user= userMapper.selectByPrimaryKey(question.getCreator());
           QuestionDTO questionDTO= new QuestionDTO();
           BeanUtils.copyProperties(question,questionDTO);
           questionDTO.setUser(user);
           questionDTOList.add(questionDTO);
        }
        paginationDTO.setPagination(totalpage,page);
        paginationDTO.setQuestions(questionDTOList);
        return  paginationDTO;
    }


    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer totalpage;
        PaginationDTO paginationDTO=new PaginationDTO();
        //（该用户总的问题数）
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);


        Integer totalcount=(int)questionMapper.countByExample(questionExample);
        //这两个if算出(该用户)总页数totalpage!
        if(totalcount%size==0){
            totalpage=totalcount/size;
        }else
        {
            totalpage=totalcount/size+1;
        }
        //size*(page-1)
        Integer offset=size*(page-1);
        //控制页面显示的问题的条数
        QuestionExample example=new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions= questionMapper.selectByExampleWithBLOBsWithRowbounds(example,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questions){
            User user= userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO= new  QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalpage,page);
        return  paginationDTO;

    }

    public QuestionDTO getById(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO= new  QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user= userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //create
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else{
            //update
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated=questionMapper.updateByExampleSelective(updateQuestion,example);
            if(updated!=1){
                throw  new  CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }

    }

    public void incView(Long id) {

        Question record = new Question();
        record.setId(id);

        record.setViewCount(1);
        questionExtMapper.incView(record);

    }
}
