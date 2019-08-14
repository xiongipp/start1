package com.xxh.start1.cache;

import com.xxh.start1.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO>get(){
    List<TagDTO> tagDTOS = new ArrayList<>();
    TagDTO program = new TagDTO();
    program.setCategoryName("开发语言");
    program.setTags(Arrays.asList("js","php","css","java","c","c++","python"));
    tagDTOS.add(program);

    TagDTO framework = new TagDTO();
    framework.setCategoryName("平台框架");
    framework.setTags(Arrays.asList("spring","框架a","框架b","框架c","框架d"));
    tagDTOS.add(framework);
    return tagDTOS;
}
        public static  String filterInvalid(String tags){
        String[] spilt= StringUtils.split(tags,",");
        List<TagDTO> tagDTOS=get();
        List<Object> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(spilt).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
}
}
