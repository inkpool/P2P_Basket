
package com.p2pnote.alert;

import com.p2pnote.db.MyDbHelper;
import com.p2pnote.ui.R;
import com.p2pnote.ui.SplashScreenActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

public class CreditDeadlineAlarmReceiver extends BroadcastReceiver {

    private MyDbHelper db = null;
    private String cardNum;
    private int alarmId; //作为闹钟ID
    private int alarmType=-1;
    private int billDate=-1;             //getNumbers;
    private int repayDate=-1;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	   
        Intent mIntent = new Intent(context, SplashScreenActivity.class); //SplashScreenActivity  CreditDeadlineAlarmReceiver
        int id=intent.getIntExtra("ID", -1);
       
        Log.e("time broad","ID:"+id);
        
        //"CARD_NUM","BANK_ID","CARD_TYPE","BILL_DATE","REPAYMENT_TYPE","REPAYMENT_DATE",
        db = MyDbHelper.getInstance(context);
        db.open();
        Cursor cursor = db.rawQuery("select  *  from TBL_BANK_CARD_INFO where ALARM_ID=? " ,new String[]{
                String.valueOf(id)});
        while (cursor.moveToNext()) {   
                Log.e("hzm",  "ge"+cursor.getCount());
                
                 cardNum=cursor.getString(cursor.getColumnIndex("CARD_NUM"));
                 alarmId=cursor.getInt(cursor.getColumnIndex("ALARM_ID")); //作为闹钟ID
                 alarmType=cursor.getInt(cursor.getColumnIndex("REPAYMENT_TYPE"));
                 billDate=cursor.getInt(cursor.getColumnIndex("BILL_DATE"));             //getNumbers;
                 repayDate=cursor.getInt(cursor.getColumnIndex("REPAYMENT_DATE"));
                 
        }
        cursor.close();
        db.close();
        InvestRecordAlarm alarm=new InvestRecordAlarm(context);
        
        Log.e("time broad","ALRM_TYPE:"+alarmType);
        Log.e("time broad","billDate:"+billDate);
        Log.e("time broad","repayDate:"+repayDate);
        if(alarmType!=-1&&billDate!=-1&&repayDate!=-1)
        alarm.setAlarm(alarm.calculateAlarmDate(alarmType, billDate, repayDate), alarmId);
        
        
       	mNotification = new Notification(R.drawable.icon,"该还款啦...",System.currentTimeMillis());   
       	////将使用默认的声音来提醒用户  
        mNotification.defaults = Notification.DEFAULT_SOUND;   
        mNotification.flags=Notification.FLAG_AUTO_CANCEL;
        mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);   
        
        //这里需要设置Intent.FLAG_ACTIVITY_NEW_TASK属性    
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);       
        PendingIntent mContentIntent =PendingIntent.getActivity(context,0, mIntent, 0);   
        //这里必需要用setLatestEventInfo(上下文,标题,内容,PendingIntent)不然会报错. 
        mNotification.setLatestEventInfo(context, "卡号:"+cardNum, "亲！已到还款期限，请及时还款哦...", mContentIntent);   
        //这里发送通知(消息ID,通知对象)      
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);  
	}
	
    //for notification
    private Notification         mNotification;   
    private NotificationManager  mNotificationManager;   
    private final static int     NOTIFICATION_ID = 0x0001;

}
