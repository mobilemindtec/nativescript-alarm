
package br.com.mobilemind.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class PluginAlarmManager {

  static String TAG = "NS_ALARM_MAN";
  private Context context;
  private AlarmManager alarmManager;

  public PluginAlarmManager(Context context){
    this.context = context;
    this.alarmManager = (AlarmManager)this.context.getSystemService(Activity.ALARM_SERVICE);
  }


  public void createAlarm(PluginAlarmModel alarm) throws JSONException {


    Intent intent = new Intent(alarm.getAlarmAction());

    Map<String, Object> bundle = alarm.getBundle();
    for(String key : bundle.keySet()){
        Log.d(TAG, "### copy bundle extra key=" + key + ", value=" + bundle.get(key));
        intent.putExtra(key, bundle.get(key) + "");
    }

    intent.putExtra("ID", alarm.getId());

    PendingIntent sender = null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(alarm.getDate());

    Log.d(TAG, "## android.os.Build.VERSION.SDK_INT=" + android.os.Build.VERSION.SDK_INT);
    if(alarm.getRepeatTime() > 0){

      sender = PendingIntent.getBroadcast(this.context, alarm.getId(), intent, 0);

      this.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarm.getRepeatTime(), sender);

      //this.alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), repeatTime, sender);
    }else{
      sender = PendingIntent.getBroadcast(this.context, alarm.getId(), intent, 0);

      if(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP_MR1){
          this.alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
      }else{
          this.alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
      }
      //this.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }

    saveAlarm(alarm, this.context);


    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Log.d(TAG, "############ CREATE NEW ALARM: action=" + alarm.getAlarmAction() + ", datetime=" + df.format(alarm.getDate()) + ", repeatTime=" + alarm.getRepeatTime());
  }


  public void cancel(PluginAlarmModel alarm) throws JSONException {

    boolean alarmUp = this.isSet(alarm);

    if (alarmUp) {
      Intent intent = new Intent(alarm.getAlarmAction());
      PendingIntent sender = PendingIntent.getBroadcast(this.context, alarm.getId(), intent, 0);
      this.alarmManager.cancel(sender);
      Log.d(TAG, "############ Alarm canceled");
    }else{
      Log.d(TAG, "############ Alarm not is seted");
    }

    deleteAlarm(alarm, this.context);
  }

  public void cancelAll() throws JSONException {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
    JSONArray items = new JSONArray(pref.getString(PluginResources.NS_NOTIFICATIONS_KEY, "[]"));

    for(int i = 0; i < items.length(); i++){
      PluginAlarmModel alarm  = new PluginAlarmModel();
      alarm.fromJson(items.getJSONObject(i));
      cancel(alarm);
    }

    SharedPreferences.Editor editor = pref.edit();
    editor.putString(PluginResources.NS_NOTIFICATIONS_KEY, "[]");
    editor.commit();
  }

  public boolean isSet(PluginAlarmModel alarm){
    return (PendingIntent.getBroadcast(this.context, alarm.getId(), new Intent(alarm.getAlarmAction()), PendingIntent.FLAG_NO_CREATE) != null);
  }

  protected static void saveAlarm(PluginAlarmModel alarm, Context context) throws JSONException {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
    JSONObject json = alarm.toJson();
    JSONArray items = new JSONArray(pref.getString(PluginResources.NS_NOTIFICATIONS_KEY, "[]"));
    JSONArray newItems = new JSONArray();

    for(int i = 0; i < items.length(); i++){

        boolean isSame = items.getJSONObject(i).optInt("id", 0) != alarm.getId();
        JSONObject item = items.getJSONObject(i);
        if(!isSame){
            newItems.put(item);
        }
    }

    newItems.put(json);

    SharedPreferences.Editor editor = pref.edit();
    editor.putString(PluginResources.NS_NOTIFICATIONS_KEY, newItems.toString());
    editor.commit();
  }

  protected static void deleteAlarm(PluginAlarmModel alarm, Context context) throws JSONException {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
    JSONArray items = new JSONArray(pref.getString(PluginResources.NS_NOTIFICATIONS_KEY, "[]"));
    JSONArray newItems = new JSONArray();

    for(int i = 0; i < items.length(); i++){
      boolean isSame = items.getJSONObject(i).optInt("id", 0) != alarm.getId();
      JSONObject item = items.getJSONObject(i);
      if(!isSame){
        newItems.put(item);
      }
    }

    SharedPreferences.Editor editor = pref.edit();
    editor.putString(PluginResources.NS_NOTIFICATIONS_KEY, newItems.toString());
    editor.commit();
  }

  public String getAlarmsAsString(){
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
    return pref.getString(PluginResources.NS_NOTIFICATIONS_KEY, "[]");
  }
}