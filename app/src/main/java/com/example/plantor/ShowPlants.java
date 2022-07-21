package com.example.plantor;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ShowPlants extends AppCompatActivity {

    TextView TVplantname, TVlocation, TVdescription, TVwaterrepeat, TVwatertimes, TVfertilizerrepeat, TVfertilizertimes;
    ImageView IVplantpic;
    ImageButton backButton, deleteButton, editButton, notifButton, complete, reset;
    DBHelper plantsDB;
    ArrayList<Plant> arrayList;

    //progression
    Button WaterProgress, FertilizerProgress;
    LinearProgressIndicator WaterP, FertelizerP;
    int waterProgress;
    int fertProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plants);

        //showing pants
        TVplantname = findViewById(R.id.plantTitle);
        TVlocation = findViewById(R.id.inLocation);
        TVdescription = findViewById(R.id.indescription);
        TVwatertimes = findViewById(R.id.waterinfoTimes);
        TVfertilizertimes = findViewById(R.id.fertilizerinfoTimes);
        TVwaterrepeat = findViewById(R.id.waterinfoRepeat);
        TVfertilizerrepeat = findViewById(R.id.fertilizrinfoRepeat);
        IVplantpic = findViewById(R.id.PlantphotoPass);

        Intent intent = getIntent();
        String plants_no, waterprogress, fertilizerprogress, plants_Name, plants_WaterRepeat, plants_WaterTimes, plants_FertilizerRepeat, plants_FertilizerTimes, plants_Location, plants_Discription, plants_AdoptionDate;
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
        waterprogress = intent.getStringExtra("WATERPROGRESS");
        fertilizerprogress = intent.getStringExtra("FERTILIZERPROGRESS");
        waterswitch = intent.getStringExtra("WATERSWITCH");
        fertilizerswitch = intent.getStringExtra("FERTILIZERSWITCH");

        Bitmap bitmap = BitmapFactory.decodeByteArray(plants_Pic, 0, plants_Pic.length);
        IVplantpic.setImageBitmap(bitmap);
        TVplantname.setText(plants_Name);
        if(plants_Location.equals("")){
            TVlocation.setText(plants_Location);
        }else {
            TVlocation.setText("@" + plants_Location);
        }
        TVdescription.setText(plants_Discription);
        TVwatertimes.setText(plants_WaterTimes);
        TVfertilizertimes.setText(plants_FertilizerTimes);
        TVwaterrepeat.setText(plants_WaterRepeat);
        TVfertilizerrepeat.setText(plants_FertilizerRepeat);

        //Deleting Plants
        deleteButton = findViewById(R.id.deleteButton);

        plantsDB = new DBHelper(this);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(ShowPlants.this);
                builder.setTitle(plants_Name)
                        .setMessage("Delete this plant?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int res = plantsDB.deletePlants(String.valueOf(plants_no));
                                if(res > 0){
                                    Cursor res1 = plantsDB.showPlants();
                                    if(res1.getCount()==0){
                                        Intent intent = new Intent(ShowPlants.this, MainEmptyPlant.class);
                                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ShowPlants.this).toBundle());
                                        Toast.makeText(ShowPlants.this, "All plant is deleted", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Intent intent = new Intent(ShowPlants.this, MainPlants.class);
                                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ShowPlants.this).toBundle());
                                        Toast.makeText(ShowPlants.this, plants_Name + " is deleted", Toast.LENGTH_SHORT).show();
                                    }

                                }

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

        //Update Plants
        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowPlants.this, EditPlants.class);
                intent.putExtra("PLANTS_NO", String.valueOf(plants_no));
                intent.putExtra("PLANTS_NAME", plants_Name);
                intent.putExtra("PLANTS_ADOPTIONDATE", plants_AdoptionDate);
                intent.putExtra("PLANTS_WATERREPREAT", plants_WaterRepeat);
                intent.putExtra("PLANTS_WATERTIMES", plants_WaterTimes);
                intent.putExtra("PLANTS_FERTILIZERREPEAT", plants_FertilizerRepeat);
                intent.putExtra("PLANTS_FERTILIZERTIMES", plants_FertilizerTimes);
                intent.putExtra("PLANTS_LOCATION", plants_Location);
                intent.putExtra("PLANTS_DESCRIPTION", plants_Discription);
                intent.putExtra("PLANTS_PIC", plants_Pic);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ShowPlants.this).toBundle());

            }
        });

        //back button
        backButton = findViewById(R.id.backButtonShowPlants);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //notif area

        notifButton = findViewById(R.id.notifSwitch);
        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPlants.this, NotificationArea.class);
                intent.putExtra("PLANTS_NO", String.valueOf(plants_no));
                intent.putExtra("PLANTS_NAME", plants_Name);
                intent.putExtra("PLANTS_ADOPTIONDATE", plants_AdoptionDate);
                intent.putExtra("PLANTS_WATERREPREAT", plants_WaterRepeat);
                intent.putExtra("PLANTS_WATERTIMES", plants_WaterTimes);
                intent.putExtra("PLANTS_FERTILIZERREPEAT", plants_FertilizerRepeat);
                intent.putExtra("PLANTS_FERTILIZERTIMES", plants_FertilizerTimes);
                intent.putExtra("PLANTS_LOCATION", plants_Location);
                intent.putExtra("PLANTS_DESCRIPTION", plants_Discription);
                intent.putExtra("PLANTS_PIC", plants_Pic);
                intent.putExtra("WATERSWITCH", waterswitch);
                intent.putExtra("FERTILIZERSWITCH", fertilizerswitch);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ShowPlants.this).toBundle());
            }

        });

        //Progress

        TextView waterV, fertV;
        waterV = findViewById(R.id.waterneed);
        fertV = findViewById(R.id.fertilizerneed);
        WaterProgress = findViewById(R.id.infoWaterButton);
        FertilizerProgress = findViewById(R.id.infoFertilizerButton);
        complete = findViewById(R.id.complete);
        reset = findViewById(R.id.reset);
        WaterP = findViewById(R.id.waterProgress);
        FertelizerP = findViewById(R.id.fertilizerProgress);
        waterProgress = Integer.parseInt(waterprogress);
        fertProgress = Integer.parseInt(fertilizerprogress);
        WaterP.setProgress(waterProgress);
        FertelizerP.setProgress(fertProgress);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowPlants.this);
                builder.setTitle(plants_Name)
                        .setMessage("Complete progress note")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               waterProgress = 100;
                                fertProgress = 100;
                                WaterP.setProgress(waterProgress);
                                FertelizerP.setProgress(fertProgress);
                                WaterP.setMax(100);
                                FertelizerP.setMax(100);
                                waterV.setVisibility(View.INVISIBLE);
                                fertV.setVisibility(View.INVISIBLE);
                                plantsDB.updatefertilizerprogress(String.valueOf(plants_no), waterProgress);
                                plantsDB.updatewaterprogress(String.valueOf(plants_no), fertProgress);
                                reset.setVisibility(View.VISIBLE);
                                reset.setImageResource(R.drawable.resetgreen_button);
                                complete.setImageResource(R.drawable.complete_button);
                                FertilizerProgress.setEnabled(false);
                                WaterProgress.setEnabled(false);
                                complete.setEnabled(false);
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

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowPlants.this);
                builder.setTitle(plants_Name)
                        .setMessage("Reset progress note")
                        .setPositiveButton("Water", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                waterProgress = 0;
                                WaterP.setProgress(waterProgress);
                                WaterP.setMin(0);
                                waterV.setVisibility(View.VISIBLE);
                                plantsDB.updatewaterprogress(String.valueOf(plants_no), waterProgress);
                                reset.setImageResource(R.drawable.reset_button);
                                if (waterProgress == 0 && Integer.parseInt(fertilizerprogress) == 0) {
                                    reset.setVisibility(View.INVISIBLE);
                                }
                                complete.setImageResource(R.drawable.check_button);
                                WaterProgress.setEnabled(true);
                                complete.setEnabled(true);
                            }
                        })
                        .setNegativeButton("Fertilizer", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fertProgress = 0;
                                FertelizerP.setProgress(fertProgress);
                                FertelizerP.setMin(0);
                                fertV.setVisibility(View.VISIBLE);
                                plantsDB.updatefertilizerprogress(String.valueOf(plants_no), fertProgress);
                                reset.setImageResource(R.drawable.reset_button);
                                if (Integer.parseInt(waterprogress) == 0 && fertProgress == 0) {
                                    reset.setVisibility(View.INVISIBLE);
                                }
                                complete.setImageResource(R.drawable.check_button);
                                FertilizerProgress.setEnabled(true);
                                complete.setEnabled(true);
                            }
                        })
                        .setNeutralButton("All", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                waterProgress = 0;
                                fertProgress = 0;
                                WaterP.setProgress(waterProgress);
                                FertelizerP.setProgress(fertProgress);
                                WaterP.setMin(0);
                                FertelizerP.setMin(0);
                                waterV.setVisibility(View.VISIBLE);
                                fertV.setVisibility(View.VISIBLE);
                                plantsDB.updatefertilizerprogress(String.valueOf(plants_no), waterProgress);
                                plantsDB.updatewaterprogress(String.valueOf(plants_no), fertProgress);
                                reset.setImageResource(R.drawable.reset_button);
                                reset.setVisibility(View.INVISIBLE);
                                complete.setImageResource(R.drawable.check_button);
                                FertilizerProgress.setEnabled(true);
                                WaterProgress.setEnabled(true);
                                complete.setEnabled(true);
                            }
                        });
                builder.create().show();
            }
        });

        if (Integer.parseInt(waterprogress) == 0) {
            waterV.setVisibility(View.VISIBLE);
            reset.setVisibility(View.INVISIBLE);

        }else{
            waterV.setVisibility(View.INVISIBLE);
        }
        if (Integer.parseInt(waterprogress) <= 99) {
            WaterProgress.setEnabled(true);
        }else{
            WaterProgress.setEnabled(false);
        }
        if (Integer.parseInt(fertilizerprogress) <= 99) {
            FertilizerProgress.setEnabled(true);
        }else{
            FertilizerProgress.setEnabled(false);
        }
        if (Integer.parseInt(fertilizerprogress) == 0) {
            fertV.setVisibility(View.VISIBLE);
            reset.setVisibility(View.INVISIBLE);
        }else{
            fertV.setVisibility(View.INVISIBLE);
        }
        if (waterProgress >= 100 && fertProgress >=100) {
            reset.setVisibility(View.VISIBLE);
            complete.setImageResource(R.drawable.complete_button);
            reset.setImageResource(R.drawable.resetgreen_button);
            complete.setEnabled(false);
        }
        if (Integer.parseInt(waterprogress) >= 1 || Integer.parseInt(fertilizerprogress) >=1) {
            reset.setVisibility(View.VISIBLE);
        }
        if (Integer.parseInt(waterprogress) <= 99 || Integer.parseInt(fertilizerprogress) <=99) {
            complete.setImageResource(R.drawable.check_button);
            complete.setEnabled(true);
        }

        WaterProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowPlants.this);
                builder.setTitle(plants_Name)
                        .setMessage("Complete Water")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(plants_WaterTimes.equals("1x")){
                                    waterProgress = waterProgress + 100;
                                    WaterP.setProgress(waterProgress);
                                    WaterP.setMax(100);
                                    plantsDB.updatewaterprogress(String.valueOf(plants_no), waterProgress);
                                    waterV.setVisibility(View.INVISIBLE);
                                    reset.setVisibility(View.VISIBLE);
                                }
                                if(plants_WaterTimes.equals("2x")){
                                    waterProgress = waterProgress + 50;
                                    WaterP.setProgress(waterProgress);
                                    WaterP.setMax(100);
                                    plantsDB.updatewaterprogress(String.valueOf(plants_no), waterProgress);
                                    waterV.setVisibility(View.INVISIBLE);
                                    reset.setVisibility(View.VISIBLE);
                                }
                                if(plants_WaterTimes.equals("3x")){
                                    waterProgress = waterProgress + 34;
                                    WaterP.setProgress(waterProgress);
                                    WaterP.setMax(100);
                                    plantsDB.updatewaterprogress(String.valueOf(plants_no), waterProgress);
                                    waterV.setVisibility(View.INVISIBLE);
                                    reset.setVisibility(View.VISIBLE);
                                }
                                if (waterProgress >= 100 && fertProgress >=100) {
                                    reset.setVisibility(View.VISIBLE);
                                    reset.setImageResource(R.drawable.resetgreen_button);
                                    complete.setImageResource(R.drawable.complete_button);
                                    complete.setEnabled(false);
                                }
                                if (waterProgress >= 100 ) {
                                    WaterProgress.setEnabled(false);
                                }
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

        FertilizerProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowPlants.this);
                builder.setTitle(plants_Name)
                        .setMessage("Complete Fertilizer")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(plants_FertilizerTimes.equals("1x")){
                                    fertProgress = fertProgress + 100;
                                    FertelizerP.setProgress(fertProgress);
                                    FertelizerP.setMax(100);
                                    plantsDB.updatefertilizerprogress(String.valueOf(plants_no), fertProgress);
                                    fertV.setVisibility(View.INVISIBLE);
                                    reset.setVisibility(View.VISIBLE);

                                }
                                if(plants_FertilizerTimes.equals("2x")){
                                    fertProgress = fertProgress + 50;
                                    FertelizerP.setProgress(fertProgress);
                                    FertelizerP.setMax(100);
                                    plantsDB.updatefertilizerprogress(String.valueOf(plants_no), fertProgress);
                                    fertV.setVisibility(View.INVISIBLE);
                                    reset.setVisibility(View.VISIBLE);
                                }
                                if(plants_FertilizerTimes.equals("3x")){
                                    fertProgress = fertProgress + 34;
                                    FertelizerP.setProgress(fertProgress);
                                    FertelizerP.setMax(100);
                                    plantsDB.updatefertilizerprogress(String.valueOf(plants_no), fertProgress);
                                    fertV.setVisibility(View.INVISIBLE);
                                    reset.setVisibility(View.VISIBLE);
                                }
                                if (waterProgress >= 100 && fertProgress >=100) {
                                    reset.setVisibility(View.VISIBLE);
                                    reset.setImageResource(R.drawable.resetgreen_button);
                                    complete.setImageResource(R.drawable.complete_button);
                                    complete.setEnabled(false);
                                }
                                if (fertProgress >= 100 ) {
                                    FertilizerProgress.setEnabled(false);
                                }

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



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowPlants.this, MainPlants.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ShowPlants.this).toBundle());
    }


}
