package com.lyc.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Objects;

/**
 * 分页工具类
 *
 * @author liuYichang
 */
public class PageUtils {

    /**
     * 创建一个本地线程，保证分页信息不会相互干扰
     */
    private static final ThreadLocal<Page<?>> PAGE_HOLDER = new ThreadLocal<>();

    public static void setCurrentPage(Page<?> page) {
        PAGE_HOLDER.set(page);
    }

    /**
     * @return 从本地线程中获取page
     */
    public static Page<?> getPage() {
        Page<?> page = PAGE_HOLDER.get();
        if (Objects.isNull(page)) {
            setCurrentPage(new Page<>());
        }
        return PAGE_HOLDER.get();
    }

    public static Long getCurrent() {
        return getPage().getCurrent();
    }

    /**
     * @return 当页数据量
     */
    public static Long getSize() {
        return getPage().getSize();
    }

    /**
     * @return 该页数据的起始大小
     */
    public static Long getLimit() {
        return (getCurrent() - 1) * getSize();
    }

    /**
     * 移除这个线程
     */
    public static void remove() {
        PAGE_HOLDER.remove();
    }

}
