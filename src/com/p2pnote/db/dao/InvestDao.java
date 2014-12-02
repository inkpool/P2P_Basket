package com.p2pnote.db.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.p2pnote.db.MyDbHelper;
import com.p2pnote.db.model.Invest;

public class InvestDao {
	private MyDbHelper db=null;
	
	public InvestDao(MyDbHelper db) {
		// TODO Auto-generated constructor stub
		this.db=db;
	}

	public void add(Invest invest) {

		/*
		 * String sqlString=
		 * "insert into user_invest_record (invest_id, user_id, invest_date,"
		 * +"repayment_ending_date, p2p_channel_name, amount,"
		 * +"interest_rate_min, interest_rate_max, bonus_rate,"
		 * +"repayment_type_id, is_guarantee, is_mortgage,"
		 * +"is_tradeable, comment, gmt_create," +"gmt_update)"
		 * +"values (?,?,?,?,?,?,?,?,?,?," +"?,?,?,?,?,?,?)";
		 * db.execSQL(sqlString,new Object(invest)); db.close();
		 */

		ContentValues values = new ContentValues();
		// values.put("invest_id", invest.getInvestId());
		// values.put("user_id", invest.getUserId());
		values.put("invest_date", invest.getInvestDate());
		values.put("repayment_ending_date", invest.getRepaymentEndingDate());
		values.put("p2p_channel_name", invest.getP2pChannelName());
		values.put("invest_name", invest.getInvestName());
		values.put("amount", invest.getAmount());
		values.put("interest_rate_min", invest.getInterestRateMin());
		values.put("interest_rate_max", invest.getInterestRateMax());
		values.put("guarantee_type", invest.getGuaranteeType());
		values.put("repayment_type", invest.getRepaymentType());
		values.put("comment", invest.getComment());
		//values.put("is_template",invest.getIs_template());
		values.put("gmt_create", invest.getGmtCreate());
		values.put("gmt_update", invest.getGmtUpdate());

		long id = db.insert("user_invest_record", values);
	}

	public boolean find(String name) {
		Cursor cursor = db.rawQuery(
				"select * from user_invest_record where name=?",
				new String[] { name });
		boolean result = cursor.moveToNext();
		cursor.close();
		return result;
	}

	public void update(Invest invest, Long investId) {
		db.execSQL(
				"update user_invest_record set invest_name = ?,"
						+ "user_id = ?,"
						+
						// "invest_type_id = ?,"+
						// "invest_status = ?,"+
						"invest_date = ?," + "repayment_ending_date = ?,"
						+ "p2p_channel_name = ?," + "amount = ?,"
						+ "interest_rate_min = ?," + "interest_rate_max = ?,"
						+ "repayment_type = ?," + "guarantee_type = ?,"
						+ "gmt_create = ?," + "gmt_update = ?,"
						+ "comment = ? where invest_id=?",
				new Object[] { invest.getInvestName(), invest.getUserId(),
						invest.getInvestDate(),
						invest.getRepaymentEndingDate(),
						invest.getP2pChannelName(), invest.getAmount(),
						invest.getInterestRateMin(),
						invest.getInterestRateMax(), invest.getRepaymentType(),
						invest.getGuaranteeType(), invest.getGmtCreate(),
						invest.getGmtUpdate(), invest.getComment(), investId });
	}
	
	public void saveAsTemplate(Invest invest, Long investId) {
		db.execSQL(
				"update user_invest_record set is_template = 1 "+
				"where invest_id=?",
				new Object[] { investId });
	}

	public void delete(Long investId) {
		db.execSQL("delete from user_invest_record where invest_id=?",
				new Object[] { investId });
	}

	public ArrayList<Invest> findAll() {
		ArrayList<Invest> invests = new ArrayList<Invest>();
		Cursor cursor = db.rawQuery(
				"select * from user_invest_record order by invest_id desc",
				null);
		while (cursor.moveToNext()) {
			Invest p = new Invest(cursor);
			invests.add(p);
		}
		cursor.close();
		return invests;

	}

}
