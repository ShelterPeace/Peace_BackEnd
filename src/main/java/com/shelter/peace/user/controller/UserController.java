package com.shelter.peace.user.controller;

import com.shelter.peace.security.service.dto.Role;
import com.shelter.peace.user.entity.UserDetailsImpl;
import com.shelter.peace.user.service.UserService;
import com.shelter.peace.user.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        log.info("회원가입 userDTO: " + userDTO);
        userDTO.setRole(Role.USER);
        userService.signupUser(userDTO);
        return ResponseEntity.ok().build();
    }

    // 아이디 중복 검사
    @GetMapping("/idChk")
    public ResponseEntity<?> idCheck(@RequestParam(value = "userId") String userId) {
        log.info("아이디 검사: " + userId);
        userService.isUserIdExisting(userId);
        return ResponseEntity.ok("사용가능한 아이디 입니다.");
    }

    // 이메일 중복 검사
    @GetMapping("/emailChk")
    public ResponseEntity<?> emailCheck(@RequestParam(value = "userEmail") String userEmail) {
        log.info("이메일 검사: " + userEmail);
        userService.isUserEmailExisting(userEmail);
        return ResponseEntity.ok("사용가능한 이메일 입니다.");
    }

    // 비밀번호 변경
    @PostMapping("/modify/password")
    public ResponseEntity<?> modifyPassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody UserDTO.PasswordDTO passwordDTO) {
        log.info("비밀번호 변경 유저: " + userDetails.getUsername());

        userService.modifyPassword(userDetails.getId(), passwordDTO);

        return ResponseEntity.ok().build();
    }

    // 주소 변경
    @PostMapping("/modify/address")
    public ResponseEntity<?> modifyAddress(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestBody UserDTO userDTO) {
        log.info("주소 변경 유저: " + userDetails.getUsername());
        userDTO.setId(userDetails.getId());
        userService.modifyAddress(userDTO);

        return ResponseEntity.ok().build();
    }
}
