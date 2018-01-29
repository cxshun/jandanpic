package com.shun.jandanpic.domain;

import java.util.Date;

/**
 * shun
 * 2018-01-27
 **/
public class Instance {

    private Long id;
    private Integer page;
    private Integer size;
    private Date createTime;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
