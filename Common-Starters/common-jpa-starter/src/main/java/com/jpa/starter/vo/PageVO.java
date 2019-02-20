package com.jpa.starter.vo;

import java.util.List;

/**
 * @apiNote 分页VO包装类
 */
public class PageVO {
    private List<?> list;
    private Long totalPages;
    private Long currentPage;
    private Long pageSize;

    public PageVO() {
    }

    public PageVO(List<?> list, Long totalPages, Long currentPage, Long pageSize) {
        this.list = list;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
