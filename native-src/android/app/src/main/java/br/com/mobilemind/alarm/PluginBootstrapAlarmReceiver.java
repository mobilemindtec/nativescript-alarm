
package br.com.mobilemind.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import java.util.Date;
import android.os.Bundle;

import org.json.JSONArray;


public class PluginBootstrapAlarmReceiver extends BroadcastReceiver {

    static String TAG = "NS_BOOT_ALARM_REC";
    private SharedPreferences pref;

    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "##########################");
        Log.d(TAG, "###### BootstrapAlarmReceiver");
        Log.d(TAG, "##########################");

        this.pref = PreferenceManager.getDefaultSharedPreferences(context);

        Date today = java.util.Calendar.getInstance().getTime();
        PluginAlarmManager alarmManager = new PluginAlarmManager(context);
        PluginNotificationManager notificationManager = new PluginNotificationManager(context);

        try {
            JSONArray alarms = new JSONArray(this.pref.getString(PluginResources.NS_NOTIFICATIONS_KEY, "[]"));

            for (int i = 0; i < alarms.length(); i++) {

                PluginAlarmModel alarm = new PluginAlarmModel();
                alarm.fromJson(alarms.getJSONObject(i));

                if (alarm.getDate().compareTo(today) > 0) {
                    alarmManager.createAlarm(alarm);
                    Log.d(TAG, "## create alarm ID " + alarm.getId());
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", alarm.getId());

                    //show(String action, String title, String text, int iconRes, Bundle bundle)
                    notificationManager.show(alarm);

                    // reemove alarm
                    PluginAlarmManager.deleteAlarm(alarm,  context);
                }

            }

            //PluginAlarmManager.alarmPrefAction(null, false, context);

        } catch (Exception e) {
            Log.e(TAG, "###### error to process BootstrapAlarmReceiver: " + e.getMessage(), e);
        }


    }
}
