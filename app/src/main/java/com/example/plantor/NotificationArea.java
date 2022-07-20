package com.example.plantor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Calendar;

public class NotificationArea extends AppCompatActivity {


    ImageButton backNotif;
    ExtendedFloatingActionButton doneB;
    DBHelper plantsDB;
    ArrayList<Plant> arrayList;
    Boolean Water_Switch, Fertilizer_Switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_area);

        Intent intent = getIntent();
        String plants_no, plants_Name, plants_WaterRepeat, plants_WaterTimes, plants_FertilizerRepeat, plants_FertilizerTimes, plants_Location, plants_Discription, plants_AdoptionDate;
        byte[] plants_Pic;
        String waterswitch, fertilizerswitch;
        plants_no = intent.getStringExtra("PLANTS_NO");
        plants_Name = intent.getStringExtra("PLANTS_NAME");
        plants_AdoptionDate = intent.getStringExtra("PLANTS_ADOPTIONDATE");
        plants_WaterRepeat = intent.getStringExtra("PLANTS_WATERREPREAT");
        plants_WaterTimes = intent.getStringExtra("PLANTS_WATERTIMES");
        plants_FertilizerRepeat = intent.getStringExtra("PLANTS_FERTILIZERREPEAT");
        plants_FertilizerTimes = intent.getStringExtra("PLANTS_FERTILIZERTIMES");
        plants_Location = intent.getStringExtra("PLANTS_LOCATION");
        plants_Discription = intent.getStringExtra("PLANTS_DESCRIPTION");
        plants_Pic = intent.getByteArrayExtra("PLANTS_PIC");
        waterswitch = intent.getStringExtra("WATERSWITCH");
        fertilizerswitch = intent.getStringExtra("FERTILIZERSWITCH");
        Bitmap bitmap = BitmapFactory.decodeByteArray(plants_Pic, 0, plants_Pic.length);

        TextView AdapDate = findViewById(R.id.AAdate);
        AdapDate.setText("Adaption date: " + plants_AdoptionDate);

        SwitchMaterial WaterSwitch;
        SwitchMaterial FertilizerSwitch;

        plantsDB = new DBHelper(this);
        FertilizerSwitch = findViewById(R.id.Fertilizer);
        WaterSwitch = findViewById(R.id.Water);

        if (waterswitch.equals("true")) {
            Water_Switch = true;
        }else{
            Water_Switch = false;
        }
        if (fertilizerswitch.equals("true")) {
            Fertilizer_Switch = true;
        }else{
            Fertilizer_Switch = false;
        }

        WaterSwitch.setChecked(Water_Switch);
        FertilizerSwitch.setChecked(Fertilizer_Switch);

        WaterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    plantsDB.updatewaterswitch(String.valueOf(plants_no), "true");
                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.HOUR_OF_DAY, 6);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND,0);

                    Intent intent = new Intent(NotificationArea.this, NotificationReciever.class);
                    intent.putExtra("PLANTS_NO", String.valueOf(plants_no));
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
//                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }else{
                    plantsDB.updatewaterswitch(String.valueOf(plants_no), "false");
                }

            }
        });

        FertilizerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    plantsDB.updatefertilizerswitch(String.valueOf(plants_no), "true");
                    plantsDB.updatewaterswitch(String.valueOf(plants_no), "true");
                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.HOUR_OF_DAY, 6);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND,0);

                    Intent intent = new Intent(NotificationArea.this, NotificationReciever1.class);
                    intent.putExtra("PLANTS_NO", String.valueOf(plants_no));
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
                }else{
                    plantsDB.updatefertilizerswitch(String.valueOf(plants_no), "false");
                }

            }
        });

        doneB = findViewById(R.id.doneButton);
        doneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NotificationArea.this);
                builder.setTitle(plants_Name)
                        .setMessage("Change Notification")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(NotificationArea.this, plants_Name + " Notification have changed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NotificationArea.this, MainPlants.class);
                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(NotificationArea.this).toBundle());
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();

            }
        });


        backNotif = findViewById(R.id.backButtonNNN);
        backNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plantsDB.updatewaterswitch(String.valueOf(plants_no), waterswitch);
                plantsDB.updatefertilizerswitch(String.valueOf(plants_no), fertilizerswitch);
                onBackPressed();
            }
        });
    }
}