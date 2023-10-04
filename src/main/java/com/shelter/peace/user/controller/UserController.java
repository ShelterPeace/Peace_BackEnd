package com.shelter.peace.user.controller;

import com.shelter.peace.dto.ResponseDTO;
import com.shelter.peace.user.service.UserService;
import com.shelter.peace.user.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> inputUser(@RequestBody UserDTO userDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        userService.inputUser(userDTO);

        responseDTO.setItem("성공");
        responseDTO.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDTO);
    }
}
