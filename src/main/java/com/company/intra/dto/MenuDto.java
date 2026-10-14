package com.company.intra.dto;

/** 메뉴 DTO */
public class MenuDto {
    private String menuId;
    private String upMenuId;
    private String menuNm;
    private String url;
    private int sortNo;
    private String useYn;

    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }
    public String getUpMenuId() { return upMenuId; }
    public void setUpMenuId(String upMenuId) { this.upMenuId = upMenuId; }
    public String getMenuNm() { return menuNm; }
    public void setMenuNm(String menuNm) { this.menuNm = menuNm; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public int getSortNo() { return sortNo; }
    public void setSortNo(int sortNo) { this.sortNo = sortNo; }
    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
}
