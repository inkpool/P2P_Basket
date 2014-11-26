package com.p2pnote.db.model;

import java.util.Date;

import android.R.integer;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Invest implements Parcelable {
	private Long investId;

	private Long userId;

	private String investDate;
	private integer invest_type_id;
	private String invest_status;

	private String repaymentEndingDate;

	private String p2pChannelName;
	private String investName;

	private Double amount;

	private Double interestRateMin;

	private Double interestRateMax;

	private String repaymentType;

	private String guaranteeType;

	private String gmtCreate;

	private String gmtUpdate;

	private String comment;
	
	public integer getInvest_type_id() {
		return invest_type_id;
	}

	public void setInvest_type_id(integer invest_type_id) {
		this.invest_type_id = invest_type_id;
	}

	public String getInvest_status() {
		return invest_status;
	}

	public void setInvest_status(String invest_status) {
		this.invest_status = invest_status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment.trim();
	}

	public Long getInvestId() {
		return investId;
	}

	public void setInvestId(Long investId) {
		this.investId = investId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getP2pChannelName() {
		return p2pChannelName;
	}

	public void setP2pChannelName(String p2pChannelName) {
		this.p2pChannelName = p2pChannelName == null ? null : p2pChannelName
				.trim();
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getInterestRateMin() {
		return interestRateMin;
	}

	public void setInterestRateMin(Double interestRateMin) {
		this.interestRateMin = interestRateMin;
	}

	public Double getInterestRateMax() {
		return interestRateMax;
	}

	public void setInterestRateMax(Double interestRateMax) {
		this.interestRateMax = interestRateMax;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public String getInvestDate() {
		return investDate;
	}

	public void setInvestDate(String investDate) {
		this.investDate = investDate;
	}

	public String getRepaymentEndingDate() {
		return repaymentEndingDate;
	}

	public void setRepaymentEndingDate(String repaymentEndingDate) {
		this.repaymentEndingDate = repaymentEndingDate;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(String gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	public String getInvestName() {
		return investName;
	}

	public void setInvestName(String investName) {
		this.investName = investName;
	}

	public Invest(Long investId, Long userId, String investDate,
			String repaymentEndingDate, String p2pChannelName,
			String investName, Double amount, Double interestRateMin,
			Double interestRateMax, String repaymentType, String guaranteeType,
			String gmtCreate, String gmtUpdate, String comment) {
		super();
		this.investId = investId;
		this.userId = userId;
		this.investDate = investDate;
		this.repaymentEndingDate = repaymentEndingDate;
		this.p2pChannelName = p2pChannelName;
		this.investName = investName;
		this.amount = amount;
		this.interestRateMin = interestRateMin;
		this.interestRateMax = interestRateMax;
		this.repaymentType = repaymentType;
		this.guaranteeType = guaranteeType;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
		this.comment = comment;
	}

	public Invest(Cursor cursor) {
		super();
		this.investId = cursor.getLong(cursor.getColumnIndex("invest_id"));
		this.userId = cursor.getLong(cursor.getColumnIndex("user_id"));
		this.investDate = cursor
				.getString(cursor.getColumnIndex("invest_date"));
		this.repaymentEndingDate = cursor.getString(cursor
				.getColumnIndex("repayment_ending_date"));
		this.p2pChannelName = cursor.getString(cursor
				.getColumnIndex("p2p_channel_name"));
		this.investName = cursor
				.getString(cursor.getColumnIndex("invest_name"));
		this.amount = cursor.getDouble(cursor.getColumnIndex("amount"));
		this.interestRateMin = cursor.getDouble(cursor
				.getColumnIndex("interest_rate_min"));
		this.interestRateMax = cursor.getDouble(cursor
				.getColumnIndex("interest_rate_max"));
		this.repaymentType = cursor.getString(cursor
				.getColumnIndex("repayment_type"));
		this.guaranteeType = cursor.getString(cursor
				.getColumnIndex("guarantee_type"));
		this.gmtCreate = cursor.getString(cursor.getColumnIndex("gmt_create"));
		this.gmtUpdate = cursor.getString(cursor.getColumnIndex("gmt_modify"));
		this.comment = cursor.getString(cursor.getColumnIndex("comment"));
	}

	public Invest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Invest [investId=" + investId + ", userId=" + userId
				+ ", investDate=" + investDate + ", repaymentEndingDate="
				+ repaymentEndingDate + ", p2pChannelName=" + p2pChannelName
				+ ", investName=" + investName + ", amount=" + amount
				+ ", interestRateMin=" + interestRateMin + ", interestRateMax="
				+ interestRateMax + ", repaymentType=" + repaymentType
				+ ", guaranteeType=" + guaranteeType + ", gmtCreate="
				+ gmtCreate + ", gmtUpdate=" + gmtUpdate + ", comment="
				+ comment + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(investId);
		if (userId!=null)
			dest.writeLong(userId);
		dest.writeString(investDate);
		dest.writeString(repaymentEndingDate);
		dest.writeString(p2pChannelName);
		dest.writeString(investName);
		dest.writeDouble(amount);
		dest.writeDouble(interestRateMin);
		dest.writeDouble(interestRateMax);
		dest.writeString(repaymentType);
		dest.writeString(guaranteeType);
		dest.writeString(gmtCreate);
		dest.writeString(gmtUpdate);
		dest.writeString(comment);

	}

	public static final Parcelable.Creator<Invest> CREATOR = new Parcelable.Creator<Invest>() {
		public Invest createFromParcel(Parcel in) {
			return new Invest(in);
		}

		public Invest[] newArray(int size) {
			return new Invest[size];
		}
	};

	private Invest(Parcel in) {
		investId = in.readLong();
		userId = in.readLong();
		investDate = in.readString();
		repaymentEndingDate = in.readString();
		p2pChannelName = in.readString();
		investName = in.readString();
		amount = in.readDouble();
		interestRateMin = in.readDouble();
		interestRateMax = in.readDouble();
		repaymentType = in.readString();
		guaranteeType = in.readString();
		gmtCreate = in.readString();
		gmtUpdate = in.readString();
		comment = in.readString();
	}
}
