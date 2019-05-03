package com.zhilingsd.collection.common.result.info;

import com.zhilingsd.collection.common.result.ListResult;

/**
 * 分页对象
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 16:55
 */
public class Page extends ListResult {

    /**
     * 当前页码
     */
    private int curPage = 1;

    /**
     * 每页项数
     */
    private int pageSize = 20;

    /**
     * 记录总数
     */
    private long totalRecord;

    /**
     * 总页数
     */
    private long totalPage;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (curPage - 1) * pageSize;
    }

    public long getTotalPage() {
        return this.totalRecord == 0 ?
                0 : (this.totalRecord / this.pageSize) + (this.totalRecord % this.pageSize > 0 ? 1 : 0);
    }
}
