package com.shelter.peace.configuration;

import com.shelter.peace.security.jwt.JwtAccessDeniedHandler;
import com.shelter.peace.security.jwt.JwtAuthenticationEntryPoint;
import com.shelter.peace.security.jwt.JwtAuthenticationFilter;
import com.shelter.peace.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 설정 클래스
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder encoder() {
        // 비밀번호를 DB에 저장하기 전 사용할 암호화
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // ACL(Access Control List, 접근 제어 목록)의 예외 URL 설정
//        return (web)
//                -> web
//                .ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 리소스들
//    }


    // 필터 체인 구현(HttpSecurity 객체 사용)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 인터셉터로 요청을 안전하게 보호하는 방법 설정
        http
                // jwt 토큰 사용을 위한 설정
                .csrf(httpSecurityCsrfConfigurer -> {
                    httpSecurityCsrfConfigurer.disable();
                })
                .httpBasic(httpSecurityHttpBasicConfigurer -> {
                    httpSecurityHttpBasicConfigurer.disable();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer.disable();
                })

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    );
                })

                // 예외 처리
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // customEntryPoint
                                .accessDeniedHandler(jwtAccessDeniedHandler) // cutomAccessDeniedHandler
                )

                .authorizeHttpRequests((authorizeHttpRequests) -> {
//                    authorizeHttpRequests.requestMatchers("/**").authenticated();
//                    authorizeHttpRequests.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN");
//                    authorizeHttpRequests.requestMatchers("/auth/signup").permitAll();
                    authorizeHttpRequests.anyRequest().permitAll();
                })
                ;

        return http.build();
    }
}
