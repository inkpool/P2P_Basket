package com.crowley.p2pnote.db.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserChannelCurrAmount implements Parcelable {
    private Integer id;

    private String channelName;

    private Integer userId;

    private Integer investCnt;

    private Double investAmount;

    private Double useableAmount;

    private Double holdingAmount;

    private Double holdingInterest;

    private Double overdueAmount;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInvestCnt() {
        return investCnt;
    }

    public void setUserChannelCurrAmountCnt(Integer investCnt) {
        this.investCnt = investCnt;
    }

    public Double getInvestAmount() {
        return investAmount;
    }

    public void setUserChannelCurrAmountAmount(Double investAmount) {
        this.investAmount = investAmount;
    }

    public Double getUseableAmount() {
        return useableAmount;
    }

    public void setUseableAmount(Double useableAmount) {
        this.useableAmount = useableAmount;
    }

    public Double getHoldingAmount() {
        return holdingAmount;
    }

    public void setHoldingAmount(Double holdingAmount) {
        this.holdingAmount = holdingAmount;
    }

    public Double getHoldingInterest() {
        return holdingInterest;
    }

    public void setHoldingInterest(Double holdingInterest) {
        this.holdingInterest = holdingInterest;
    }

    public Double getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(Double overdueAmount) {
        this.overdueAmount = overdueAmount;
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

	@Override
	public String toString() {
		return "UserChannelCurrAmount [id=" + id + ", channelName="
				+ channelName + ", userId=" + userId + ", investCnt="
				+ investCnt + ", investAmount=" + investAmount
				+ ", useableAmount=" + useableAmount + ", holdingAmount="
				+ holdingAmount + ", holdingInterest=" + holdingInterest
				+ ", overdueAmount=" + overdueAmount + ", gmtCreate="
				+ gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
	}

	public UserChannelCurrAmount(Integer id, String channelName,
			Integer userId, Integer UserChannelCurrAmountCnt, Double UserChannelCurrAmountAmount,
			Double useableAmount, Double holdingAmount, Double holdingInterest,
			Double overdueAmount, String gmtCreate, String gmtUpdate) {
		super();
		this.id = id;
		this.channelName = channelName;
		this.userId = userId;
		this.investCnt = UserChannelCurrAmountCnt;
		this.investAmount = UserChannelCurrAmountAmount;
		this.useableAmount = useableAmount;
		this.holdingAmount = holdingAmount;
		this.holdingInterest = holdingInterest;
		this.overdueAmount = overdueAmount;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
	}
    
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(channelName);		
		dest.writeInt(userId);
		dest.writeInt(investCnt);
		dest.writeDouble(investAmount);
		dest.writeDouble(useableAmount);
		dest.writeDouble(holdingAmount);
		dest.writeDouble(holdingInterest);
		dest.writeDouble(overdueAmount);
		dest.writeString(gmtCreate);
		dest.writeString(gmtUpdate);
	}

	public static final Parcelable.Creator<UserChannelCurrAmount> CREATOR = new Parcelable.Creator<UserChannelCurrAmount>() {
		public UserChannelCurrAmount createFromParcel(Parcel in) {
			return new UserChannelCurrAmount(in);
		}

		public UserChannelCurrAmount[] newArray(int size) {
			return new UserChannelCurrAmount[size];
		}
	};

	private UserChannelCurrAmount(Parcel in) {
		id = in.readInt();
		channelName = in.readString();
		userId = in.readInt();
		investCnt = in.readInt();
		investAmount = in.readDouble();
		useableAmount = in.readDouble();
		holdingAmount = in.readDouble();
		holdingInterest = in.readDouble();
		overdueAmount = in.readDouble();
		gmtCreate = in.readString();
		gmtUpdate = in.readString();
	}

	public UserChannelCurrAmount() {
		// TODO Auto-generated constructor stub
		super();
	}
}