package com.example.plantor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainEmptyPlant extends AppCompatActivity {

    Button addPlantsEm;

    //back
    ImageButton back_B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_empty_plant);

        addPlantsEm = findViewById(R.id.emptyAddPlants);
        addPlantsEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainEmptyPlant.this, addPlants.class);
                startActivity(intent);
            }
        });

        //back
        back_B = findViewById(R.id.backButtonAddPlants);
        back_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 1 ) {
            manager.popBackStack();
        } else {
            // if there is only one entry in the backstack, show the home screen
            moveTaskToBack(true);
        }
    }

}