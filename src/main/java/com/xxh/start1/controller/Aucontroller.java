package com.xxh.start1.controller;

import com.xxh.start1.dto.AccessTokenDto;
import com.xxh.start1.dto.GitHubUser;
import com.xxh.start1.provider.Githubprovider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Aucontroller {
    @Autowired
    private Githubprovider githubprovider;
    @Value("${github.client.id}")
    private  String clientid;
    @Value("${github.client.sercet}")
    private  String clientserect;
    @Value("${github,reedirect_uri}")
    private  String clientRedirecturi;
    @GetMapping("/callback")

    public String callback(@RequestParam(name ="code")String code,
                           @RequestParam(name="state")String state)
    {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_secrect(clientserect);
        accessTokenDto.setClient_id(clientid);
        accessTokenDto.setRedirect_uri(clientRedirecturi);
        accessTokenDto.setState(state);
        String accessToken=githubprovider.getAccessToken(accessTokenDto);
        githubprovider.getuser(accessToken);
        GitHubUser user=githubprovider.getuser(accessToken);
        System.out.println(user.getName());
        return "hello1";
    }
}
