package com.xxh.start1.service;

import com.xxh.start1.dto.NotificationDTO;
import com.xxh.start1.dto.PaginationDTO;
import com.xxh.start1.dto.QuestionDTO;
import com.xxh.start1.enums.NotificationStatusEnum;
import com.xxh.start1.enums.NotificationTypeEnum;
import com.xxh.start1.exception.CustomizeErrorCode;
import com.xxh.start1.exception.CustomizeException;
import com.xxh.start1.mapper.NotifiedMapper;
import com.xxh.start1.mapper.UserMapper;
import com.xxh.start1.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class NotificationService {
    @Autowired
    private NotifiedMapper notifiedMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer totalpage;
        PaginationDTO<NotificationDTO> paginationDTO=new PaginationDTO<>();
        //（该用户总的问题数）
        NotifiedExample notifiedExample=new NotifiedExample();
        notifiedExample.createCriteria()
                .andReceiverEqualTo(userId);

        Integer totalcount=(int)notifiedMapper.countByExample(notifiedExample);
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
        NotifiedExample example=new NotifiedExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        List<Notified> notifieds= notifiedMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        if(notifieds.size()==0){
            return paginationDTO;
        }
        List<NotificationDTO>notificationDTOS =new ArrayList<>();
        for (Notified notified : notifieds) {
            NotificationDTO notificationDto = new NotificationDTO();
            BeanUtils.copyProperties(notified,notificationDto);
            notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notified.getType()));
            notificationDTOS.add(notificationDto);
        }
        paginationDTO.setData(notificationDTOS);
        paginationDTO.setPagination(totalpage,page);
        return  paginationDTO;
    }

    public Long unreadCount(Integer userId) {
        NotifiedExample notifiedExample = new NotifiedExample();
        notifiedExample.createCriteria()
                .andReceiverEqualTo(userId);
        return  notifiedMapper.countByExample(notifiedExample);
    }

    public NotificationDTO read(Integer id, User user) {
        Notified notified = notifiedMapper.selectByPrimaryKey(id);
        if(notified.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notified.setStatus(NotificationStatusEnum.READ.getStatus());
        notifiedMapper.updateByPrimaryKey(notified);
        NotificationDTO notificationDto = new NotificationDTO();
        BeanUtils.copyProperties(notified,notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notified.getType()));
        return notificationDto;
    }
}
