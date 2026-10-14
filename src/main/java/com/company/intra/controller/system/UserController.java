package com.company.intra.controller.system;

import com.company.intra.common.Page;
import com.company.intra.common.SessionUtil;
import com.company.intra.dto.UserDto;
import com.company.intra.repository.CodeRepository;
import com.company.intra.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자관리 - 목록/상세/저장 (ADMIN 전용, SecurityConfig에서 차단)
 * 화면 개발 패턴의 기준이 되는 컨트롤러이므로 주석을 상세히 유지할 것
 */
@Controller
@RequestMapping("/system/user")
public class UserController {

    private final UserService userService;
    private final CodeRepository codeRepository;

    public UserController(UserService userService, CodeRepository codeRepository) {
        this.userService = userService;
        this.codeRepository = codeRepository;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "1") int pageNo,
                       @RequestParam(defaultValue = "10") int pageSize,
                       Model model) {
        Page<UserDto> page = userService.search(keyword, pageNo, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "system/userList";
    }

    @GetMapping("/{userId}")
    public String detail(@PathVariable String userId, Model model) {
        UserDto user = userService.findOne(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음: " + userId));
        model.addAttribute("user", user);
        model.addAttribute("deptList", codeRepository.findUsableByGroup("DEPT"));
        model.addAttribute("roleList", codeRepository.findUsableByGroup("ROLE"));
        return "system/userDetail";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("deptList", codeRepository.findUsableByGroup("DEPT"));
        model.addAttribute("roleList", codeRepository.findUsableByGroup("ROLE"));
        return "system/userDetail";
    }

    @PostMapping("/save")
    public String save(@RequestParam String userId,
                       @RequestParam String userNm,
                       @RequestParam(required = false) String password,
                       @RequestParam(required = false) String deptCd,
                       @RequestParam(defaultValue = "USER") String roleCd) {
        String loginId = SessionUtil.getLoginUser() != null
                ? SessionUtil.getLoginUser().getUserId() : "system";
        userService.save(userId, userNm, password, deptCd, roleCd, loginId);
        return "redirect:/system/user";
    }
}
