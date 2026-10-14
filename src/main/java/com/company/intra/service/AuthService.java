package com.company.intra.service;

import com.company.intra.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Spring Security 인증용 Service.
 * CM_USER 테이블을 직접 조회한다.
 */
@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Map<String, Object> row = userRepository.findAuthById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));
        if (!"Y".equals(String.valueOf(row.get("USE_YN")))) {
            throw new UsernameNotFoundException("미사용 계정입니다: " + userId);
        }
        String role = "ROLE_" + String.valueOf(row.get("ROLE_CD"));
        return new User(
                String.valueOf(row.get("USER_ID")),
                String.valueOf(row.get("PWD")),
                List.of(new SimpleGrantedAuthority(role)));
    }
}
