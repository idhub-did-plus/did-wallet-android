package com.idhub.wallet.setting;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.idhub.wallet.R;
import com.idhub.base.greendao.entity.TransactionRecordEntity;

public class NotificationUtils {
    private static final String CHANNEL_ID = "transaction";

    public static void sendTransactionNotification(Context context, TransactionRecordEntity transactionRecordEntity) {
        int currentTimeMillis = (int)System.currentTimeMillis();
        Intent intent = new Intent(context, TransactionDetailActivity.class);
        intent.putExtra("data", transactionRecordEntity);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, currentTimeMillis, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.wallet_channel_name);
            String description = context.getString(R.string.wallet_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(context.getString(R.string.wallet_transaction_notification_content_title))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("fromAddress：" + transactionRecordEntity.getFrom() + " \n"
                                + "toAddress：" + transactionRecordEntity.getTo() + " \n"
                                + "gasPrice：" + transactionRecordEntity.getGasPrice() + " \n"
                                + "gas: " + transactionRecordEntity.getGas() + " \n"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setShowWhen(true);
        String contractAddress = transactionRecordEntity.getContractAddress();
        if (TextUtils.isEmpty(contractAddress)) {
            builder.setContentText(context.getString(R.string.wallet_transaction_notification_content_text_etn));
        } else {
            builder.setContentText(transactionRecordEntity.getTokenName() + context.getString(R.string.wallet_transaction_notification_content_text_contract));
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(currentTimeMillis, builder.build());
    }
}
