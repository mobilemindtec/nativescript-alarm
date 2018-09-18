var application = require("application");
var frameModule = require("ui/frame");

var notificationManager
var alarmManager

function init(){
  notificationManager = new br.com.mobilemind.alarm.PluginNotificationManager(application.android.context)
  alarmManager = new br.com.mobilemind.alarm.PluginAlarmManager(application.android.context)
}

exports.init = init

// appName, title, text, icon id, extra (json object)
exports.showNotification = function(args){

  console.log("## call showNotification")

  if(!notificationManager)
    init()

  var alarm = new br.com.mobilemind.PluginAlarmModel()  

  if(args.bundle){
    for(var key in args.bundle){      
      alarm.addBundle(key, args.bundle[key]);
    }
  }


  alarm.setId(args.id)    
  alarm.setNotificationTitle(args.title || "")
  alarm.setNotificationBody(args.body || "")
  alarm.setNotificationColor(args.color || "")
  alarm.setAlertSmallIcon(args.smallIcon || "")
  alarm.setAlertLargeIcon(args.largeIcon || "")
  alarm.setSoundName(args.soundName || "")
  alarm.setInsistent(args.insistent || false)

  alarm.setAlarmAction(args.alarmAction || "")
  alarm.setNotificationAction(args.notificationAction || "")
  alarm.setSnoozeEnabled(args.snoozeEnabled || false)
  alarm.setSnoozeInterval(args.snoozeInterval || 15)
  alarm.setButtonOkText(args.buttonOkText || "OK")
  alarm.setButtonSnoozeText(args.buttonSnoozeText || "Sonece")
  alarm.setButtonActionText(args.buttonActionText || "Abrir")
  
  alarm.setStartActivityOnReceive(args.startActivityOnReceive || false)
  alarm.setNotifyOnReceive(args.notifyOnReceive || false)
  alarm.setRepeatTime(args.repeatTime || 0)

  alarm.setShowButtonAction(args.showButtonAction || false)
  alarm.setShowButtonOk(args.showButtonOk || false)
  alarm.setShowButtonSnooze(args.showButtonSnooze || false)


  notificationManager.show(alarm)
}

exports.cancelNotification = function(args){

  console.log("## call cancelNotification")

  if(!alarmManager)
    init()

  var alarm = new br.com.mobilemind.PluginAlarmModel()  
  alarm.setId(args.id)  

  notificationManager.cancel(alarm)
}


exports.cancelAllNotifications = function() {

  console.log("## call cancelAllNotifications")

  if(!alarmManager)
    init()
  notificationManager.cancelAll() 
}

exports.createAlarm = function(args){

  console.log("## call createAlarm")

  if(!alarmManager)
    init()

  // createAlarm(String action, Date datetime, int schedulerId, String extraKey, String extraValue)


  var cal = new java.util.Calendar.getInstance()
  cal.set(args.datetime.year, args.datetime.month, args.datetime.day, args.datetime.hour, args.datetime.minute, 0)
  var javaDate = cal.getTime()

  var alarm = new br.com.mobilemind.PluginAlarmModel()  

  if(args.bundle){
    for(var key in args.bundle){      
      alarm.addBundle(key, args.bundle[key]);
    }
  }


  alarm.setId(args.id)  
  alarm.setDate(javaDate)
  alarm.setNotificationTitle(args.title || "")
  alarm.setNotificationBody(args.body || "")
  alarm.setNotificationColor(args.color || "")
  alarm.setAlertSmallIcon(args.smallIcon || "")
  alarm.setAlertLargeIcon(args.largeIcon || "")
  alarm.setSoundName(args.soundName || "")
  alarm.setInsistent(args.insistent || false)

  alarm.setAlarmAction(args.alarmAction || "")
  alarm.setNotificationAction(args.notificationAction || "")
  alarm.setSnoozeEnabled(args.snoozeEnabled || false)
  alarm.setSnoozeInterval(args.snoozeInterval || 15)
  alarm.setButtonOkText(args.buttonOkText || "OK")
  alarm.setButtonSnoozeText(args.buttonSnoozeText || "Sonece")
  alarm.setButtonActionText(args.buttonActionText || "Abrir")
  
  alarm.setStartActivityOnReceive(args.startActivityOnReceive || false)
  alarm.setNotifyOnReceive(args.notifyOnReceive || false)
  alarm.setRepeatTime(args.repeatTime || 0)

  alarm.setShowButtonAction(args.showButtonAction || false)
  alarm.setShowButtonOk(args.showButtonOk || false)
  alarm.setShowButtonSnooze(args.showButtonSnooze || false)

  alarmManager.createAlarm(alarm)

}

exports.getAllAlarms = function () {
  
  console.log("## call getAllAlarms")

  if(!alarmManager)
    init()

  return JSON.parse(alarmManager.getAlarmsAsString())
}

exports.cancelAlarm = function(args){

  console.log("## call cancelAlarm")

  if(!alarmManager)
    init()

  var alarm = new br.com.mobilemind.PluginAlarmModel()  
  alarm.setId(args.id)  
  alarm.setAlarmAction(args.alarmAction || "")

  alarmManager.cancel(alarm)
}


exports.cancelAllAlarms = function() {

  console.log("## call cancelAllAlarms")

  if(!alarmManager)
    init()
  alarmManager.cancelAll() 
}



exports.isAlarmSet = function() {
    console.log("## call isAlarmSet")

  if(!alarmManager)
    init()

  var alarm = new br.com.mobilemind.PluginAlarmModel()  
  alarm.setId(args.id)  

  return notificationManager.isSet(alarm)
}