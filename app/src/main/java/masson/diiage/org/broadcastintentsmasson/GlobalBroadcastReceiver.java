package masson.diiage.org.broadcastintentsmasson;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class GlobalBroadcastReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String BATTERY_CHANGED = "android.intent.action.BATTERY_CHANGED";
    final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {

        // Permet de faire une action de l'event de la batterie
        if(intent.getAction() == BATTERY_CHANGED){
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = (level / (float)scale) * 100;

            if(batteryPct == 20){
                Toast toast = Toast.makeText(context, "Battery: " + Math.round(batteryPct) + "%", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        // Permet de faire une action de l'event du SMS
        if(intent.getAction() == SMS_RECEIVED){
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Toast.makeText(context,"senderNum: "+ senderNum + ",\nmessage: " + message, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
