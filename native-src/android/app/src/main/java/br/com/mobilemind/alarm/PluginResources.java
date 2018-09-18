

package br.com.mobilemind.alarm;

import android.media.RingtoneManager;
import android.net.Uri;

public class PluginResources {

  public static  String NS_NOTIFICATIONS_KEY = "NS_NOTIFICATIONS";


  public Uri getSoundUri(){
    return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
  }

}