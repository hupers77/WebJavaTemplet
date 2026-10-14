package com.company.intra.controller;

import com.company.intra.common.SessionUtil;
import com.company.intra.common.SessionUser;
import com.company.intra.dto.LoginForm;
import com.company.intra.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * 인증 + 메인 + 헬스체크
 * - Spring Security가 실제 로그인을 처리하고, 여기서는 세션 동기화/화면만 담당
 */
@Controller
public class MainController {

    private final UserRepository userRepository;

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "auth/login";
    }

    @GetMapping("/")
    public String main(HttpServletRequest request) {
        syncSession();
        return "main/main";
    }

    @GetMapping("/health")
    public String health() {
        return "forward:/health-check.html";
    }

    /** Security 인증 정보를 사내 세션(SessionUser)에 동기화 */
    private void syncSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) return;
        if (SessionUtil.getLoginUser() != null) return;
        userRepository.findAuthById(auth.getName()).ifPresent(row -> {
            SessionUser u = new SessionUser(
                    String.valueOf(row.get("USER_ID")),
                    String.valueOf(row.getOrDefault("USER_NM", auth.getName())),
                    String.valueOf(row.getOrDefault("ROLE_CD", "USER")),
                    (String) row.get("DEPT_CD"));
            SessionUtil.setLoginUser(u);
        });
    }
}
