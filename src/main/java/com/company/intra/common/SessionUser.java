package com.company.intra.common;

import java.io.Serializable;

/** 세션에 담는 최소 사용자 정보 */
public class SessionUser implements Serializable {

    private String userId;
    private String userNm;
    private String roleCd;
    private String deptCd;

    public SessionUser() {}

    public SessionUser(String userId, String userNm, String roleCd, String deptCd) {
        this.userId = userId;
        this.userNm = userNm;
        this.roleCd = roleCd;
        this.deptCd = deptCd;
    }

    public boolean isAdmin() {
        return "ADMIN".equals(roleCd);
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserNm() { return userNm; }
    public void setUserNm(String userNm) { this.userNm = userNm; }
    public String getRoleCd() { return roleCd; }
    public void setRoleCd(String roleCd) { this.roleCd = roleCd; }
    public String getDeptCd() { return deptCd; }
    public void setDeptCd(String deptCd) { this.deptCd = deptCd; }
}
