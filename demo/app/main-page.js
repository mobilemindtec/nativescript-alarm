var AlarmManager = require("nativescript-alarm")
var observableModule = require("data/observable");
var ViewUtil = require("nativescript-view-util");
var dialogs = require("ui/dialogs"); 
var utils = require("utils/utils")
var application = require("application");
var page
var viewModel = new observableModule.Observable({
  message: 'nenhum agendamento'
})

exports.loaded = function(args){
  page = args.object;
  page.bindingContext = viewModel;

  var notifyId = ViewUtil.getExtraKey('ID')

  if(notifyId){
    setTimeout(function(){
      dialogs.alert({
        title: "Alarm",
        message: "notify id " + notifyId,
        okButtonText: "OK"
      })
    }, 3000)
  }


  if(application.ios){
    setTimeout(function(){
      AlarmManager.requestAuthorization()
    },2000)
  }




}

exports.onSetAlarm = function(){
  createAlarm()
}

exports.onCancelAlarm = function(){

  var action = 'MY_TEST_ALARM_APP_ALARM_RECEIVER'


  AlarmManager.cancelNotification({
    id: 2
  })


  AlarmManager.cancelAlarm({
    alarmAction: action,
    id: 2
  })

  if(application.ios){
    AlarmManager.getAlarmSupport().stopSound()
  }

}

exports.onTapNotification = function(){

  
  AlarmManager.showNotification({      
    title: 'My Alarm title',
    body: 'My Alarm title',
    smallIcon: 'icon',
    largeIcon: 'icon',
    color: "#000000",
    id: 1
  })
}

exports.getAlarms = function(){
  viewModel.set('message', JSON.stringify(AlarmManager.getAlarms()))
  
}

function createAlarm(){

  var action = 'MY_TEST_ALARM_APP_ALARM_RECEIVER'
  var date = new Date()

  
  date = new Date(date.getTime() + (60 * 1000))

  var args = {
    alarmAction: action,   
    id: 2,   
    title: 'My Alarm title',
    body: 'My Alarm title',
    datetime: date,
    startActivityOnReceive: true,
    notifyOnReceive: true,
    color: "#000000",
    soundName: "bell",
    insistent: true
  }    
  
  AlarmManager.createAlarm(args)

  viewModel.set('message', "alarm at: " + args.datetime.getHours() + ":" + args.datetime.getMinutes())

}
