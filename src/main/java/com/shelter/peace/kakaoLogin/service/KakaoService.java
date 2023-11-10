package com.shelter.peace.kakaoLogin.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shelter.peace.kakaoLogin.entity.KakaoUser;
import com.shelter.peace.kakaoLogin.repository.KakaoRepository;
import com.shelter.peace.user.entity.User;
import com.shelter.peace.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoRepository kakaoRepository;

    public String getAccessToken(String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");

            sb.append("&client_id=32f42cf6b572f36e99516fb994f935b6"); //본인이 발급받은 key
            sb.append("&redirect_uri=http://localhost:8080/oauth/callback/kakao"); // 본인이 설정한 주소

            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    public String createKakaoUser(String token) throws Exception {

        final String reqURL = "https://kapi.kakao.com/v2/user/me";

        int id = 0;
        String email = null;
        String nickname = null;
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            // "properties" 객체에서 "nickname" 가져오기
            JsonObject properties = element.getAsJsonObject().getAsJsonObject("properties");
            nickname = properties.get("nickname").getAsString();

            System.out.println("id: " + id);
            System.out.println("email: " + email);
            System.out.println("nickname: " + nickname);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //DB저장
        KakaoUser kakaoUser = new KakaoUser((long) id, email, nickname);
        kakaoRepository.save(kakaoUser);

        return reqURL;
    }
}


