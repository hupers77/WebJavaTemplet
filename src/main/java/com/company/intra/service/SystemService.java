package com.company.intra.service;

import com.company.intra.repository.CodeRepository;
import com.company.intra.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/** 메뉴/코드 Service */
@Service
public class SystemService {

    private final MenuRepository menuRepository;
    private final CodeRepository codeRepository;

    public SystemService(MenuRepository menuRepository, CodeRepository codeRepository) {
        this.menuRepository = menuRepository;
        this.codeRepository = codeRepository;
    }

    public List<Map<String, Object>> menus() {
        return menuRepository.findAllIncludeUnused();
    }

    @Transactional
    public void saveMenu(String menuId, String upMenuId, String menuNm, String url, int sortNo, String useYn) {
        if (menuId == null || menuId.trim().isEmpty()) throw new IllegalArgumentException("메뉴ID는 필수입니다.");
        menuRepository.save(menuId, upMenuId, menuNm, url, sortNo, useYn);
    }

    @Transactional
    public void deleteMenu(String menuId) {
        menuRepository.delete(menuId);
    }

    public List<Map<String, Object>> codeGroups() {
        return codeRepository.findGroups();
    }

    public List<Map<String, Object>> codes(String grpCd) {
        return codeRepository.findByGroup(grpCd);
    }

    @Transactional
    public void saveCode(String grpCd, String dtlCd, String dtlNm, int sortNo, String useYn) {
        codeRepository.save(grpCd, dtlCd, dtlNm, sortNo, useYn);
    }

    @Transactional
    public void deleteCode(String grpCd, String dtlCd) {
        codeRepository.delete(grpCd, dtlCd);
    }
}
