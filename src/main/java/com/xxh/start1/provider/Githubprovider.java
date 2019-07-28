package com.xxh.start1.provider;

import com.alibaba.fastjson.JSON;
import com.xxh.start1.dto.AccessTokenDto;
import com.xxh.start1.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import static okhttp3.RequestBody.create;

@Component
public class Githubprovider {
    public String getAccessToken( AccessTokenDto accessTokenDto){

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        OkHttpClient client=new OkHttpClient();
       RequestBody body=RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute())
            {
                String string= response.body().string();
                String token=string.split("&")[0].split("=")[1];
                return token;
            } catch (IOException e)
            {
                e.printStackTrace(); 
            }

        return null;

    }
        public GitHubUser getuser(String accessToken) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String string = null;
                string = response.body().string();
                GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
                return gitHubUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;

        }


    }
