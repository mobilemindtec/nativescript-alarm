
package br.com.mobilemind.nativescript.alarmnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import br.com.mobilemind.nativescript.alarmnotification.sql.DBHelper;
import android.database.Cursor;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import android.os.Bundle;


public class BootstrapAlarmReceiver extends BroadcastReceiver{

  static String TAG = "NS_BOOT_ALARM_REC";
  private DBHelper helper;
  private Resources resources;

  public void onReceive(Context context, Intent intent){

    Log.d(TAG, "##########################");
    Log.d(TAG, "###### BootstrapAlarmReceiver");
    Log.d(TAG, "##########################");

    resources = new Resources(context);
  
    DateFormat df = new SimpleDateFormat(resources.getDateFormat());

    helper = new DBHelper(context, resources.getDatabaseName(), resources.getDatabaseVersion());

    Cursor rs = helper.getData(resources.getQuery());
    Date today = java.util.Calendar.getInstance().getTime();
    NativescriptAlarmManager alarmManager = new NativescriptAlarmManager(context);
    NativescriptNotificationManager notificationManager = new NativescriptNotificationManager(context);
    

    if(rs.moveToFirst()){

      do{

        int key = rs.getInt(0); // key
        String datetimeStr = rs.getString(1); //
        String title = rs.getString(2); //title
        int repeat = rs.getInt(3); //repeat

        if(datetimeStr != null){

          try{
            Date datetime = df.parse(datetimeStr);

            if(datetime.compareTo(today) > 0){

              Bundle bundle = new Bundle();
              bundle.putString("ID", key + "");              
              bundle.putString("SHOW_NOTIFICATION", "true");               
              bundle.putString("NOTIFICATION_TITLE", resources.getNotificationTitle()); 
              bundle.putString("NOTIFICATION_TEXT", String.format(resources.getNotificationTextAtraso(), title)); 

              //createAlarm(String action, Date datetime, int schedulerId, String extraKey, String extraValue)
              alarmManager.createAlarmRepeat(Resources.DEFAULT_RECEIVER_ACTION, datetime, bundle, key, repeat);

              Log.d(TAG, "## create alarm");

            }else{

              Bundle bundle = new Bundle();
              bundle.putString("ID", key + "");              

              //show(String action, String title, String text, int iconRes, Bundle bundle)
              notificationManager.show(                
                resources.getNotificationTitle(), 
                String.format(resources.getNotificationTextNormal(), title),                 
                resources.getSmallIconId(),
                resources.getLargeIconId(),
                resources.getSoundUri(),
                bundle);

              Log.d(TAG, "## show notification");
            }
          }catch(Exception e){
            Log.i(TAG, "Erro processando BootstrapAlarmReceiver", e);
          }


        }



      }while(rs.moveToNext());

    }


  }
}