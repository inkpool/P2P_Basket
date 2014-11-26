package com.p2pnote.alert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class InvestRecordAlarm
{
 
    private Context mContext;
    public static final int ALARM_FIX_DATE  = 2; //每月固定时期
    public static final int ALARM_AFTER_DAY = 1;//账单后固定天数

    public InvestRecordAlarm(Context context ){
        
        mContext=context;
    }
    
    //计算alarm 时间
    public Calendar calculateAlarmDate(int ALRM_TYPE ,int  billDate  ,int repayDate ) {

        // start with now
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, 9);  //将时间设置为当日 9:00
        c.set(Calendar.MINUTE, 0);
        
        int month = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        int day = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        
        Log.e("time","intit:"+format(c.getTime()));
        Log.e("time","ALRM_TYPE:"+ALRM_TYPE);
//        int nowHour = c.get(Calendar.HOUR_OF_DAY);
//        int nowMinute = c.get(Calendar.MINUTE);

        if(ALRM_TYPE==ALARM_FIX_DATE){ //每月固定时期
            
            if(repayDate>day)   {c.add(Calendar.DATE,repayDate-day); Log.e("time","time1:"+format(c.getTime())); }
            
              else {
                    c.add(Calendar.MONTH,1);
                    
                    Log.e("time","time2:"+format(c.getTime()));
                    
                    c.add(Calendar.DATE,repayDate-day);
                    
                    Log.e("time","time3:"+format(c.getTime()));
              }  
        }
        
        else if(ALRM_TYPE==ALARM_AFTER_DAY){ //账单后固定天数
           
           c.set(Calendar.DAY_OF_MONTH,billDate); //设置为账单日
           c.add(Calendar.DATE,repayDate);//账单日后x天数
           
           int repaymonth = c.get(Calendar.MONTH) + 1; //取得还款月
           int repayday   = c.get(Calendar.DAY_OF_MONTH); //取得还款日
           
           Log.e("time","time4:"+format(c.getTime()));
          
               while (repayday<=day&&repaymonth==month){   //计算下一个还款日
                //   if(){        
                   repayday= repayDate+repayday;
                   c.add(Calendar.DATE,repayDate);//账单日后x天数
                   repaymonth = c.get(Calendar.MONTH) + 1; //取得还款月
                   
                   Log.e("time","time5:"+format(c.getTime()));
           //        }
               }
   
           Log.e("time","time6:"+format(c.getTime()));
          
        }
        
        return c;
    }
    
    
    
  //设置闹钟
    public void setAlarm(Calendar c,int alarmId)
    {
    
          /* 建立Intent和PendingIntent，来调用目标组件 */  
          Intent intent = new Intent(mContext, CreditDeadlineAlarmReceiver.class); 
          intent.putExtra("ID", alarmId);
          PendingIntent pendingIntent=PendingIntent.getBroadcast(mContext,alarmId, intent, 0);   
          AlarmManager am;   
          /* 获取闹钟管理的实例 */  
          am = (AlarmManager)mContext.getSystemService(mContext.ALARM_SERVICE);   
//          /* 设置闹钟 */  
          am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);   
          
          Toast.makeText(mContext, "将在"+format(c.getTime())+"提醒你还款", Toast.LENGTH_SHORT).show();  
         
    }
    
    public void CancelAlarmNotify(int alarmId)
    {
        Intent intent = new Intent(mContext, CreditDeadlineAlarmReceiver.class);   
        PendingIntent pendingIntent=PendingIntent.getBroadcast(mContext,alarmId, intent, 0);   
        AlarmManager am;   
        /* 获取闹钟管理的实例 */  
        am =(AlarmManager)mContext.getSystemService(mContext.ALARM_SERVICE);   
        /* 取消 */  
        am.cancel(pendingIntent);     
       // Toast.makeText(mContext, "还款提醒已取消", Toast.LENGTH_SHORT).show();    
    }

    /* 格式化字符串(7:3->07:03) */  
    private String format(int x)   
    {   
        String s = "" + x;   
        if (s.length() == 1)   
            s = "0" + s;   
        return s;    
    }  
    
    private String format(Date date){
        String str = "";
        SimpleDateFormat ymd = null;
        ymd = new SimpleDateFormat("yyyy-MM-dd");
        str = ymd.format(date); 
        return str;
    }

}
