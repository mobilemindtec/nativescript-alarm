
package br.com.mobilemind.nativescript.alarmnotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Map;
import java.util.Calendar;
import android.util.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.net.Uri;
import android.graphics.BitmapFactory;

public class NativescriptNotificationManager{
  

  private Context context;
  private NotificationManager notificationManager;

  public NativescriptNotificationManager(Context context){
    this.context = context;
    this.notificationManager = (NotificationManager)this.context.getSystemService(Activity.NOTIFICATION_SERVICE);
  }

  public void show(String title, String text, int smallIconRes, int largeIconRes, Uri custonSound, Bundle bundle){

    String packageName = this.context.getPackageName();
    Intent launchIntent = this.context.getPackageManager().getLaunchIntentForPackage(packageName);
    String className = launchIntent.getComponent().getClassName();
    String tns = className + ".class";

    try{

      Intent intent = new Intent(this.context, Class.forName(className));
      for(String key : bundle.keySet()){
          Log.d("NativescriptNotificationManager", "### copy bundle extra key=" + key + ", value=" + bundle.get(key));
          intent.putExtra(key, bundle.get(key) + "");
      }

      PendingIntent contentIntent = PendingIntent.getActivity(this.context, 0, intent, 0);
      
      /*
      Notification notification = new Notification(
              iconRes, // the icon for the status bar
              title, // the text to display in the ticker
              System.currentTimeMillis()); // the timestamp for the   
      */

      Notification.Builder builder = new Notification.Builder(this.context)
             .setContentTitle(title)
             .setContentText(text)                          
             .setDefaults(Notification.DEFAULT_LIGHTS)
             .setContentIntent(contentIntent);

      if(smallIconRes > 0){
        builder.setSmallIcon(smallIconRes);
      }

      if(largeIconRes > 0){
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIconRes));
      }

      if(custonSound != null){
        builder.setSound(custonSound);
      }

      Notification notification = builder.build();



      //notification.setLatestEventInfo(this.context, title, text, contentIntent);

    
      //notification.defaults = Notification.DEFAULT_ALL;

      notificationManager.notify((int)System.currentTimeMillis(), notification); 


    }catch(Exception e){
      Log.i("NativescriptNotificationManager", "#### Error at AlarmReceiver", e);
    }

  }

}