

package br.com.mobilemind.nativescript.alarmnotification;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.media.RingtoneManager;
import android.net.Uri;

public class Resources{
  static String TAG = "NS_RESOURCES";
  public static String DEFAULT_RECEIVER_ACTION = "NATIVESCRIPT_ALARM_RECEIVER";

  private Context context;

  public static final String BOOTSTRAP_ALARM_MANAGER_QUERY = "bootstrap_alarm_manager_query";
  public static final String BOOTSTRAP_ALARM_MANAGER_DATABASE_NAME = "bootstrap_alarm_manager_database_name";
  public static final String BOOTSTRAP_ALARM_MANAGER_DATABASE_VERSION = "bootstrap_alarm_manager_database_version";
  public static final String BOOTSTRAP_ALARM_MANAGER_DATABASE_DATETIME_FORMAT = "bootstrap_alarm_manager_database_datetime_format";

  public static final String BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_TITLE = "bootstrap_alarm_manager_notification_title";
  public static final String BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_TEXT_NORMAL = "bootstrap_alarm_manager_notification_text_normal";
  public static final String BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_TEXT_ATRASO = "bootstrap_alarm_manager_notification_text_atraso";

  public static final String BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_SMALL_ICON = "bootstrap_alarm_manager_notification_small_icon";
  public static final String BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_LARGE_ICON = "bootstrap_alarm_manager_notification_large_icon";
  public static final String BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_SOUND = "bootstrap_alarm_manager_notification_sound";

  public static final String BOOTSTRAP_ALARM_MANAGER_DEFAULT_ACTION = "bootstrap_alarm_manager_default_action";

  private int queryId, databaseNameId, databaseVersionId, datetimeFormatId, notificationTitleId, 
  notificationTextNormalId, notificationTextAtrasoId, smallIconId, largeIconId;

  private PackageManager pm;
  private String packageName;

  public Resources(Context context){

    this.context = context;

    pm = context.getPackageManager(); 
    packageName = context.getPackageName();


    queryId = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_QUERY, "string", packageName);    
    databaseNameId = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_DATABASE_NAME, "string", packageName);    
    databaseVersionId = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_DATABASE_VERSION, "string", packageName);    
    datetimeFormatId = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_DATABASE_DATETIME_FORMAT, "string", packageName);    

    notificationTitleId = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_TITLE, "string", packageName);    
    notificationTextNormalId = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_TEXT_NORMAL, "string", packageName);    
    notificationTextAtrasoId = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_TEXT_ATRASO, "string", packageName);    

    int smallIconNameId  = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_SMALL_ICON, "string", packageName);
    String smallIconName = null;
    if(smallIconNameId > 0){
      smallIconName = context.getString(smallIconNameId);
      smallIconId = context.getResources().getIdentifier(smallIconName, "drawable", packageName);  
    }

    int largeIconNameId  = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_LARGE_ICON, "string", packageName);  
    String largeIconName = null;
    if(largeIconNameId > 0){
      largeIconName = context.getString(largeIconNameId);
      largeIconId = context.getResources().getIdentifier(largeIconName, "drawable", packageName);      
    }

    int defaultReceiverActionId  = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_DEFAULT_ACTION, "string", packageName);  
    //String defaultReceiverActionName = null;
    if(defaultReceiverActionId > 0){
      DEFAULT_RECEIVER_ACTION = context.getString(defaultReceiverActionId);
      //DEFAULT_RECEIVER_ACTION = context.getResources().getIdentifier(defaultReceiverActionName, "string", packageName);      
    }
    


    notificationTextAtrasoId = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_TEXT_ATRASO, "string", packageName);        
    

    Log.i(TAG, "##################################");

    Log.i(TAG, "### smallIconName=" + smallIconName);
    Log.i(TAG, "### largeIconName=" + largeIconName);

    Log.i(TAG, "### queryId=" + queryId);
    Log.i(TAG, "### databaseNameId=" + databaseNameId);
    Log.i(TAG, "### databaseVersionId=" + databaseVersionId);
    Log.i(TAG, "### datetimeFormatId=" + datetimeFormatId);
    Log.i(TAG, "### notificationTitleId=" + notificationTitleId);
    Log.i(TAG, "### notificationTextNormalId=" + notificationTextNormalId);
    Log.i(TAG, "### notificationTextAtrasoId=" + notificationTextAtrasoId);
    Log.i(TAG, "### DEFAULT_RECEIVER_ACTION=" + DEFAULT_RECEIVER_ACTION);

    Log.i(TAG, "##################################");
  }  

  public String getQuery(){
    return context.getString(queryId);
  }

  public String getDatabaseName(){
    return context.getString(databaseNameId);
  }

  public int getDatabaseVersion(){
    return Integer.parseInt(context.getString(databaseNameId));
  }

  public String getNotificationTitle(){
    return context.getString(notificationTitleId);
  }

  public String getNotificationTextNormal(){
    return context.getString(notificationTextNormalId);
  }

  public String getNotificationTextAtraso(){
    return context.getString(notificationTextAtrasoId);
  }

  public String getDateFormat(){
    return context.getString(datetimeFormatId);
  }

  public int getSmallIconId(){
    return this.smallIconId;
  }

  public int getLargeIconId(){
    return this.largeIconId;
  }

  public int getIconId(String iconName){
    return context.getResources().getIdentifier(iconName, "drawable", packageName);        
  }

  public Uri getSoundUri(){
    return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
  }

}