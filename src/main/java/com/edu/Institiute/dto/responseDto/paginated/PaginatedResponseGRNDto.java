package com.edu.Institiute.dto.responseDto.paginated;

import com.edu.Institiute.dto.responseDto.GRNResponseDto;

import java.util.List;

public class PaginatedResponseGRNDto {
    private long count;
    private List<GRNResponseDto> dataList;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;

    public PaginatedResponseGRNDto() {
    }

    public PaginatedResponseGRNDto(long count, List<GRNResponseDto> dataList) {
        this.count = count;
        this.dataList = dataList;
    }

    public PaginatedResponseGRNDto(long count, List<GRNResponseDto> dataList,
                                          int totalPages, long totalElements, int currentPage,
                                          int pageSize, boolean hasNext, boolean hasPrevious) {
        this.count = count;
        this.dataList = dataList;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    // Getters and Setters
    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }

    public List<GRNResponseDto> getDataList() { return dataList; }
    public void setDataList(List<GRNResponseDto> dataList) { this.dataList = dataList; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public boolean isHasNext() { return hasNext; }
    public void setHasNext(boolean hasNext) { this.hasNext = hasNext; }

    public boolean isHasPrevious() { return hasPrevious; }
    public void setHasPrevious(boolean hasPrevious) { this.hasPrevious = hasPrevious; }

}
