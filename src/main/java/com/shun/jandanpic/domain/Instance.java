package com.shun.jandanpic.domain;

import java.util.Date;

/**
 * shun
 * 2018-01-27
 **/
public class Instance {

    private Long id;
    private Integer fromPage;
    private Integer toPage;
    private Date createTime;
    private String status;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFromPage() {
        return fromPage;
    }
    public void setFromPage(Integer fromPage) {
        this.fromPage = fromPage;
    }

    public Integer getToPage() {
        return toPage;
    }
    public void setToPage(Integer toPage) {
        this.toPage = toPage;
    }

    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
