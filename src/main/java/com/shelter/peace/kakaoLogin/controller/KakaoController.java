package com.shelter.peace.kakaoLogin.controller;

import com.shelter.peace.kakaoLogin.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Change to @Controller to render HTML
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    @GetMapping("/oauth/callback/kakao")
    public String kakaoLogin(Model model, @RequestParam(value = "code", required = false) String code) throws Exception {
        model.addAttribute("kakaoUrl", "https://kauth.kakao.com/oauth/authorize?client_id=32f42cf6b572f36e99516fb994f935b6&redirect_uri=http://localhost:8080/oauth/callback/kakao&response_type=code");

        System.out.println("#########" + code);

         String access_Token = kakaoService.getAccessToken(code);
         System.out.println("###access_Token#### : " + access_Token);

         String userInfo = kakaoService.createKakaoUser(access_Token);
         System.out.println(userInfo);

        return "kakao"; // Thymeleaf template name (kakao.html)
    }
}
