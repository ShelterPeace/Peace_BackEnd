package com.shelter.peace.user.service;

import com.shelter.peace.user.entity.User;
import com.shelter.peace.user.repository.UserRepository;
import com.shelter.peace.user.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void signupUser(UserDTO userDTO) {
        String encodedPassword = encoder.encode(userDTO.getUserPwd());
        userDTO.setUserPwd(encodedPassword);
        User user = userDTO.DTOTOEntity();
        userRepository.save(user);
    }

    // PK로 유저 찾기
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 계정입니다."));
    }

    // ID로 유저 찾기
    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("없는 계정입니다."));
    }

    public boolean isUserIdExisting (String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new RuntimeException("이미 있는 아이디 입니다.");
        }
        return true;
    }

    public boolean isUserEmailExisting (String userEmail) {
        if (userRepository.existsByUserEmail(userEmail)) {
            throw new RuntimeException("이미 있는 이메일 입니다.");
        }
        return true;
    }

    @Transactional
    public void modifyPassword(long id, UserDTO.PasswordDTO passwordDTO) {
        User user = getUserById(id);

        if (!passwordDTO.getNewPassword().equals(passwordDTO.getNewPasswordConfirm())) {
            throw new RuntimeException("비밀번호가 다릅니다.");
        }

        String encodedPassword = encoder.encode(passwordDTO.getNewPassword());
        user.setUserPwd(encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public void modifyAddress(UserDTO userDTO) {
        User user = getUserById(userDTO.getId());

        if (userDTO.getUserAddress().isBlank()) {
            throw new NullPointerException("주소가 제대로 입력되지 않았습니다.");
        }
        user.setUserAddress(user.getUserAddress());
        userRepository.save(user);
    }

    public UserDTO getUserInfo(long id) {
        User user = getUserById(id);

        return UserDTO.builder()
                .userName(user.getUserName())
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userAddress(user.getUserAddress())
                .build();
    }

}
