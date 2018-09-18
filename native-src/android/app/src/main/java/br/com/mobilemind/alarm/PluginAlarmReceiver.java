
package br.com.mobilemind.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.content.pm.PackageManager;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;


public class PluginAlarmReceiver extends BroadcastReceiver{

    static String TAG = "NS_ALARM_REC";
    private PluginNotificationManager notificationManager;
    private SharedPreferences pref;

    public void onReceive(Context context, Intent intent){

        Log.d(TAG, "##########################");
        Log.d(TAG, "###### AlarmReceiver");
        Log.d(TAG, "##########################");


        PackageManager pm = context.getPackageManager(); 
        Intent launchIntent = pm.getLaunchIntentForPackage(context.getPackageName());
        this.notificationManager = new PluginNotificationManager(context);
        this.pref = PreferenceManager.getDefaultSharedPreferences(context);
        Bundle originalBundle = intent.getExtras();
        PluginAlarmManager alarmManager = new PluginAlarmManager(context);

        try{
            JSONArray alarms = new JSONArray(this.pref.getString(PluginResources.NS_NOTIFICATIONS_KEY, "[]"));
            PluginAlarmModel alarm = null;


            Log.d(TAG, "### alarms count " + alarms.length());

            int id = intent.getExtras().getInt("ID");

            for(int i = 0; i < alarms.length(); i++){
                if(alarms.getJSONObject(i).optInt("id", 0) == id) {
                    alarm = new PluginAlarmModel();
                    JSONObject json = alarms.getJSONObject(i);
                    alarm.fromJson(json);
                    Log.d(TAG, "### alarm found " + json.toString());
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




            if(alarm.isNotifyOnReceive()){
                notificationManager.show(alarm);
            }

            if(alarm.isStartActivityOnReceive()){
                Log.d(TAG, "### starting activity for package: " + context.getApplicationContext().getPackageName());
                launchIntent.setPackage(null);
                context.startActivity(launchIntent);
            }

            if(alarm.getRepeatTime() == 0){
                alarmManager.cancel(alarm);
            }

        }catch (Exception e){
            Log.e(TAG, "### error to process AlarmReceiver: " + e.getMessage(), e);
        }


    }
}