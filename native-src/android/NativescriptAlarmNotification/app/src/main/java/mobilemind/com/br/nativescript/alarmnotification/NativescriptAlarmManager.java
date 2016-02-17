
package br.com.mobilemind.nativescript.alarmnotification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Map;
import java.util.Calendar;
import android.util.Log;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.net.Uri;


public class NativescriptAlarmManager{
  

  private Context context;
  private AlarmManager alarmManager;

  public NativescriptAlarmManager(Context context){
    this.context = context;
    this.alarmManager = (AlarmManager)this.context.getSystemService(Activity.ALARM_SERVICE);
  }

  public void createAlarmRepeat(String action, Date datetime, Bundle bundle, int id, long repeatTime){
    createInternalAlarm(action, datetime, bundle, id, repeatTime);
  }

  public void createAlarm(String action, Date datetime, Bundle bundle, int id){
    createInternalAlarm(action, datetime, bundle, id, 0);
  }
  
  private void createInternalAlarm(String action, Date datetime, Bundle bundle, int id, long repeatTime){


    Intent intent = new Intent(action);

    for(String key : bundle.keySet()){
        Log.d("NativescriptAlarmManager", "### copy bundle extra key=" + key + ", value=" + bundle.get(key));
        intent.putExtra(key, bundle.get(key) + "");
    }

    PendingIntent sender = null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(datetime);

    if(repeatTime > 0){
      sender = PendingIntent.getBroadcast(this.context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
      this.alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), repeatTime, sender);
    }else{
      sender = PendingIntent.getBroadcast(this.context, id, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
      this.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }
    
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Log.d("NativescriptAlarmManager", "############ CREATE NEW ALARM: action=" + action + ", datetime=" + df.format(datetime) + ", repeatTime=" + repeatTime);
  }  


  public void cancel(String action, int id){
    Intent intent = new Intent(action);
    PendingIntent sender = PendingIntent.getBroadcast(this.context, id, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
    this.alarmManager.cancel(sender);
  }

}