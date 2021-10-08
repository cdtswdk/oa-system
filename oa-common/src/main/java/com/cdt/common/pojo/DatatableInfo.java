package com.cdt.common.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class DatatableInfo<T> {

    int page = 1;

    int pageSize = 20;

    int recordsTotal = 0;

    List<T> data = new ArrayList<>(0);

    public int getPage() {
        return this.page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getOffset() {
        System.out.println(this.page);
        return (this.page - 1) * this.pageSize;
    }

}
