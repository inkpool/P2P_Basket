package com.p2pnote.db;

public class MyDbInfo {

	private static String TableNames[] = { "user_invest_record",
			"user_channel_curr_amount", "user_channel_amount_log",
			"channel_dim", };// 表名

	private static String FieldNames[][] = {
			{ "invest_id", "invest_name", "user_id", "invest_type_id",
					"invest_status", "invest_date", "repayment_ending_date",
					"p2p_channel_id","p2p_channel_name", "amount", "interest_rate_min",
					"interest_rate_max", "bonus_rate", "repayment_type",
					"guarantee_type", "comment", "gmt_create", "gmt_update" },
			{ "id", "channel_name", "user_id","invest_cnt","invest_amount","useable_amount","holding_amount",
					"holding_interest","overdue_amount","gmt_create","gmt_update" },
			{ "id", "channel_name", "user_id", "change_type", "change_amount",
					"amount", "gmt_create" },
			{ "id", "channel_name", "invest_cnt", "amount", "gmt_create",
					"gmt_update" }, };// 字段名

	private static String FieldTypes[][] = {
			// 字段类型
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "varchar(300)", "integer",
					"integer DEFAULT 1", "varchar(30) DEFAULT '投资中'",
					"varchar(20) NOT NULL", "varchar(20)","integer DEFAULT NULL",
					"varchar(300) NOT NULL", "double NOT NULL",
					"double DEFAULT NULL", "double DEFAULT NULL",
					"double DEFAULT NULL", "varchar(30)", "varchar(30)",
					"varchar(2000)", "varchar(20)", "varchar(20)" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "varchar(300) NOT NULL",
					"integer", "integer DEFAULT 0","double DEFAULT 0",
					"double DEFAULT 0","double DEFAULT 0",
					"double DEFAULT 0","double DEFAULT 0",
					"varchar(20)", "varchar(20)" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "varchar(300) NOT NULL",
					"integer", "varchar(10) NOT NULL", "double DEFAULT 0", "double DEFAULT 0","varchar(20)" },
			{ "INTEGER PRIMARY KEY AUTOINCREMENT", "varchar(300) NOT NULL",
					"integer DEFAULT 0", "double DEFAULT 0", "varchar(20)", "varchar(20)" }, };

	public MyDbInfo() {
		// TODO Auto-generated constructor stub
	}

	public static String[] getTableNames() {
		return TableNames;
	}

	public static String[][] getFieldNames() {
		return FieldNames;
	}

	public static String[][] getFieldTypes() {
		return FieldTypes;
	}

}
