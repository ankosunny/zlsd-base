package com.zhilingsd.base.common.result;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageSerializable;
import com.google.common.collect.Lists;
import com.zhilingsd.base.common.utils.collection.GV;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * Description:
 *
 * @Author zengkai
 * @Date 2019/4/19 15:05
 */
@Data
public class PageBean<T> extends PageSerializable<T> {

    @ApiModelProperty(value = "当前页")
    private int pageNum;

    @ApiModelProperty(value = "每页的数量")
    private int pageSize;

    @ApiModelProperty(value = "当前页的数量")
    private int size;

    //由于startRow和endRow不常用，这里说个具体的用法
    //可以在页面中"显示startRow到endRow 共size条数据"

    @ApiModelProperty(value = "当前页面第一个元素在数据库中的行号")
    private int startRow;

    @ApiModelProperty(value = "当前页面最后一个元素在数据库中的行号")
    private int endRow;

    @ApiModelProperty(value = "总页数")
    private int pages;

    @ApiModelProperty(value = "前一页")
    private int prePage;

    @ApiModelProperty(value = "下一页")
    private int nextPage;

    @ApiModelProperty(value = "是否为第一页")
    private boolean isFirstPage = false;

    @ApiModelProperty(value = "是否为最后一页")
    private boolean isLastPage = false;

    @ApiModelProperty(value = "是否有前一页")
    private boolean hasPreviousPage = false;

    @ApiModelProperty(value = "是否有下一页")
    private boolean hasNextPage = false;

    @ApiModelProperty(value = "导航页码数")
    private int navigatePages;

    @ApiModelProperty(value = "所有导航页号")
    private int[] navigatepageNums;

    @ApiModelProperty(value = "导航条上的第一页")
    private int navigateFirstPage;

    @ApiModelProperty(value = "导航条上的最后一页")
    private int navigateLastPage;

    public PageBean() {
    }

    /**
     * 包装Page对象
     *
     * @param list
     */
    public PageBean(List<T> list) {
        this(list, 8);
    }

    /**
     * 包装Page对象
     *
     * @param list
     */
    public PageBean(List<T> list,Long totalNum) {
        this(list, 8,totalNum);
    }

    /**
     * 包装Page对象
     *
     * @param list          page结果
     * @param navigatePages 页码数量
     */
    public PageBean(List<T> list, int navigatePages) {
        super(list);
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();

            this.pages = page.getPages();
            this.size = page.size();
            //由于结果是>startRow的，所以实际的需要+1
            if (this.size == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                //计算实际的endRow（最后一页的时候特殊）
                this.endRow = this.startRow - 1 + this.size;
            }
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.pages = this.pageSize > 0 ? 1 : 0;
            this.size = list.size();
            this.startRow = 0;
            this.endRow = list.size() > 0 ? list.size() - 1 : 0;
        }
        if (list instanceof Collection) {
            this.navigatePages = navigatePages;
            //计算导航页
            calcNavigatepageNums();
            //计算前后页，第一页，最后一页
            calcPage();
            //判断页面边界
            judgePageBoudary();
        }
    }

    /**
     * 包装Page对象
     *
     * @param list          page结果
     * @param navigatePages 页码数量
     */
    public PageBean(List<T> list, int navigatePages , long totalNum) {
        super(list);
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();

            this.pages = page.getPages();
            this.size = page.size();
            //由于结果是>startRow的，所以实际的需要+1
            if (this.size == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                //计算实际的endRow（最后一页的时候特殊）
                this.endRow = this.startRow - 1 + this.size;
            }
        } else if (list instanceof Collection) {
            this.pageNum = totalNum>0?1:0;
            this.pageSize = list.size();
            this.total = totalNum;
            this.pages = this.pageSize>0?(GV.i(this.total)+this.pageSize-1)/this.pageSize:0;
            this.size = list.size();
            this.startRow = 0;
            this.endRow = list.size() > 0 ? list.size() - 1 : 0;
        }
        if (list instanceof Collection) {
            this.navigatePages = navigatePages;
            //计算导航页
            calcNavigatepageNums();
            //计算前后页，第一页，最后一页
            calcPage();
            //判断页面边界
            judgePageBoudary();
        }
    }

    public static <T> com.github.pagehelper.PageInfo<T> of(List<T> list) {
        return new com.github.pagehelper.PageInfo<T>(list);
    }

    public static <T> com.github.pagehelper.PageInfo<T> of(List<T> list, int navigatePages) {
        return new com.github.pagehelper.PageInfo<T>(list, navigatePages);
    }

    public static void main(String[] args) {
        PageBean pageBean = new PageBean(Lists.newArrayList(),0L);
        System.out.println(pageBean);
    }

    /**
     * 计算导航页
     */
    private void calcNavigatepageNums() {
        //当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
    }

    /**
     * 计算前后页，第一页，最后一页
     */
    private void calcPage() {
        if (navigatepageNums != null && navigatepageNums.length > 0) {
            navigateFirstPage = navigatepageNums[0];
            navigateLastPage = navigatepageNums[navigatepageNums.length - 1];
            if (pageNum > 1) {
                prePage = pageNum - 1;
            }
            if (pageNum < pages) {
                nextPage = pageNum + 1;
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages || pages == 0;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageBean{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", size=").append(size);
        sb.append(", startRow=").append(startRow);
        sb.append(", endRow=").append(endRow);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", list=").append(list);
        sb.append(", prePage=").append(prePage);
        sb.append(", nextPage=").append(nextPage);
        sb.append(", isFirstPage=").append(isFirstPage);
        sb.append(", isLastPage=").append(isLastPage);
        sb.append(", hasPreviousPage=").append(hasPreviousPage);
        sb.append(", hasNextPage=").append(hasNextPage);
        sb.append(", navigatePages=").append(navigatePages);
        sb.append(", navigateFirstPage=").append(navigateFirstPage);
        sb.append(", navigateLastPage=").append(navigateLastPage);
        sb.append(", navigatepageNums=");
        if (navigatepageNums == null) {
            sb.append("null");
        } else {
            sb.append('[');
            for (int i = 0; i < navigatepageNums.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(navigatepageNums[i]);
            }
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}

