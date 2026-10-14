package com.company.intra.common;

import java.util.List;

/** 페이징 표준 객체 - 모든 목록 화면에서 동일 규격 사용 */
public class Page<T> {

    private List<T> list;
    private int pageNo = 1;    // 1-based
    private int pageSize = 10;
    private long totalCount;

    public Page() {}

    public Page(List<T> list, int pageNo, int pageSize, long totalCount) {
        this.list = list;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        if (pageSize <= 0) return 0;
        return (int) Math.ceil((double) totalCount / pageSize);
    }

    public int getOffset() {
        return (Math.max(pageNo, 1) - 1) * pageSize;
    }

    public List<T> getList() { return list; }
    public void setList(List<T> list) { this.list = list; }
    public int getPageNo() { return pageNo; }
    public void setPageNo(int pageNo) { this.pageNo = pageNo; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public long getTotalCount() { return totalCount; }
    public void setTotalCount(long totalCount) { this.totalCount = totalCount; }
}
