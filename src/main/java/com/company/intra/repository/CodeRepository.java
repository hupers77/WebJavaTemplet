package com.company.intra.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/** 공통코드 Repository */
@Repository
public class CodeRepository {

    private final JdbcTemplate jdbc;

    public CodeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> findGroups() {
        return jdbc.queryForList(
            "SELECT GRP_CD, MIN(DTL_NM) GRP_NM, COUNT(*) CNT FROM CM_CODE GROUP BY GRP_CD ORDER BY GRP_CD");
    }

    public List<Map<String, Object>> findByGroup(String grpCd) {
        return jdbc.queryForList(
            "SELECT GRP_CD, DTL_CD, DTL_NM, SORT_NO, USE_YN FROM CM_CODE WHERE GRP_CD=? ORDER BY SORT_NO",
            grpCd);
    }

    public List<Map<String, Object>> findUsableByGroup(String grpCd) {
        return jdbc.queryForList(
            "SELECT DTL_CD code, DTL_NM name FROM CM_CODE WHERE GRP_CD=? AND USE_YN='Y' ORDER BY SORT_NO",
            grpCd);
    }

    public int save(String grpCd, String dtlCd, String dtlNm, int sortNo, String useYn) {
        int cnt = jdbc.queryForObject(
            "SELECT COUNT(*) FROM CM_CODE WHERE GRP_CD=? AND DTL_CD=?", Integer.class, grpCd, dtlCd);
        if (cnt > 0) {
            return jdbc.update(
                "UPDATE CM_CODE SET DTL_NM=?, SORT_NO=?, USE_YN=? WHERE GRP_CD=? AND DTL_CD=?",
                dtlNm, sortNo, useYn, grpCd, dtlCd);
        }
        return jdbc.update(
            "INSERT INTO CM_CODE (GRP_CD, DTL_CD, DTL_NM, SORT_NO, USE_YN) VALUES (?,?,?,?,?)",
            grpCd, dtlCd, dtlNm, sortNo, useYn);
    }

    public int delete(String grpCd, String dtlCd) {
        return jdbc.update("DELETE FROM CM_CODE WHERE GRP_CD=? AND DTL_CD=?", grpCd, dtlCd);
    }
}
