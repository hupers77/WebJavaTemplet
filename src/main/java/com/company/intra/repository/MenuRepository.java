package com.company.intra.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/** 메뉴 Repository */
@Repository
public class MenuRepository {

    private final JdbcTemplate jdbc;

    public MenuRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> findAll() {
        return jdbc.queryForList(
            "SELECT MENU_ID, UP_MENU_ID, MENU_NM, URL, SORT_NO, USE_YN FROM CM_MENU WHERE USE_YN='Y' ORDER BY SORT_NO, MENU_ID");
    }

    public List<Map<String, Object>> findAllIncludeUnused() {
        return jdbc.queryForList(
            "SELECT MENU_ID, UP_MENU_ID, MENU_NM, URL, SORT_NO, USE_YN FROM CM_MENU ORDER BY SORT_NO, MENU_ID");
    }

    public int save(String menuId, String upMenuId, String menuNm, String url, int sortNo, String useYn) {
        int cnt = jdbc.queryForObject("SELECT COUNT(*) FROM CM_MENU WHERE MENU_ID=?", Integer.class, menuId);
        if (cnt > 0) {
            return jdbc.update(
                "UPDATE CM_MENU SET UP_MENU_ID=?, MENU_NM=?, URL=?, SORT_NO=?, USE_YN=? WHERE MENU_ID=?",
                upMenuId, menuNm, url, sortNo, useYn, menuId);
        }
        return jdbc.update(
            "INSERT INTO CM_MENU (MENU_ID, UP_MENU_ID, MENU_NM, URL, SORT_NO, USE_YN) VALUES (?,?,?,?,?,?)",
            menuId, upMenuId, menuNm, url, sortNo, useYn);
    }

    public int delete(String menuId) {
        return jdbc.update("DELETE FROM CM_MENU WHERE MENU_ID=?", menuId);
    }
}
