var application = require("application");
var frameModule = require("ui/frame");

var notificationManager
var alarmManager

function init(){
  notificationManager = new br.com.mobilemind.alarm.PluginNotificationManager(application.android.context)
  alarmManager = new br.com.mobilemind.alarm.PluginAlarmManager(application.android.context)
}

exports.init = init

function alarmToJson(alarm) {

  var cal = java.util.Calendar.getInstance()
  cal.setTime(alarm.getDate())

  var date = new Date(
    cal.get(java.util.Calendar.YEAR), 
    cal.get(java.util.Calendar.MONTH), 
    cal.get(java.util.Calendar.DATE), 
    cal.get(java.util.Calendar.HOUR), 
    cal.get(java.util.Calendar.SECOND), 
    0
  )  

  return {
    id: alarm.getId(),
    datetime: date,
    titel: alarm.getNotificationTitle(),
    body: alarm.getNotificationBody(),
    color: alarm.getNotificationColor(),
    smallIcon: alarm.getAlertSmallIcon(),
    largeIcon: alarm.getAlertLargeIcon(),
    soundName: alarm.getSoundName(),
    insistent: alarm.isInsistent(),
    alarmAction: alarm.getAlarmAction(),
    notificationAction: alarm.getNotificationAction(),
    snoozeEnabled: alarm.isSnoozeEnabled(),
    snoozeInterval: alarm.getSnoozeInterval(),
    buttonOkText: alarm.getButtonOkText(),
    buttonSnoozeText: alarm.getButtonSnoozeText(),
    buttonOpenText: alarm.getButtonOpenText(),    
    startActivityOnReceive: alarm.isStartActivityOnReceive(),
    notifyOnReceive: alarm.isNotifyOnReceive(),
    repeatTime: alarm.getRepeatTime(),
    showButtonOpen: alarm.isShowButtonOpen(),
    showButtonOk: alarm.isShowButtonOk(),
    showButtonSnooze: alarm.isShowButtonSnooze(),
    alarmBootstrapText: alarm.getAlarmBootstrapText(),
    enabled: alarm.isEnabled()
  }
}

// appName, title, text, icon id, extra (json object)
exports.showNotification = function(args){

  console.log("## call showNotification")

  if(!notificationManager)
    init()

  var alarm = new br.com.mobilemind.alarm.PluginAlarmModel()  

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
  alarm.setButtonOpenText(args.buttonOpenText || "Abrir")
  alarm.setAlarmBootstrapText(args.alarmBootstrapText || "")

  alarm.setStartActivityOnReceive(args.startActivityOnReceive || false)
  alarm.setNotifyOnReceive(args.notifyOnReceive || false)
  alarm.setRepeatTime(args.repeatTime || 0)

  alarm.setShowButtonOpen(args.showButtonOpen || false)
  alarm.setShowButtonOk(args.showButtonOk || false)
  alarm.setShowButtonSnooze(args.showButtonSnooze || false)
  alarm.setEnabled(true)


  notificationManager.show(alarm)
}

exports.cancelNotification = function(args){

  console.log("## call cancelNotification")

  if(!alarmManager)
    init()

  var alarm = new br.com.mobilemind.alarm.PluginAlarmModel()  
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
  cal.set(args.datetime.getFullYear(), args.datetime.getMonth(), args.datetime.getDate(), args.datetime.getHours(), args.datetime.getMinutes(), 0)
  var javaDate = cal.getTime()

  var alarm = new br.com.mobilemind.alarm.PluginAlarmModel()  

  if(args.bundle){
    for(var key in args.bundle){      
      alarm.addBundle(key, args.bundle[key]);
    }
  }


  alarm.setId(args.id)  
  alarm.setDate(javaDate)
  alarm.setNotificationTitle(args.title || "")
  alarm.setNotificationBody(args.body || "")
  alarm.setAlarmBootstrapText(args.alarmBootstrapText || "")
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
  alarm.setButtonOpenText(args.buttonOpenText || "Abrir")
  
  alarm.setStartActivityOnReceive(args.startActivityOnReceive || false)
  alarm.setNotifyOnReceive(args.notifyOnReceive || false)
  alarm.setRepeatTime(args.repeatTime || 0)

  alarm.setShowButtonOpen(args.showButtonOpen || false)
  alarm.setShowButtonOk(args.showButtonOk || false)
  alarm.setShowButtonSnooze(args.showButtonSnooze || false)
  alarm.setEnabled(true)
  
  alarmManager.createAlarm(alarm)

}

exports.getAlarms = function () {
  
  console.log("## call getAlarms")

  if(!alarmManager)
    init()

  var alarms = alarmManager.getAlarms()
  var items = []

  for(var i = 0; i < alarms.size(); i++){
    items.push(alarmToJson(alarms.get(i)))
  }


  return items
}

exports.cancelAlarm = function(args){

  console.log("## call cancelAlarm")

  if(!alarmManager)
    init()

  var alarm = new br.com.mobilemind.alarm.PluginAlarmModel()  
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

  var alarm = new br.com.mobilemind.alarm.PluginAlarmModel()  
  alarm.setId(args.id)  

  return notificationManager.isSet(alarm)
}