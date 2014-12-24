package com.crowley.p2pnote.db;

public class MyDbInfo {

	private static String TableNames[] = { "user_invest_record",
			"user_channel_curr_amount", "user_channel_amount_log",
			"channel_dim", };// 表名

	private static String FieldNames[][] = {
		/*
		 * 主力表，user_invest_record，记录所有的投资记录
		 * 表的字段分别为
		 * invest_id:id号
		 * user_id：用户id，如果是注册id，此字段有值，否则此字段为空
		 * invest_name:具体的投资项目 如富赢人生
		 * p2p_channel_name: 平台名称，如陆金所
		 * invest_status: 投资状态，0投资中 1已到帐 2已逾期
		 * money:投资的本金
		 * interest_rate_min:浮动收益率下限 如果是固定收益率 该值为0 格式为小数 即15%的话该值为0.15
		 * interest_rate_max:浮动收益率上限 如果是固定收益率 该值为其收益率
		 * bonus_rate: 额外奖励，预留字段，初期先不考虑
		 * repayment_type:计息方式 0为到期还本息 1为 按月还本息 2为按月只还息
		 * guarantee_type:是否有担保，0担保本金 1担保本金和利息 2无担保
		 * invest_date:计息时间 格式为2014-12-26的字符串
		 * repayment_ending_date:到期时间 格式为2014-12-26的字符串	 
		 * comment：备注，预留字段
		 * is_template：是否为模板记录，用于生成模板
		 * gmt_create： 记录生成时间
		 * gmt_modify： 记录修改时间
		 */	
			{ "invest_id", "invest_name", "user_id", "invest_type_id",
					"invest_status", "invest_date", "repayment_ending_date",
					"p2p_channel_id","p2p_channel_name", "amount", "interest_rate_min",
					"interest_rate_max", "bonus_rate", "repayment_type",
					"guarantee_type", "comment","is_template", "gmt_create", "gmt_update" },

					/*
					 * 平台余额表，user_channel_curr_amount，记录每个平台当前的可用余额，这个表我再考虑考虑，是不是设计得过于复杂了
					 * 表的字段分别为
					 */
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
					"double DEFAULT NULL", "integer DEFAULT 0", "integer DEFAULT 0",
					"varchar(2000)","integer DEFAULT 0", "varchar(20)", "varchar(20)" },
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
