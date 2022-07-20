package com.example.plantor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainPlants extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //add plants
    ExtendedFloatingActionButton add_Plants;

    //plantlisting
    GridView listview;
    ArrayList<Plant> arrayList;
    ArrayList<Plant> searchList;
    PlantsAdapter adapter;
    DBHelper plantsDB;
    Plant plant;

    //searching
    SearchView searchView;
    int getPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_plants);

        //add plants
        add_Plants = findViewById(R.id.addplantsButton);
        add_Plants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPlants.this, addPlants.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainPlants.this).toBundle());
            }
        });

        //plantlisting
        listview = findViewById(R.id.PlantContentListview);
        plantsDB = new DBHelper(this);
        showPlantsData();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(getPosition == 1){
                    plant = searchList.get(position);
                }else{
                    plant = arrayList.get(position);
                }

                String plants_Name, plants_WaterRepeat, plants_WaterTimes, plants_FertilizerRepeat, plants_FertilizerTimes, plants_Location, plants_Discription, plants_AdoptionDate;
                int plants_no, waterprogress, fertilizerprogress;
                byte[] plants_Pic;
                String waterswitch, fertilizerswitch;
                plants_no = plant.getPlants_no();
                plants_Name = plant.getPlants_name();
                plants_AdoptionDate = plant.getPlants_date();
                plants_WaterRepeat = plant.getPlantswater_repeat();
                plants_WaterTimes = plant.getPlantswater_times();
                plants_FertilizerRepeat = plant.getPlantsfertilizer_repeat();
                plants_FertilizerTimes = plant.getPlantsfertilizer_times();
                plants_Location = plant.getPlants_location();
                plants_Discription = plant.getPlants_discription();
                plants_Pic = plant.getPlants_pic();
                waterprogress = plant.getWaterprogress();
                fertilizerprogress = plant.getFertilizerprogress();
                waterswitch = plant.getWaterswitch();
                fertilizerswitch = plant.getFertilizerswitch();

                Intent intent = new Intent(MainPlants.this, ShowPlants.class);
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
                intent.putExtra("WATERPROGRESS", String.valueOf(waterprogress));
                intent.putExtra("FERTILIZERPROGRESS", String.valueOf(fertilizerprogress));
                intent.putExtra("WATERSWITCH", waterswitch);
                intent.putExtra("FERTILIZERSWITCH", fertilizerswitch);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainPlants.this).toBundle());

            }
        });

        //Search
        searchView = findViewById(R.id.searching);
        searchView.setOnQueryTextListener( this );

    }

    private void showPlantsData() {
        arrayList = plantsDB.getPlantsData();
        adapter = new PlantsAdapter(this, arrayList);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        searchList = new ArrayList<>();
        getPosition = 1;
        for(Plant plant:arrayList){
            String plant_name = plant.getPlants_name().toLowerCase();
            if(plant_name.contains(newText)){
                searchList.add(plant);
            }
        }
        adapter.searchFilter(searchList);
        return true;
    }
}