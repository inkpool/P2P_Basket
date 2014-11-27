11-23 19:28:06.560: D/com.p2pnote.db(950): CREATE TABLE user_invest_record (invest_id INTEGER PRIMARY KEY AUTOINCREMENT,invest_name varchar(300),user_id integer,invest_type_id integer DEFAULT 1,invest_status varchar(30) DEFAULT 'Í¶×ÊÖÐ',invest_date varchar(20) NOT NULL,repayment_ending_date varchar(20),p2p_channel_name varchar(300) NOT NULL,amount double NOT NULL,interest_rate_min double DEFAULT NULL,interest_rate_max double DEFAULT NULL,bonus_rate double DEFAULT NULL,repayment_type varchar(30),guarantee_type varchar(30),comment varchar(2000),gmt_create varchar(20),gmt_update varchar(20))


11-23 19:28:06.580: D/com.p2pnote.db(950): CREATE TABLE user_channel_curr_amount (id INTEGER PRIMARY KEY AUTOINCREMENT,channel_name varchar(300) NOT NULL,user_id integer,invest_cnt integer DEFAULT 0,invest_amount double DEFAULT 0,useable_amount double DEFAULT 0,holding_amount double DEFAULT 0,holding_interest double DEFAULT 0,overdue_amount double DEFAULT 0,gmt_create varchar(20),gmt_update varchar(20))


11-23 19:28:06.690: D/com.p2pnote.db(950): CREATE TABLE user_channel_amount_log (id INTEGER PRIMARY KEY AUTOINCREMENT,channel_name varchar(300) NOT NULL,user_id integer,change_type varchar(10) NOT NULL,change_amount double DEFAULT 0,amount double DEFAULT 0,gmt_create varchar(20))


11-23 19:28:06.700: D/com.p2pnote.db(950): CREATE TABLE channel_dim (id INTEGER PRIMARY KEY AUTOINCREMENT,channel_name varchar(300) NOT NULL,invest_cnt integer DEFAULT 0,amount double DEFAULT 0,gmt_create varchar(20),gmt_update varchar(20))
