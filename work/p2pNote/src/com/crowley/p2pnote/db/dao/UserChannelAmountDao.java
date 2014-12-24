package com.crowley.p2pnote.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.crowley.p2pnote.db.MyDbHelper;
import com.crowley.p2pnote.db.model.UserChannelCurrAmount;

public class UserChannelAmountDao {
	private MyDbHelper db=null;
	
	public UserChannelAmountDao(MyDbHelper db) {
		// TODO Auto-generated constructor stub
		this.db=db;
	}

	public void add(UserChannelCurrAmount UserChannelCurrAmount) {

		ContentValues values = new ContentValues();
		values.put("id", UserChannelCurrAmount.getId());
		values.put("user_id", UserChannelCurrAmount.getUserId());
		values.put("channel_name", UserChannelCurrAmount.getChannelName());
		values.put("invest_cnt", UserChannelCurrAmount.getInvestCnt());
		values.put("invest_amount", UserChannelCurrAmount.getInvestAmount());
		values.put("useable_amount", UserChannelCurrAmount.getUseableAmount());
		values.put("holding_amount", UserChannelCurrAmount.getHoldingAmount());
		values.put("holding_interest", UserChannelCurrAmount.getHoldingInterest());
		values.put("overdue_amount", UserChannelCurrAmount.getOverdueAmount());
		values.put("gmt_create", UserChannelCurrAmount.getGmtCreate());
		values.put("gmt_update", UserChannelCurrAmount.getGmtUpdate());

		long id = db.insert("user_channel_curr_amount", values);
	}

	public int find(String channel_name) {
		int result=0;
		Cursor cursor = db.rawQuery(
				"select id from user_channel_curr_amount where channel_name=? limit 1",
				new String[] { channel_name });
		while (cursor.moveToNext()) {
			result=cursor.getInt(0);
		}
		cursor.close();
		return result;
	}

	public void update(UserChannelCurrAmount UserChannelCurrAmount, int UserChannelCurrAmountId) {
		db.execSQL(
				"update user_channel_curr_amount set channel_name = ?,"
						+"invest_cnt = ?," 
						+ "invest_amount = ?,"
						+ "useable_amount = ?,"
						+ "holding_amount = ?,"
						+ "holding_interest = ?,"
						+ "overdue_amount = ?,"
						+ "gmt_create = ?," + "gmt_update = ?",
				new Object[] { UserChannelCurrAmount.getChannelName(), 
						UserChannelCurrAmount.getInvestCnt(),
						UserChannelCurrAmount.getInvestAmount(),
						UserChannelCurrAmount.getUseableAmount(),
						UserChannelCurrAmount.getHoldingAmount(),
						UserChannelCurrAmount.getHoldingInterest(),
						UserChannelCurrAmount.getOverdueAmount(),
						UserChannelCurrAmount.getGmtCreate(),
						UserChannelCurrAmount.getGmtUpdate(), UserChannelCurrAmountId });
	}
	
	public void addNewInvest(double invest_amount,double holding_amount,double holding_interest,String gmt_update,int UserChannelCurrAmountId) {
		db.execSQL(
				"update user_channel_curr_amount set invest_cnt = invest_cnt+1," 
						+ "invest_amount = invest_amount+?,"
						+ "holding_amount = holding_amount+?,"
						+ "holding_interest = holding_interest+?,"
						+ "gmt_update = ?"
						+ "where id= ?",
				new Object[] { invest_amount,holding_amount,holding_interest,gmt_update, UserChannelCurrAmountId });
	}

	public void delete(Long UserChannelCurrAmountId) {
		db.execSQL("delete from user_channel_curr_amount where id=?",
				new Object[] { UserChannelCurrAmountId });
	}

/*	public ArrayList<UserChannelCurrAmount> findAll() {
		ArrayList<UserChannelCurrAmount> UserChannelCurrAmounts = new ArrayList<UserChannelCurrAmount>();
		Cursor cursor = db.rawQuery(
				"select * from user_channel_curr_amount",
				null);
		while (cursor.moveToNext()) {
			UserChannelCurrAmount p = new UserChannelCurrAmount(cursor);
			UserChannelCurrAmounts.add(p);
		}
		cursor.close();
		return UserChannelCurrAmounts;

	}*/
	
}
