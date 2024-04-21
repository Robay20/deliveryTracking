package com.example.packettracer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String param = extras.getString("param");
            if (param != null) {
                Toast t = Toast.makeText(ctx, param, Toast.LENGTH_SHORT);
                t.show();
            } else {
                Toast.makeText(ctx, "Paramètre 'param' non trouvé", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ctx, "Aucune donnée supplémentaire trouvée", Toast.LENGTH_SHORT).show();
        }
    }
}