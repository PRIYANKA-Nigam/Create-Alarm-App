package com.example.createalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
TimePicker timePicker;
TextView textView;int hrs,min;
Button button; private Ringtone ringtone;
TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker=(TimePicker)findViewById(R.id.tp);
        textView=(TextView)findViewById(R.id.tt);
        t=(TextView)findViewById(R.id.textView2);
        button=(Button)findViewById(R.id.button3);
        ringtone= RingtoneManager.getRingtone(getApplicationContext(),RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
                t.setText(level.toString());
                if (level>40)
                { ringtone.play();}
                if (level<15){
                    ringtone.play();
                }

            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            ringtone.stop();
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
               hrs=hourOfDay;
               min=minute;
               textView.setText(" "+hrs +":"+min);
            }
        });

    }



    public void setTime(View view){
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Date date=new Date();
        Calendar calendar= Calendar.getInstance();
        Calendar cal_now=Calendar.getInstance();
        cal_now.setTime(date);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,hrs);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);
        if(calendar.before(cal_now)){
            calendar.add(Calendar.DATE,1);
        }
        Intent intent= new Intent(MainActivity.this,MyBroadCastReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,24444,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);





    }
}