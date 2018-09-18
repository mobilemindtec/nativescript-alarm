
package br.com.mobilemind.alarm;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Map;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.net.Uri;

public class PluginNotificationManager {
  
  static String TAG = "NS_NOTIFY_MANGR";
  private Context context;
  private NotificationManager notificationManager;

  public PluginNotificationManager(Context context){
    this.context = context;
    this.notificationManager = (NotificationManager)this.context.getSystemService(Activity.NOTIFICATION_SERVICE);
  }

  public void show(PluginAlarmModel alarm){

    String packageName = this.context.getPackageName();
    Intent launchIntent = this.context.getPackageManager().getLaunchIntentForPackage(packageName);
    String className = launchIntent.getComponent().getClassName();

    try{

      Map<String, Object> bundle = alarm.getBundle();
      Intent notificationIntent = new Intent(this.context, Class.forName(className));
      for(String key : bundle.keySet()){
          Log.d(TAG, "### copy bundle extra key=" + key + ", value=" + bundle.get(key));
          notificationIntent.putExtra(key, bundle.get(key) + "");
      }

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String appName = getAppName(context);

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, alarm.getId(), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder mBuilder =
              new NotificationCompat.Builder(context)
                      .setSmallIcon(getSmallIcon(context, alarm))
                      .setWhen(System.currentTimeMillis())
                      .setContentTitle(alarm.getNotificationTitle())
                      .setTicker(alarm.getNotificationTitle())
                      .setContentIntent(contentIntent)
                      .setColor(getColor(alarm))
                      .setAutoCancel(true);

        if(alarm.isSnoozeEnabled() && alarm.isShowButtonSnooze()){
            Intent snoozeIntent = new Intent(this.context, PluginNotificatonReceiver.class);
            snoozeIntent.setAction(alarm.getNotificationAction());
            snoozeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            snoozeIntent.putExtra("ID", alarm.getId());
            snoozeIntent.putExtra("NOTIFICATION_ACTION", "snooze");
            PendingIntent snoozePendingIntent =
                    PendingIntent.getBroadcast(this.context, (int)System.currentTimeMillis(), snoozeIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.Action.Builder ab = new NotificationCompat.Action.Builder(0, alarm.getButtonSnoozeText(), snoozePendingIntent);
            mBuilder.addAction(ab.build());
        }

        if(alarm.isShowButtonAction()) {
            Intent openIntent = new Intent(this.context, PluginNotificatonReceiver.class);
            openIntent.setAction(alarm.getNotificationAction());
            openIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            openIntent.putExtra("ID", alarm.getId());
            openIntent.putExtra("NOTIFICATION_ACTION", "open");
            PendingIntent openPendingIntent =
                    PendingIntent.getBroadcast(this.context, (int) System.currentTimeMillis(), openIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.Action.Builder ab = new NotificationCompat.Action.Builder(0, alarm.getNotificationAction(), openPendingIntent);
            mBuilder.addAction(ab.build());
        }


        if(alarm.isShowButtonOk()) {
            Intent okIntent = new Intent(this.context, PluginNotificatonReceiver.class);
            okIntent.setAction(alarm.getNotificationAction());
            okIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            okIntent.putExtra("ID", alarm.getId());
            okIntent.putExtra("NOTIFICATION_ACTION", "ok");
            PendingIntent okPendingIntent =
                    PendingIntent.getBroadcast(this.context, (int) System.currentTimeMillis(), okIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.Action.Builder ab = new NotificationCompat.Action.Builder(0, alarm.getButtonOkText(), okPendingIntent);
            mBuilder.addAction(ab.build());
        }



        String message = alarm.getNotificationBody();
      if (message != null) {
        if(message.length() > 30){
          mBuilder.setContentText(message.substring(0, 30) + "..")
                  .setStyle(new NotificationCompat.BigTextStyle()
                          .bigText(message));
        }else{
          mBuilder.setContentText(message);
        }
      } else {
        mBuilder.setContentText("<missing message content>");
      }


      String soundName = alarm.getSoundName();
      int defaults = Notification.DEFAULT_ALL;
      if (soundName != null) {
        Resources r = context.getResources();
        int resourceId = r.getIdentifier(soundName, "raw", context.getPackageName());
        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + resourceId);
        mBuilder.setSound(soundUri);
        defaults &= ~Notification.DEFAULT_SOUND;
        mBuilder.setDefaults(defaults);
      }

      final Notification notification = mBuilder.build();

      if(alarm.isInsistent())
        notification.flags = Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;

      final int largeIcon = getLargeIcon(context, alarm);
      if (largeIcon > -1) {
        notification.contentView.setImageViewResource(android.R.id.icon, largeIcon);
      }

        mNotificationManager.notify(appName, alarm.getId(), notification);


    }catch(Exception e){
      Log.i(TAG, "#### Error at AlarmReceiver", e);
    }

  }

    private static String getAppName(Context context) {
        CharSequence appName =
                context
                        .getPackageManager()
                        .getApplicationLabel(context.getApplicationInfo());

        return (String) appName;
    }

    private static int getColor(PluginAlarmModel alarm) {
        int theColor = 0; // default, transparent
        final String passedColor = alarm.getNotificationColor(); // something like "#FFFF0000", or "red"
        if (passedColor != null) {
            try {
                theColor = Color.parseColor(passedColor);
            } catch (IllegalArgumentException ignore) {
            }
        }
        return theColor;
    }

    private static int getSmallIcon(Context context, PluginAlarmModel alarm) {

        int icon = -1;

        // first try an iconname possible passed in the server payload
        final String iconNameFromServer = alarm.getAlertSmallIcon();
        if (iconNameFromServer != null) {
            icon = getIconValue(context.getPackageName(), iconNameFromServer);
        }

        // try a custom included icon in our bundle named ic_stat_notify(.png)
        if (icon == -1) {
            icon = getIconValue(context.getPackageName(), "ic_stat_notify");
        }

        // fall back to the regular app icon
        if (icon == -1) {
            icon = context.getApplicationInfo().icon;
        }

        return icon;
    }

    private static int getLargeIcon(Context context, PluginAlarmModel alarm) {

        int icon = -1;

        // first try an iconname possible passed in the server payload
        final String iconNameFromServer = alarm.getAlertLargeIcon();
        if (iconNameFromServer != null) {
            icon = getIconValue(context.getPackageName(), iconNameFromServer);
        }

        // try a custom included icon in our bundle named ic_stat_notify(.png)
        if (icon == -1) {
            icon = getIconValue(context.getPackageName(), "ic_notify");
        }

        // fall back to the regular app icon
        if (icon == -1) {
            icon = context.getApplicationInfo().icon;
        }

        return icon;
    }

    private static int getIconValue(String className, String iconName) {
        try {
            Class<?> clazz = Class.forName(className + ".R$drawable");
            return (Integer) clazz.getDeclaredField(iconName).get(Integer.class);
        } catch (Exception ignore) {
        }
        return -1;
    }

    public void cancelAll(){
      this.notificationManager.cancelAll();
    }

    public void cancel(PluginAlarmModel alarm){
        this.notificationManager.cancel(alarm.getId());
    }
}