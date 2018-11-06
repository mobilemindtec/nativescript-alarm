
package br.com.mobilemind.alarm;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;


public class PluginNotificatonReceiver extends BroadcastReceiver{

    static String TAG = "NS_NOTIFY_REC";
    private PluginNotificationManager notificationManager;
    private SharedPreferences pref;

    public void onReceive(Context context, Intent intent){

        Log.d(TAG, "##########################");
        Log.d(TAG, "###### PluginNotificatonReceiver");
        Log.d(TAG, "##########################");


        PackageManager pm = context.getPackageManager(); 
        Intent launchIntent = pm.getLaunchIntentForPackage(context.getPackageName());
        this.notificationManager = new PluginNotificationManager(context);
        this.pref = PreferenceManager.getDefaultSharedPreferences(context);
        Bundle originalBundle = intent.getExtras();
        String action = originalBundle.getString("NOTIFICATION_ACTION");
        PluginAlarmManager alarmManager = new PluginAlarmManager(context);

        try{
            JSONArray alarms = new JSONArray(this.pref.getString(PluginResources.NS_NOTIFICATIONS_KEY, "[]"));
            PluginAlarmModel alarm = null;


            Log.d(TAG, "### alarms count " + alarms.length() + ", action " + action);

            int id = intent.getExtras().getInt("ID");

            for(int i = 0; i < alarms.length(); i++){
                if(alarms.getJSONObject(i).optInt("id", 0) == id) {
                    alarm = new PluginAlarmModel();
                    JSONObject json = alarms.getJSONObject(i);
                    alarm.fromJson(json);
                    break;
                }
            }

            if(alarm == null){
                Log.d(TAG, "### alarm ID " + id + " not found");
                return;
            }


            for(String key : originalBundle.keySet()){
                Log.d(TAG, "### copy bundle extra key=" + key + ", value=" + originalBundle.get(key));
                launchIntent.putExtra(key, originalBundle.get(key) + "");
            }

            if("open".equals(action)){
                Log.d(TAG, "### starting activity for package: " + context.getApplicationContext().getPackageName());
                launchIntent.setPackage(null);
                context.startActivity(launchIntent);
                alarm.setEnabled(false);
                alarmManager.saveAlarm(alarm, context);
            } else if("snooze".equals(action)){
                Log.d(TAG, "### snooze action");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(alarm.getDate());
                calendar.add(Calendar.MINUTE, alarm.getSnoozeInterval());
                alarm.setDate(calendar.getTime());
                alarmManager.createAlarm(alarm);
            }else if("ok".equals(action)){
                Log.d(TAG, "### ok action");
                alarm.setEnabled(false);
                alarmManager.saveAlarm(alarm, context);
            }else{
                Log.d(TAG, "### action " + action + " not found");
            }


            notificationManager.cancel(alarm);



        }catch (Exception e){
            Log.e(TAG, "### error to process AlarmReceiver: " + e.getMessage(), e);
        }


    }
}