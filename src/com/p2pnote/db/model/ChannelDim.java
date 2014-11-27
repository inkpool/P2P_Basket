package com.p2pnote.db.model;

public class ChannelDim {
    private Integer id;

    private String channelName;

    private Integer investCnt;

    private Double amount;

    private String gmtCreate;

    private String gmtUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public Integer getInvestCnt() {
        return investCnt;
    }

    public void setInvestCnt(Integer investCnt) {
        this.investCnt = investCnt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate == null ? null : gmtCreate.trim();
    }

    public String getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(String gmtUpdate) {
        this.gmtUpdate = gmtUpdate == null ? null : gmtUpdate.trim();
    }
}