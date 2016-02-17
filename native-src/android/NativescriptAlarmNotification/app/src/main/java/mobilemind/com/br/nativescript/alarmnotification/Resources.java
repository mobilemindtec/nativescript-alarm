

package br.com.mobilemind.nativescript.alarmnotification;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.media.RingtoneManager;
import android.net.Uri;

public class Resources{
  
  public static final String DEFAULT_RECEIVER_ACTION = "NATIVESCRIPT_ALARM_RECEIVER";

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


    notificationTextAtrasoId = context.getResources().getIdentifier(BOOTSTRAP_ALARM_MANAGER_NOTIFICATION_TEXT_ATRASO, "string", packageName);        
    

    Log.i("Resources", "##################################");

    Log.i("Resources", "### smallIconName=" + smallIconName);
    Log.i("Resources", "### largeIconName=" + largeIconName);

    Log.i("Resources", "### queryId=" + queryId);
    Log.i("Resources", "### databaseNameId=" + databaseNameId);
    Log.i("Resources", "### databaseVersionId=" + databaseVersionId);
    Log.i("Resources", "### datetimeFormatId=" + datetimeFormatId);
    Log.i("Resources", "### notificationTitleId=" + notificationTitleId);
    Log.i("Resources", "### notificationTextNormalId=" + notificationTextNormalId);
    Log.i("Resources", "### notificationTextAtrasoId=" + notificationTextAtrasoId);

    Log.i("Resources", "##################################");
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