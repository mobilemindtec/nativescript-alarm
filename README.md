# Android

### Add at AndroidManifest - Change YOUR_APP to app name or whatever you wont

```
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>  
    <uses-permission android:name="android.permission.WAKE_LOCK" />   

    <receiver
        android:name="br.com.mobilemind.nativescript.alarmnotification.BootstrapAlarmReceiver"      
        android:exported="true">
      <intent-filter>
          <action android:name="android.intent.action.BOOT_COMPLETED" />
          <action android:name="YOUR_APP_BOOTSTRAP_ALARM_RECEIVER" />
          <category android:name="android.intent.category.DEFAULT" />
      </intent-filter>
    </receiver>

    <receiver
        android:name="br.com.mobilemind.nativescript.alarmnotification.AlarmReceiver"
        android:exported="true">
      <intent-filter>
          <action android:name="YOUR_APP_ALARM_RECEIVER" />
          <category android:name="android.intent.category.DEFAULT" />
      </intent-filter>
    </receiver>    
```

### Add at strings.xml

This will be used at ALARM_RECEIVER and to show automatic notifications inside AlarmReceiver and configure alarms at BootstrapAlarmReceiver

* bootstrap_alarm_manager_query - this sql retrive alarms at database on BootstrapAlarmReceiver
* bootstrap_alarm_manager_database_name - your database app name
* bootstrap_alarm_manager_notification_title - default notification title to create alarm on BootstrapAlarmReceiver
* bootstrap_alarm_manager_notification_text_atraso - default notification text to create alarm on BootstrapAlarmReceiver. receives param title from sql
* bootstrap_alarm_manager_default_action - action at AndroidManifest (YOUR_APP_ALARM_RECEIVER)

```
    <!-- alarm manager plugin -->
    <string  name="bootstrap_alarm_manager_query">select id, showAt, title, 1000 * 60 * 60 from my_schedule_table where delivered = 0</string>
    <string  name="bootstrap_alarm_manager_database_name">my-app.db</string>
    <string  name="bootstrap_alarm_manager_database_version">1</string>
    <string  name="bootstrap_alarm_manager_database_datetime_format">YYYY-MM-DD HH:mm:ss.SSS</string>
    <string  name="bootstrap_alarm_manager_notification_title">Default Notification Title</string>
    <string  name="bootstrap_alarm_manager_notification_text_normal">Default Notification Text %2$s</string>
    <string  name="bootstrap_alarm_manager_notification_text_atraso">O livro %2$s está com a devolução atrasada!</string>    
    <string  name="bootstrap_alarm_manager_notification_small_icon">icon</string> 
    <string  name="bootstrap_alarm_manager_default_action">YOUR_APP_ALARM_RECEIVER</string> 
    <string  name="bootstrap_alarm_manager_notification_sound"></string>  
```

### Create new alarm

SHOW_NOTIFICATION - if true show notification else only open app
bundle - bundle params they are passed to intent and can be retrive on app open

```

var AlarmManager = require("nativescript-alarm-notification")

var action = 'YOUR_APP_ALARM_RECEIVER'
var id = 1

var args = {
  action: action,   
  id: id,   
  bundle: [
    {key: 'ID', value: id + ""},
    {key: 'SHOW_NOTIFICATION', value: 'true'},
    {key: 'NOTIFICATION_TITLE', value: 'My App'},
    {key: 'NOTIFICATION_TEXT', value: "Test Notification"},
    
    //{key: 'NOTIFICATION_SMALL_ICON_NAME', value: "Icon A"},
    //{key: 'NOTIFICATION_LARGE_ICON_NAME', value: "Icon B"},
    
  ],
  datetime: {
    year: 2016, 
    month: 10, 
    day: 25, 
    hour: 10, 
    minute: 15
  },
  //repeatTime: 1000 * 60 * 60 // 1 hora
}    

```

### Cancel Alarm

```
var action = 'YOUR_APP_ALARM_RECEIVER'
var id = 1

AlarmManager.alarmCancel({
  action: action,   
  id: id
})
```

### Getting bundle params on app open

```
function getExtraKey(key){
  var activity = application.android.foregroundActivity || application.android.startActivity  

  if(activity.getIntent() && activity.getIntent().getExtras()){

    var extra =  activity.getIntent().getExtras()

    if(extra.containsKey(key)){
      return extra.get(key)
    }
  }

  return undefined    
}

var id = getExtraKey('ID')

```

### Show notification

```
AlarmManager.show({  
  title: 'Title',
  text: 'text message',
  smallIcon: 'icon',
  largeIcon: 'icon',
  soundUri: '',
  bundle: []
})

```
