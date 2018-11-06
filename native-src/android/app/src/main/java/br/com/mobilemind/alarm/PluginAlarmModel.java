package br.com.mobilemind.alarm;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PluginAlarmModel {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private int id;

    // default alarm action
    private String alarmAction;
    // default button action click for notification
    private String notificationAction;


    private Date date;
    private boolean snoozeEnabled;
    private int snoozeInterval = 15;

    private String soundName = "";

    private String buttonOkText = "OK";
    private String buttonSnoozeText = "Soneca";
    private String buttonOpenText = "Abrir";

    private String alertSmallIcon = "";
    private String alertLargeIcon = "";

    private String notificationTitle = "";
    private String notificationBody = "";
    private String notificationColor = "";

    private String alarmBootstrapText = "";

    private Map<String, Object> bundle = new HashMap<>();

    // open actvity in alarm receiver
    private boolean startActivityOnReceive;
    // show notification in alarm receiver
    private boolean notifyOnReceive;

    // insistent vibrate and sound
    private boolean insistent;
    // repeat interval, while not cancel
    private long repeatTime;

    private boolean showButtonOpen;
    private boolean showButtonOk;
    private boolean showButtonSnooze;

    private boolean alarmBootstrap;

    private boolean enabled = true;


    public boolean isAlarmBootstrap() {
        return alarmBootstrap;
    }

    public void setAlarmBootstrap(boolean alarmBootstrap) {
        this.alarmBootstrap = alarmBootstrap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlarmAction() {
        return alarmAction;
    }

    public void setAlarmAction(String alarmAction) {
        this.alarmAction = alarmAction;
    }

    public String getNotificationAction() {
        return notificationAction;
    }

    public void setNotificationAction(String notificationAction) {
        this.notificationAction = notificationAction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSnoozeEnabled() {
        return snoozeEnabled;
    }

    public void setSnoozeEnabled(boolean snoozeEnabled) {
        this.snoozeEnabled = snoozeEnabled;
    }

    public int getSnoozeInterval() {
        return snoozeInterval;
    }

    public void setSnoozeInterval(int snoozeInterval) {
        this.snoozeInterval = snoozeInterval;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public String getButtonOkText() {
        return buttonOkText;
    }

    public void setButtonOkText(String buttonOkText) {
        this.buttonOkText = buttonOkText;
    }

    public String getButtonSnoozeText() {
        return buttonSnoozeText;
    }

    public void setButtonSnoozeText(String buttonSnoozeText) {
        this.buttonSnoozeText = buttonSnoozeText;
    }

    public String getButtonOpenText() {
        return buttonOpenText;
    }

    public void setButtonOpenText(String buttonOpenText) {
        this.buttonOpenText = buttonOpenText;
    }

    public String getAlertSmallIcon() {
        return alertSmallIcon;
    }

    public void setAlertSmallIcon(String alertSmallIcon) {
        this.alertSmallIcon = alertSmallIcon;
    }

    public String getAlertLargeIcon() {
        return alertLargeIcon;
    }

    public void setAlertLargeIcon(String alertLargeIcon) {
        this.alertLargeIcon = alertLargeIcon;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public String getNotificationColor() {
        return notificationColor;
    }

    public void setNotificationColor(String notificationColor) {
        this.notificationColor = notificationColor;
    }

    public Map<String, Object> getBundle() {
        return bundle;
    }

    public void setBundle(Map<String, Object> bundle) {
        this.bundle = bundle;
    }

    public boolean isStartActivityOnReceive() {
        return startActivityOnReceive;
    }

    public void setStartActivityOnReceive(boolean startActivityOnReceive) {
        this.startActivityOnReceive = startActivityOnReceive;
    }

    public boolean isNotifyOnReceive() {
        return notifyOnReceive;
    }

    public void setNotifyOnReceive(boolean notifyOnReceive) {
        this.notifyOnReceive = notifyOnReceive;
    }

    public boolean isInsistent() {
        return insistent;
    }

    public void setInsistent(boolean insistent) {
        this.insistent = insistent;
    }

    public long getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(long repeatTime) {
        this.repeatTime = repeatTime;
    }

    public boolean isShowButtonOpen() {
        return showButtonOpen;
    }

    public void setShowButtonOpen(boolean showButtonOpen) {
        this.showButtonOpen = showButtonOpen;
    }

    public boolean isShowButtonOk() {
        return showButtonOk;
    }

    public void setShowButtonOk(boolean showButtonOk) {
        this.showButtonOk = showButtonOk;
    }

    public boolean isShowButtonSnooze() {
        return showButtonSnooze;
    }

    public void setShowButtonSnooze(boolean showButtonSnooze) {
        this.showButtonSnooze = showButtonSnooze;
    }

    public String getAlarmBootstrapText() {
        return alarmBootstrapText;
    }

    public void setAlarmBootstrapText(String alarmBootstrapText) {
        this.alarmBootstrapText = alarmBootstrapText;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addBundle(String key, Object value){
        this.bundle.put(key, value);
    }


    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("alarmAction", this.alarmAction);
        json.put("id", this.id);
        json.put("date", this.date.getTime());
        json.put("snoozeEnabled", this.snoozeEnabled);
        json.put("soundName", this.soundName);
        json.put("snoozeInterval", this.snoozeInterval);
        json.put("repeatTime", this.repeatTime);
        json.put("buttonOkText", this.buttonOkText);
        json.put("buttonSnoozeText", this.buttonSnoozeText);
        json.put("buttonOpenText", this.buttonOpenText);
        json.put("notificationBody", this.notificationBody);
        json.put("notificationTitle", this.notificationTitle);
        json.put("alertSmallIcon", this.alertSmallIcon);
        json.put("alertLargeIcon", this.alertLargeIcon);
        json.put("notificationColor", this.notificationColor);
        json.put("notifyOnReceive", this.notifyOnReceive);
        json.put("startActivityOnReceive", this.startActivityOnReceive);
        json.put("notificationAction", this.notificationAction);
        json.put("insistent", this.insistent);
        json.put("showButtonOpen", this.showButtonOpen);
        json.put("showButtonOk", this.showButtonOk);
        json.put("showButtonSnooze", this.showButtonSnooze);
        json.put("alarmBootstrapText", this.alarmBootstrapText);
        json.put("enabled", this.enabled);


        JSONObject b = new JSONObject();

        Set<String> keys = bundle.keySet();
        for(String key : keys){
            b.put(key, bundle.get(key));
        }

        json.put("bundle", b);

        return  json;
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.alarmAction = json.optString("alarmAction", "");
        this.id = json.optInt("id", 0);
        this.date = new Date(json.optLong("date"));
        this.snoozeEnabled = json.optBoolean("snoozeEnabled", false);
        this.soundName = json.optString("soundName", "");
        this.snoozeInterval = json.optInt("snoozeInterval", 0);
        this.repeatTime = json.optLong("repeatTime", 0L);
        this.buttonOkText = json.optString("buttonOkText", "");
        this.buttonSnoozeText = json.optString("buttonSnoozeText", "");
        this.buttonOpenText = json.optString("buttonOpenText", "");
        this.notificationBody = json.optString("notificationBody", "");
        this.notificationAction = json.optString("notificationAction", "");
        this.notificationTitle = json.optString("notificationTitle", "");
        this.alertSmallIcon = json.optString("alertSmallIcon", "");
        this.alertLargeIcon = json.optString("alertLargeIcon", "");
        this.notificationColor = json.optString("notificationColor", "");
        this.notifyOnReceive = json.optBoolean("notifyOnReceive", false);
        this.startActivityOnReceive = json.optBoolean("startActivityOnReceive", false);
        this.notificationAction = json.optString("notificationAction", "");
        this.insistent = json.optBoolean("insistent", false);

        this.showButtonOpen = json.optBoolean("showButtonOpen", false);
        this.showButtonOk = json.optBoolean("showButtonOk", false);
        this.showButtonSnooze = json.optBoolean("showButtonSnooze", false);

        this.alarmBootstrapText = json.optString("alarmBootstrapText", "");
        this.enabled = json.optBoolean("enabled", false);

        JSONObject b = json.getJSONObject("bundle");
        if(b.length() > 0) {
            Iterator<String> keys = b.keys();
            for (String key = keys.next(); keys.hasNext(); ) {
                this.addBundle(key, b.get(key));
            }
        }
    }

    @Override
    public String toString() {
        return id + " - " + dateFormat.format(date);
    }
}
