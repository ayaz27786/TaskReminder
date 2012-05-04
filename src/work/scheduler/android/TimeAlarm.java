package work.scheduler.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class TimeAlarm extends BroadcastReceiver {

	 NotificationManager nm;

	 @Override
	 public void onReceive(Context context, Intent intent) {
		 
	  nm = (NotificationManager) context
	    .getSystemService(Context.NOTIFICATION_SERVICE);
	
      int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);


    
	  CharSequence message = "notification is on";
	  PendingIntent contentIntent = PendingIntent.getBroadcast(context, iUniqueId,
	    new Intent(), 0);
	  Notification notif = new Notification(R.drawable.icon,
	    message, System.currentTimeMillis());
	  notif.setLatestEventInfo(context, "new notification", message, contentIntent);
	

    notif.vibrate = new long[]{100, 250, 100, 500};
    notif.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS;

	  nm.notify(iUniqueId, notif);
	 }
	}