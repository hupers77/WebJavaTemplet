package com.company.intra.service;

import com.company.intra.common.Page;
import com.company.intra.dto.UserDto;
import com.company.intra.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** 사용자 Service */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UserDto> search(String keyword, int pageNo, int pageSize) {
        if (pageNo < 1) pageNo = 1;
        if (pageSize < 1 || pageSize > 100) pageSize = 10;
        long total = userRepository.countByKeyword(keyword);
        int offset = (pageNo - 1) * pageSize;
        List<UserDto> list = total == 0 ? List.of() : userRepository.findPage(keyword, offset, pageSize);
        return new Page<>(list, pageNo, pageSize, total);
    }

    public Optional<UserDto> findOne(String userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void save(String userId, String userNm, String rawPwd,
                     String deptCd, String roleCd, String loginUserId) {
        if (userId == null || userId.trim().isEmpty()) throw new IllegalArgumentException("아이디는 필수입니다.");
        if (userNm == null || userNm.trim().isEmpty()) throw new IllegalArgumentException("성명은 필수입니다.");
        Optional<UserDto> exists = userRepository.findById(userId);
        if (exists.isEmpty()) {
            if (rawPwd == null || rawPwd.isEmpty()) throw new IllegalArgumentException("신규 등록 시 비밀번호는 필수입니다.");
            userRepository.insert(userId, userNm, passwordEncoder.encode(rawPwd), deptCd, roleCd, loginUserId);
        } else {
            userRepository.update(userId, userNm, deptCd, roleCd, exists.get().getUseYn(), loginUserId);
            if (rawPwd != null && !rawPwd.isEmpty()) {
                userRepository.updatePassword(userId, passwordEncoder.encode(rawPwd));
            }
        }
    }
}
