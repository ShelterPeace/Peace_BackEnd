package com.shelter.peace.user.service;

import com.shelter.peace.security.service.dto.AuthDTO;
import com.shelter.peace.user.entity.User;
import com.shelter.peace.user.repository.UserRepository;
import com.shelter.peace.user.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signupUser(UserDTO userDTO) {
        User user = userDTO.DTOTOEntity();
        userRepository.save(user);
    }
}
