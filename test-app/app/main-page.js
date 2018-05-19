var AlarmManager = require("nativescript-alarm-notification")
var observableModule = require("data/observable");

var page
var viewModel = new observableModule.Observable({
  message: 'nenhum agendamento'
})

exports.loaded = function(args){
  page = args.object;
  page.bindingContext = viewModel;
}

exports.onSetAlarm = function(){
  createAlarm()
}

exports.onTapNotification = function(){
  AlarmManager.show({  
    title: 'Title',
    text: 'text message',
    smallIcon: 'icon',
    largeIcon: 'icon',
  })  
}

function createAlarm(){

  var action = 'MY_TEST_ALARM_APP_ALARM_RECEIVER'
  var date = new Date()

  var args = {
    action: action,   
    id: 1,   
    bundle: [
      {key: 'ID', value: 1 + ""},
      {key: 'SHOW_NOTIFICATION', value: 'true'},
      {key: 'NOTIFICATION_TITLE', value: 'Título Alarme'},
      {key: 'NOTIFICATION_TEXT', value: "Descrição do Alarme"}
    ],
    datetime: {
      year: date.getFullYear(), 
      month: date.getMonth(), 
      day: date.getDate(), 
      hour: date.getHours(), 
      minute: date.getMinutes() + 1
    },
    repeatTime: 1000 * 60 // 1 minuto
  }    

  console.log("### form-util - createAlarm " + JSON.stringify(args))


  AlarmManager.createAlarm(args)

  viewModel.set('message', "alarm at: " + args.datetime.hour + ":" + args.datetime.minute)

}

function cancelAlarm(book){
  AlarmManager.alarmCancel({
    action: 'MY_TEST_ALARM_APP_ALARM_RECEIVER',   
    id: 1
  })
}