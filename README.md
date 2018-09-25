# Android

### Add at AndroidManifest

```
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>  
    <uses-permission android:name="android.permission.WAKE_LOCK" />   

    <application>
    
        <!-- schedule alarms after system reboot -->
        <receiver
            android:name="br.com.mobilemind.alarm.PluginBootstrapAlarmReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <action android:name="NS_APP_TEST_BOOTSTRAP_ALARM_RECEIVER" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </receiver>
        
        <!-- when alarm starts -->
        <receiver
            android:name="br.com.mobilemind.alarm.PluginAlarmReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="NS_APP_TEST_ALARM_RECEIVER" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </receiver>
        
        <!-- when notification or notification action is clicked -->
        <receiver
            android:name="br.com.mobilemind.alarm.PluginNotificatonReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="NS_APP_TEST_NOTIFICATION_RECEIVER" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </receiver>                      
    </application>
```

### Custom Sound

If you want add a custom sound, put .mp3 file in:

Android -> App_Resources/Android/raw/file.mp3
IOS -> App_Resources/iOS/file.mp3


### Startup alarm IOS

In `app.js` create a custom delegate

```
if(application.ios){
    var AlarmManager = require("nativescript-alarm")
    var alarmSupport = new AlarmSupport()

    var MyDelegate = (function (_super) {
        __extends(MyDelegate, _super);
        function MyDelegate() {
            _super.apply(this, arguments);
        }
        MyDelegate.prototype.applicationDidFinishLaunchingWithOptions = function (application, launchOptions) {
        	console.log("applicationDidFinishLaunchingWithOptions ")
        	
            AlarmManager.setUpNotifications({
                alarmSupport: alarmSupport,
                onNotificationReceived: function(alarm){
                    // receive notification and play a sound (when app is open)
                    console.log("notification received: " + JSON.stringify(alarm))
                    AlarmManager.getAlarmSupport().playSound(alarm.soundName, true, -1)
                },
                onNotificationClick: function(alarm){

                },
                onNotificationActionOk: function(alarm){

                },
                onNotificationActionOpen: function(alarm){

                },
                onNotificationActionSnooze: function(alarm){

                }
            })
    
            return true
        };

        MyDelegate.ObjCProtocols = [UIApplicationDelegate];
        return MyDelegate;
    }(UIResponder));


    application.ios.delegate = MyDelegate;	
}

```


### Create new alarm

SHOW_NOTIFICATION - if true show notification else only open app
bundle - bundle params they are passed to intent and can be retrive on app open

```

  var AlarmManager = require("nativescript-alarm")

  var alarmAction = 'NS_APP_TEST_ALARM_RECEIVER' // same value configured at AndroidManifest
  var notificationAction = 'NS_APP_TEST_NOTIFICATION_RECEIVER' // same value configured at AndroidManifest
  var date = new Date()

  
  date = new Date(date.getTime() + (60 * 1000))

  var args = {
    alarmAction: alarmAction,   //only android
    notificationAction: notificationAction,   //only android
    id: 2,   
    title: 'My Alarm title',
    body: 'My Alarm title',
    datetime: date, 
    startActivityOnReceive: true, // only android
    notifyOnReceive: true, // only android
    color: "#000000", // only android
    soundName: "bell",
    snoozeEnabled: false, // enable snooze
    insistent: true, // only android
    smallIcon: "icon", // only android
    largeIcon: "icon", // only android
    showButtonOpen: false, // notification actions
    buttonSnoozeText: "Snooze", // notification actions
    showButtonOk: false, // notification actions
    buttonOkText: "OK", // notification actions
    showButtonSnooze: false, // notification actions
    buttonOpenText: "Open" // notification actions
  }    
  
  AlarmManager.createAlarm(args) 

```

### Cancel Alarm

```
  var alarmAction = 'NS_APP_TEST_ALARM_RECEIVER' // same value configured at AndroidManifest

  // remove notification (android) and badge (ios)
  AlarmManager.cancelNotification({
    id: 2
  })

  // cancel alarm
  AlarmManager.cancelAlarm({
    alarmAction: alarmAction,
    id: 2
  })

  // cancel all alarms
  AlarmManager.cancelAllAlarms()

```

### IOS Sound

```
  // start sount {name, vibrate, loop numbers (-1 infinite)
  AlarmManager.getAlarmSupport().playSound("soundName", true, -1)

  // stop ios sound 
  AlarmManager.getAlarmSupport().stopSound()


```

### Get alarms

```
AlarmManager.getAlarms()
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
  AlarmManager.showNotification({      
    title: 'My Alarm title',
    body: 'My Alarm title',
    smallIcon: 'icon',
    largeIcon: 'icon',
    color: "#000000",
    id: 1
  })

```
