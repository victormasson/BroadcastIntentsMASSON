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

        Toast.makeText(context, "Action: " + intent.getAction(), Toast.LENGTH_SHORT).show();

        if(intent.getAction() == BATTERY_CHANGED){
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = (level / (float)scale) * 100;

            if(20 - batteryPct == 0){
                Toast toast = Toast.makeText(context, "Battery: " + Math.round(batteryPct) + "%", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if(intent.getAction() == SMS_RECEIVED){
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Toast.makeText(context,"senderNum: "+ senderNum + ", message: " + message, Toast.LENGTH_LONG).show();
                }

//                Object[] pdus = (Object[])bundle.get("pdus");
//                final SmsMessage[] messages = new SmsMessage[pdus.length];
//                for (int i = 0; i < pdus.length; i++) {
//                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
//                }
//                if (messages.length > -1) {
//                    String log = messages[0].getMessageBody();
//                    Toast.makeText(context, log, Toast.LENGTH_LONG).show();
//                }
            }
        }
    }
}
