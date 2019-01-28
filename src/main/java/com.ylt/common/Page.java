package com.ylt.common;


import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer startRecord;
    private Integer pageSize = 5;
    private Integer allCount;
    private Integer currentPage = 1;
    private Integer allPage;
    private List<T> items;

    public Page() {
    }

    public Integer getStartRecord() {
        return this.startRecord;
    }

    public void setStartRecord(Integer startRecord) {
        this.startRecord = startRecord;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getAllCount() {
        return this.allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getAllPage() {
        return this.allPage;
    }

    public void setAllPage(Integer allPage) {
        this.allPage = allPage;
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public static long getSerialversionuid() {
        return 1L;
    }
}

