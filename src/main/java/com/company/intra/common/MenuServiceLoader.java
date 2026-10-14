package com.company.intra.common;

import com.company.intra.repository.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/** MenuInterceptor 순환참조 방지용 로더 (Repository 직접 호출) */
@Component
public class MenuServiceLoader {

    private final MenuRepository menuRepository;

    public MenuServiceLoader(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Map<String, Object>> loadMenus(SessionUser user) {
        try {
            if (user != null && user.isAdmin()) {
                return menuRepository.findAll();
            }
            return menuRepository.findAll(); // 일반사용자 메뉴필터는 다음 단계에서 확장
        } catch (Exception e) {
            return List.of();
        }
    }
}
