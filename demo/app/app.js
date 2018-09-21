/*
In NativeScript, the app.js file is the entry point to your application.
You can use this file to perform app-level initialization, but the primary
purpose of the file is to pass control to the app’s first module.
*/

require("./bundle-config");
var application = require("application");
var AlarmManager = require("nativescript-alarm")
var utils = require("utils/utils")


if(application.ios){

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


        MyDelegate.prototype.applicationOpenURLSourceApplicationAnnotation = function (application, url, sourceApplication, annotation) {
            return false
        };
        MyDelegate.prototype.applicationDidBecomeActive = function (application) {
            
        };
        MyDelegate.prototype.applicationWillTerminate = function (application) {
            //Do something you want here
        };
        MyDelegate.prototype.applicationDidEnterBackground = function (application) {
            //Do something you want here
        };
        MyDelegate.ObjCProtocols = [UIApplicationDelegate];
        return MyDelegate;
    }(UIResponder));



    application.ios.delegate = MyDelegate;	
}

application.start({ moduleName: "main-page" });

/*
Do not place any code after the application has been started as it will not
be executed on iOS.
*/
