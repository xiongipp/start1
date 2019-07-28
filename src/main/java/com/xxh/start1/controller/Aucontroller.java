package com.xxh.start1.controller;

import com.xxh.start1.dto.AccessTokenDto;
import com.xxh.start1.dto.GitHubUser;
import com.xxh.start1.mapper.UserMapper;
import com.xxh.start1.model.User;
import com.xxh.start1.provider.Githubprovider;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.util.UUID;

@Controller
public class Aucontroller {
    @Autowired
    private Githubprovider githubprovider;
    @Value("${github.client.id}")
    private  String clientid;
    @Value("${github.client.secret}")
    private  String clientsecret;
    @Value("${github,redirect_uri}")
    private  String clientRedirecturi;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")

    public String callback(@RequestParam(name ="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response)
    {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_secret(clientsecret);
        accessTokenDto.setClient_id(clientid);
        accessTokenDto.setRedirect_uri(clientRedirecturi);
        accessTokenDto.setState(state);
        String accessToken=githubprovider.getAccessToken(accessTokenDto);
        GitHubUser githubuser=githubprovider.getuser(accessToken);
        if(githubuser!=null){

            //登录成功
            User user=new User();
            String token=UUID.randomUUID().toString();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubuser.getName());
            user.setAccountId(String.valueOf(githubuser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubuser.getAvatar_url());
            System.out.println(token);
            userMapper.insert(user);
            System.out.println("执行数据库插入之后："+user.getToken());

            response.addCookie(new Cookie("token",user.getToken()));
            //request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else
        {
                return "redirect:/";
        }
    }
}
