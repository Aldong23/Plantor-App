package com.example.plantor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Front extends AppCompatActivity {

    Button getStartButton;
    DBHelper plantsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        plantsDB = new DBHelper(this);

        //fisrtTime Opening
        SharedPreferences preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall", "");

        if(FirstTime.equals("Yes")){
            Cursor res = plantsDB.showPlants();
            if(res.getCount()==0){
                Intent intent = new Intent(Front.this, MainEmptyPlant.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(Front.this, MainPlants.class);
                startActivity(intent);
            }
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }

        //Button
        getStartButton = findViewById(R.id.getstartedbutton);

        getStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Front.this, MainEmptyPlant.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(Front.this).toBundle());
            }
        });
    }
}