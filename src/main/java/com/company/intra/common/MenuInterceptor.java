package com.company.intra.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 공통 인터셉터
 * - 로그인 사용자/메뉴를 모든 화면 모델에 주입
 * - 추후 메뉴권한 체크 확장 지점
 */
@Component
public class MenuInterceptor implements HandlerInterceptor {

    private final MenuServiceLoader menuServiceLoader;

    public MenuInterceptor(MenuServiceLoader menuServiceLoader) {
        this.menuServiceLoader = menuServiceLoader;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView mav) throws Exception {
        if (mav == null || isRedirectOrForward(mav)) return;
        SessionUser user = SessionUtil.getLoginUser();
        // Spring Security 인증 정보와 세션 동기화는 AuthController에서 처리
        if (user != null) {
            mav.addObject("loginUser", user);
            List<Map<String, Object>> menus = menuServiceLoader.loadMenus(user);
            mav.addObject("menus", menus);
            mav.addObject("currentUri", request.getRequestURI());
        }
    }

    private boolean isRedirectOrForward(ModelAndView mav) {
        String v = mav.getViewName();
        return v != null && (v.startsWith("redirect:") || v.startsWith("forward:"));
    }
}
