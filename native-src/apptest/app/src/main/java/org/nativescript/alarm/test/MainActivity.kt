package org.nativescript.alarm.test

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import br.com.mobilemind.alarm.PluginAlarmManager
import br.com.mobilemind.alarm.PluginAlarmModel
import br.com.mobilemind.alarm.PluginResources
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.util.*

class MainActivity : AppCompatActivity() {

    var alarmManager: PluginAlarmManager? = null
    var pref: SharedPreferences? = null
    var alarms = mutableListOf<PluginAlarmModel>()
    var adapter: ArrayAdapter<PluginAlarmModel>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.alarmManager = PluginAlarmManager(this)
        this.pref = PreferenceManager.getDefaultSharedPreferences(this)

        adapter = ArrayAdapter<PluginAlarmModel>(this, android.R.layout.simple_list_item_1, alarms)
        listAlarms.adapter = adapter

        listAlarms.setOnItemClickListener { parent, view, position, id ->

            var alarm = alarms[position]
            alarmManager!!.cancel(alarm)
            alarms.remove(alarm)
            refreshData()
        }

        refreshData()
    }

    fun onCancellAll(v: View){
        alarmManager!!.cancelAll()
        refreshData()
    }

    fun onNewNotificationAlarm(v: View){

        var cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, 1)

        var alarm = PluginAlarmModel()
        alarm.id = 1
        alarm.action = this.getString(R.string.alarm_manager_default_action)
        alarm.notificationAction = this.getString(R.string.notification_default_action)
        alarm.notificationTitle = "Alarm test title"
        alarm.notificationBody = "Alarm test body"
        alarm.date = cal.time
        alarm.isNotifyOnReceive = true
        alarm.notificationColor = "#3F51B5"
        alarm.soundName = "bell"
        alarm.isInsistent = true

        this.alarmManager!!.createAlarm(alarm)

        refreshData()
    }

    fun onNewActivityAlarm(v: View){

        var cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, 1)

        var alarm = PluginAlarmModel()
        alarm.id = 1
        alarm.action = this.getString(R.string.alarm_manager_default_action)
        alarm.notificationAction = this.getString(R.string.notification_default_action)
        alarm.notificationTitle = "Alarm test title"
        alarm.notificationBody = "Alarm test body"
        alarm.date = cal.time
        alarm.isStartActivityOnReceive = true
        alarm.notificationColor = "#3F51B5"
        alarm.soundName = "bell"
        alarm.isInsistent = true

        this.alarmManager!!.createAlarm(alarm)

        refreshData()
    }

    fun onNewFullAlarm(v: View){
        var cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, 1)

        var alarm = PluginAlarmModel()
        alarm.id = 1
        alarm.action = this.getString(R.string.alarm_manager_default_action)
        alarm.notificationAction = this.getString(R.string.notification_default_action)
        alarm.notificationTitle = "Alarm test title"
        alarm.notificationBody = "Alarm test body"
        alarm.date = cal.time
        alarm.isStartActivityOnReceive = true
        alarm.isNotifyOnReceive = true
        alarm.notificationColor = "#3F51B5"
        alarm.soundName = "bell"
        alarm.isInsistent = true

        this.alarmManager!!.createAlarm(alarm)

        refreshData()
    }

    fun refreshData(){

        var items = JSONArray(this.pref!!.getString(PluginResources.NS_NOTIFICATIONS_KEY, "[]"))
        alarms.clear()

        for(i in 0 until items.length()){
            var alarm = PluginAlarmModel()
            alarm.fromJson(items.getJSONObject(i))
            alarms.add(alarm)
        }

        adapter!!.notifyDataSetChanged()

    }

}
