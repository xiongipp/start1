package com.xxh.start1.cache;

import com.xxh.start1.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
