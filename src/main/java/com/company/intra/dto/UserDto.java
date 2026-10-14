package com.company.intra.dto;

/** 사용자 DTO - Lombok 없이 getter/setter 직접 작성 (사내 표준) */
public class UserDto {
    private String userId;
    private String userNm;
    private String deptCd;
    private String deptNm;
    private String roleCd;
    private String useYn;
    private String regDt;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserNm() { return userNm; }
    public void setUserNm(String userNm) { this.userNm = userNm; }
    public String getDeptCd() { return deptCd; }
    public void setDeptCd(String deptCd) { this.deptCd = deptCd; }
    public String getDeptNm() { return deptNm; }
    public void setDeptNm(String deptNm) { this.deptNm = deptNm; }
    public String getRoleCd() { return roleCd; }
    public void setRoleCd(String roleCd) { this.roleCd = roleCd; }
    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public String getRegDt() { return regDt; }
    public void setRegDt(String regDt) { this.regDt = regDt; }
}
