
var utils = require("utils/utils")

var scheduler, alarmSupport

function init(){
  scheduler = new Scheduler()
  alarmSupport = new AlarmSupport()
}

function alarmToJson(alarm){

  var dict = AlarmSupport.alarmToDict(alarm)
  var component = AlarmSupport.getDateComponents(alarm)

  var date = new Date(
    component.year,
    component.month-1,
    component.day,
    component.hour,
    component.minute,
    component.second,
    0
  )

  var keys = utils.iOSNativeHelper.collections.nsArrayToJSArray(dict.allKeys)
  var result = {datetime: date, object: alarm}

  for(var i in keys) {
    var key = keys[i]
    var value = dict.objectForKey(key)
    
    result[key] = value

    if(key == "alertBody")
      result["body"] = value

    if(key == "alertTitle")
      result["title"] = value

  }

  return result
}

exports.setUpNotifications = function(params) {
  
  params.alarmSupport.onNotificationReceived = function(alarm){

    console.log("onNotificationReceived")
    if(params.onNotificationReceived)
      params.onNotificationReceived(alarmToJson(alarm))
  }

  params.alarmSupport.onNotificationClick = function(alarm){

    console.log("onNotificationClick")
    if(params.onNotificationClick)
      params.onNotificationClick(alarmToJson(alarm))
  }

  params.alarmSupport.onNotificationActionOk = function(alarm){

    console.log("onNotificationActionOk")
    if(params.onNotificationActionOk)
      params.onNotificationActionOk(alarmToJson(alarm))
  }

  params.alarmSupport.onNotificationActionOpen = function(alarm){

    console.log("onNotificationActionOpen")
    if(params.onNotificationActionOpen)
      params.onNotificationActionOpen(alarmToJson(alarm))
  }

  params.alarmSupport.onNotificationActionSnooze = function(alarm){

    console.log("onNotificationActionSnooze")
    if(params.onNotificationActionSnooze)
      params.onNotificationActionSnooze(alarmToJson(alarm))
  }

  AlarmSupport.setUpNotifications(params.alarmSupport)
}

exports.getAlarmSupport = function(){
  if(!alarmSupport)
    init()

  return alarmSupport
}

exports.init = init

// appName, title, text, icon id, extra (json object)
exports.showNotification = function(args){

  console.log("## call showNotification")

  if(!scheduler)
    init()

  var alarm = new Alarm()  


  alarm.id = args.id    
  alarm.enabled = true
  alarm.alertBody = args.body || ""
  alarm.alertTitle = args.title || ""
  alarm.snoozeInterval = args.snoozeInterval || 15
  alarm.soundName = args.soundName || ""

  scheduler.show(alarm)
}

exports.cancelNotification = function(args){

  console.log("## call cancelNotification")

  if(!scheduler)
    init()

  scheduler.removeNotificaton()
}


exports.cancelAllNotifications = function() {

  console.log("## call cancelAllNotifications")

  if(!scheduler)
    init()

  scheduler.removeNotificaton() 
}

exports.createAlarm = function(args){

  console.log("## call createAlarm")

  if(!scheduler)
    init()

  // createAlarm(String action, Date datetime, int schedulerId, String extraKey, String extraValue)


  var swiftDate = AlarmSupport.makeDate(args.datetime.getFullYear(), args.datetime.getMonth()+1, args.datetime.getDate(), args.datetime.getHours(), args.datetime.getMinutes(), args.datetime.getSeconds())

  var alarm = new Alarm()  


  alarm.id = args.id  
  alarm.date = swiftDate
  alarm.alertBody = args.body || ""
  alarm.alertTitle = args.title || ""
  alarm.soundName = args.soundName || ""
  alarm.enabled = true
  alarm.snoozeEnabled = args.snoozeEnabled || false
  alarm.snoozeInterval = args.snoozeInterval || 15
  alarm.buttonOkText = args.buttonOkText || "OK"
  alarm.buttonOpenText = args.buttonOpenText || "Abrir"
  alarm.buttonSnoozeText = args.buttonSnoozeText || "Sonece"  
  alarm.now = false

  alarm.showButtonOpen = args.showButtonOpen || false
  alarm.showButtonOk = args.showButtonOk || false
  alarm.showButtonSnooze = args.showButtonSnooze || false

  scheduler.schedule(alarm)
}

exports.getAlarms = function () {
  
  console.log("## call getAlarms")

  if(!scheduler)
    init()

  var alarms = scheduler.getAlarms()
  var items = []

  for(var i = 0; i < alarms.count; i++){
    items.push(alarmToJson(alarms[i]))
  }

  return items
}

exports.cancelAlarm = function(args){

  console.log("## call cancelAlarm")

  if(!scheduler)
    init()

  scheduler.cancel(args.id)
}


exports.cancelAllAlarms = function() {

  console.log("## call cancelAllAlarms")

  if(!scheduler)
    init()
  scheduler.cancelAll() 
}



exports.isAlarmSet = function() {
    console.log("## isAlarmSet is not not implemented from ios")
}

exports.requestAuthorization = function(callback){
  AlarmSupport.requestAuthorization(callback)
}