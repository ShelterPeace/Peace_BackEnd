package com.shelter.peace.user.service;

import com.shelter.peace.user.entity.User;
import com.shelter.peace.user.entity.UserDetailsImpl;
import com.shelter.peace.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String userId) {
        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디는 찾을 수 없습니다. -> " + userId));
        if (findUser != null) {
            UserDetailsImpl userDetails = new UserDetailsImpl(findUser);
            return userDetails;
        }
        return null;
    }
}
