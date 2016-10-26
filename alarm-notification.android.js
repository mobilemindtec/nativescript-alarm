var application = require("application");
var frameModule = require("ui/frame");




var notificationManager
var alarmManager

function init(){
  notificationManager = new br.com.mobilemind.nativescript.alarmnotification.NativescriptNotificationManager(application.android.context);
  alarmManager = new br.com.mobilemind.nativescript.alarmnotification.NativescriptAlarmManager(application.android.context);
}

exports.init = init

// appName, title, text, icon id, extra (list key|val)
exports.show = function(args){

  if(!notificationManager)
    init()
  
  var bundle = new android.os.Bundle()

  if(args.bundle){
    for(var i = 0; i < args.bundle.length; i++){
      console.log("## bundle copy key=" + args.bundle[i].key + ", value=" + args.bundle[i].value)
      bundle.putString(args.bundle[i].key, args.bundle[i].value);
    }    
  }

  var smallIconRes = args.smallIcon ? application.android.context.getResources().getIdentifier(args.smallIcon, "drawable", application.android.context.getPackageName()) : -1; 
  var largeIconRes = args.largeIcon ? application.android.context.getResources().getIdentifier(args.largeIcon, "drawable", application.android.context.getPackageName()) : -1;
  var soundUri = args.sound ? android.net.Uri.parse(args.sound) : null
  //String title, String text, int smallIconRes, int largeIconRes, Uri custonSound, Bundle bundle
  notificationManager.show(args.title, args.text, smallIconRes, largeIconRes, soundUri, bundle)
}

exports.createAlarm = function(args){

  if(!alarmManager)
    init()

  // createAlarm(String action, Date datetime, int schedulerId, String extraKey, String extraValue) 


  var cal = new java.util.Calendar.getInstance()
  cal.set(args.datetime.year, args.datetime.month, args.datetime.day, args.datetime.hour, args.datetime.minute, 0);
  var javaDate = cal.getTime();

  var bundle = new android.os.Bundle()

  for(var i = 0; i < args.bundle.length; i++){
    console.log("## bundle copy key=" + args.bundle[i].key + ", value=" + args.bundle[i].value)
    bundle.putString(args.bundle[i].key, args.bundle[i].value);
  }

  alarmManager.createAlarmRepeat(args.action, javaDate, bundle, args.id, args.repeatTime || 0 );

  console.log("## called alarmManager.createAlarmRepeat action=" + args.action + ", id=" + args.id + ", date=" + javaDate + ", args.repeatTime=" + args.repeatTime);  

}

exports.alarmCancel = function(args){

  console.log("## call alarmManager.alarmCancel")

  if(!alarmManager)
    init()

  alarmManager.cancel(args.action, args.id); 
}


exports.DEFAULT_RECEIVER_ACTION = br.com.mobilemind.nativescript.alarmnotification.Resources.DEFAULT_RECEIVER_ACTION