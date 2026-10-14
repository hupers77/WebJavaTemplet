package com.company.intra.repository;

import com.company.intra.dto.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 사용자 Repository - JdbcTemplate + Plain SQL (사내 표준)
 */
@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<UserDto> mapper = (rs, rowNum) -> {
        UserDto d = new UserDto();
        d.setUserId(rs.getString("USER_ID"));
        d.setUserNm(rs.getString("USER_NM"));
        d.setDeptCd(rs.getString("DEPT_CD"));
        d.setRoleCd(rs.getString("ROLE_CD"));
        d.setUseYn(rs.getString("USE_YN"));
        d.setRegDt(rs.getString("REG_DT"));
        return d;
    };

    /** Spring Security 인증용 단건 조회 (PWD 포함) */
    public Optional<Map<String, Object>> findAuthById(String userId) {
        List<Map<String, Object>> list = jdbc.queryForList(
            "SELECT USER_ID, USER_NM, PWD, ROLE_CD, DEPT_CD, USE_YN FROM CM_USER WHERE USER_ID = ?", userId);
        return list.stream().findFirst();
    }

    public long countByKeyword(String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CM_USER WHERE 1=1");
        List<Object> args = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (USER_ID LIKE ? OR USER_NM LIKE ?)");
            args.add("%" + keyword + "%");
            args.add("%" + keyword + "%");
        }
        Long c = jdbc.queryForObject(sql.toString(), Long.class, args.toArray());
        return c == null ? 0 : c;
    }

    public List<UserDto> findPage(String keyword, int offset, int limit) {
        StringBuilder sql = new StringBuilder(
            "SELECT USER_ID, USER_NM, DEPT_CD, ROLE_CD, USE_YN, DATE_FORMAT(REG_DT,'%Y-%m-%d') REG_DT FROM CM_USER WHERE 1=1");
        List<Object> args = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (USER_ID LIKE ? OR USER_NM LIKE ?)");
            args.add("%" + keyword + "%");
            args.add("%" + keyword + "%");
        }
        sql.append(" ORDER BY REG_DT DESC LIMIT ?, ?");
        args.add(offset);
        args.add(limit);
        return jdbc.query(sql.toString(), mapper, args.toArray());
    }

    public Optional<UserDto> findById(String userId) {
        List<UserDto> list = jdbc.query(
            "SELECT USER_ID, USER_NM, DEPT_CD, ROLE_CD, USE_YN, DATE_FORMAT(REG_DT,'%Y-%m-%d') REG_DT FROM CM_USER WHERE USER_ID = ?",
            mapper, userId);
        return list.stream().findFirst();
    }

    public int insert(String userId, String userNm, String encPwd, String deptCd, String roleCd, String regId) {
        return jdbc.update(
            "INSERT INTO CM_USER (USER_ID, USER_NM, PWD, DEPT_CD, ROLE_CD, USE_YN, REG_ID) VALUES (?,?,?,?,?,'Y',?)",
            userId, userNm, encPwd, deptCd, roleCd, regId);
    }

    public int update(String userId, String userNm, String deptCd, String roleCd, String useYn, String modId) {
        return jdbc.update(
            "UPDATE CM_USER SET USER_NM=?, DEPT_CD=?, ROLE_CD=?, USE_YN=?, MOD_ID=? WHERE USER_ID=?",
            userNm, deptCd, roleCd, useYn, modId, userId);
    }

    public int updatePassword(String userId, String encPwd) {
        return jdbc.update("UPDATE CM_USER SET PWD=? WHERE USER_ID=?", encPwd, userId);
    }

    public int insertLoginHist(String userId, String ip, String successYn, String failRsn) {
        return jdbc.update(
            "INSERT INTO CM_LOGIN_HIST (USER_ID, LOGIN_IP, SUCCESS_YN, FAIL_RSN) VALUES (?,?,?,?)",
            userId, ip, successYn, failRsn);
    }
}
