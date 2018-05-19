
package br.com.mobilemind.nativescript.alarmnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.content.pm.PackageManager;
import android.os.Bundle;


public class AlarmReceiver extends BroadcastReceiver{

    static String TAG = "NS_ALARM_REC";
    private Resources resources;
    private NativescriptNotificationManager notificationManager;

    public void onReceive(Context context, Intent intent){

        Log.d(TAG, "##########################");
        Log.d(TAG, "###### AlarmReceiver");
        Log.d(TAG, "##########################");

        this.resources = new Resources(context);
        PackageManager pm = context.getPackageManager(); 
        Intent launchIntent = pm.getLaunchIntentForPackage(context.getPackageName());
        notificationManager = new NativescriptNotificationManager(context);
        Bundle originalBundle = intent.getExtras();        

        for(String key : originalBundle.keySet()){
            Log.d(TAG, "### copy bundle extra key=" + key + ", value=" + originalBundle.get(key));
            launchIntent.putExtra(key, originalBundle.get(key) + "");
        }

        Log.d("AlarmReceiver", "### getPackageName=" + context.getPackageName());

        if(originalBundle.containsKey("SHOW_NOTIFICATION")){

            Bundle newBundle = new Bundle();
            for(String key : originalBundle.keySet()){
                Log.d("AlarmReceiver", "### copy bundle extra key=" + key + ", value=" + originalBundle.get(key));
                newBundle.putString(key, originalBundle.get(key) + "");
            }

            String smallIconName = originalBundle.getString("NOTIFICATION_SMALL_ICON_NAME", null);
            String largeIconName = originalBundle.getString("NOTIFICATION_LARGE_ICON_NAME", null);
            String title = originalBundle.getString("NOTIFICATION_TITLE", null);
            String text = originalBundle.getString("NOTIFICATION_TEXT", null);            

            int smallIconId = resources.getSmallIconId();
            int lergeIconId = resources.getLargeIconId();

            if(smallIconName != null){
                smallIconId = resources.getIconId(smallIconName); 
            }

            if(largeIconName != null){
                lergeIconId = resources.getIconId(largeIconName); 
            }

            notificationManager.show(                
                title, 
                text,                 
                smallIconId,
                lergeIconId,
                resources.getSoundUri(),
                newBundle);  

            //show(String title, String text, int smallIconRes, int largeIconRes, Uri custonSound, Bundle bundle)

        }else{

            Log.d(TAG, "### starting activity for package: " + context.getApplicationContext().getPackageName());

            launchIntent.setPackage(null);

            context.startActivity(launchIntent);    
        }

    }
}