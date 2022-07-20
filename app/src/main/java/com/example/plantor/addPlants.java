package com.example.plantor;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class addPlants extends AppCompatActivity{

    //Ready to pass
    TextInputEditText PlantName, PlantLocation, PlantDiscription;
    // wariable data in in date Picker
    ExtendedFloatingActionButton saveButton;
    //variable waterRepeat times Fertilizer repeat times is in the spinner dropdown
    //image view is in upload photo
    DBHelper plantsDB;

    //notification updater
    TextView waterRepeatnotif, waterCyclenotif, fertilizerRepeatnotif, fertilizerCycletnotif;

    //spinner dropdown
    String[] wateritemstimes = {"1x", "2x", "3x"};
    String[] wateritemsrepeat = {"everyday", "every 2days", "everyweek"};
    String[] fertilizeritemstimes = {"1x", "2x", "3x"};
    String[] fertilizeritemsrepeat = {"everyweek", "every 2weeks", "everymonth"};
    AutoCompleteTextView waterTimes, waterRepeat, fertilizerTimes, fertilizerRepeat;
    ArrayAdapter<String> adapterItems;

    //dateinputPicker
    EditText mDateFormat;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    //Upload Photo using cam and gallery
    private static final int GALLERY_REQ_CODE = 1000;
    private static final int SELECT_IMAGE_CODE = 1;
    ImageView InPhoto;
    ImageButton UpGallery, UpCam;

    //back button
    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plants);

        ////back button
        backArrow = findViewById(R.id.backButtonAddPlants);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //dropdown water & fertilizer
        waterTimes = findViewById(R.id.autoCompleteWaterTimes);
        adapterItems = new ArrayAdapter<String>(this,R.layout.drop_waterlist,wateritemstimes);
        waterTimes.setAdapter(adapterItems);
        waterRepeat = findViewById(R.id.autoCompleteWaterRepeat);
        adapterItems = new ArrayAdapter<String>(this,R.layout.drop_waterlist,wateritemsrepeat);
        waterRepeat.setAdapter(adapterItems);
        fertilizerTimes = findViewById(R.id.autoCompleteFertilizerTimes);
        adapterItems = new ArrayAdapter<String>(this,R.layout.drop_waterlist,fertilizeritemstimes);
        fertilizerTimes.setAdapter(adapterItems);
        fertilizerRepeat = findViewById(R.id.autoCompleteFertilizerRepeat);
        adapterItems = new ArrayAdapter<String>(this,R.layout.drop_waterlist,fertilizeritemsrepeat);
        fertilizerRepeat.setAdapter(adapterItems);

        //dateinputPicker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDateFormat = findViewById(R.id.InputDateAdaption1);
        mDateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        addPlants.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = month+"/"+dayOfMonth+"/"+year;
                mDateFormat.setText(date);
            }
        };

        //Upload Photo using cam and gallery
        InPhoto = findViewById(R.id.InputInsertPhoto);
        UpGallery = findViewById(R.id.imageButtonAddPhotoGallery);
        UpCam = findViewById(R.id.imageButtonAddPhotoCam);

        UpCam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(!checkCameraPermission()){
                    requestCameraPermission();

                }else {
                    Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camIntent, GALLERY_REQ_CODE);
                }
            }
        });

        UpGallery.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(!checkStoragePermission()){
                    requestStoragePermission();

                }else {
                    Intent galIntent = new Intent();
                    galIntent.setType("image/*");
                    galIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(galIntent, "Title"), SELECT_IMAGE_CODE);
                }
            }
        });


        //notification updater
        waterRepeatnotif = findViewById(R.id.setRepeatnotifW);
        waterCyclenotif = findViewById(R.id.setCyclenotifW);
        fertilizerRepeatnotif = findViewById(R.id.setRepeatnotifF);
        fertilizerCycletnotif = findViewById(R.id.setCyclenotifF);

        waterRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                waterRepeatnotif.setText(waterRepeat.getText().toString());
                if(waterRepeat.getText().toString().equals("everyday")){
                    waterCyclenotif.setText("1");
                }
                if(waterRepeat.getText().toString().equals("every 2days")){
                    waterCyclenotif.setText("2");
                }
                if(waterRepeat.getText().toString().equals("everyweek")){
                    waterCyclenotif.setText("7");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fertilizerRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fertilizerRepeatnotif.setText(fertilizerRepeat.getText().toString());
                if(fertilizerRepeat.getText().toString().equals("everyweek")){
                    fertilizerCycletnotif.setText("7");
                }
                if(fertilizerRepeat.getText().toString().equals("every 2weeks")){
                    fertilizerCycletnotif.setText("14");
                }
                if(fertilizerRepeat.getText().toString().equals("everymonth")){
                    fertilizerCycletnotif.setText("30");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Ready to pass

        PlantName = findViewById(R.id.plantName);
        //mDateFormat = findViewById(R.id.InputDateAdaption1); Plant Date
        // waterRepeat = findViewById(R.id.autoCompleteWaterRepeat);
        //waterTimes = findViewById(R.id.autoCompleteWaterTimes);
        //fertilizerRepeat = findViewById(R.id.autoCompleteFertilizerRepeat);
        //fertilizerTimes = findViewById(R.id.autoCompleteFertilizerTimes);
        PlantLocation = findViewById(R.id.plantLocation);
        PlantDiscription = findViewById(R.id.plantDiscription);
        //InPhoto = findViewById(R.id.InputInsertPhoto);
        saveButton = findViewById(R.id.saveAddButton);
        plantsDB = new DBHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plants_Name, plants_Date, plants_WaterRepeat, plants_WaterTimes, plants_FertilizerRepeat, plants_FertilizerTimes, plants_Location, plants_Discription;
                int waterprogress = 0, fertilizerprogress = 0;
                String waterswitch = "false";
                String fertilizerswitch = "false";

                plants_Name = PlantName.getText().toString().trim();
                plants_Date = mDateFormat.getText().toString();
                plants_WaterRepeat = waterRepeat.getText().toString();
                plants_WaterTimes = waterTimes.getText().toString();
                plants_FertilizerRepeat = fertilizerRepeat.getText().toString();
                plants_FertilizerTimes = fertilizerTimes.getText().toString();
                plants_Location = PlantLocation.getText().toString().trim();
                plants_Discription = PlantDiscription.getText().toString().trim();
                if(plants_Name.equals("")){
                    PlantName.setError("please enter plants name");
                }else if(plants_Date.equals("")){
                    Toast.makeText(addPlants.this, "please fill adaption date", Toast.LENGTH_SHORT).show();
                }else if(plants_WaterRepeat.equals("") || plants_WaterTimes.equals("") || plants_FertilizerRepeat.equals("") || plants_FertilizerTimes.equals("")){
                    Toast.makeText(addPlants.this, "please insert repeat and times", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean result = plantsDB.insertplants(plants_Name, plants_Date, plants_WaterRepeat, plants_WaterTimes, plants_FertilizerRepeat, plants_FertilizerTimes, plants_Location, plants_Discription, ImageToByte(InPhoto), waterprogress, fertilizerprogress, waterswitch, fertilizerswitch);
                    if(result == true){
                        Toast.makeText(addPlants.this, "New Plants Added!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(addPlants.this, MainPlants.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(addPlants.this).toBundle());
                    }else {
                        Toast.makeText(addPlants.this, "Plants not Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_IMAGE_CODE);
    }

    private boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return res2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQ_CODE);
    }

    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return  res1 && res2;
    }

    //Ready to pass
    private byte[] ImageToByte(ImageView inPhoto) {
        Bitmap bitmap = ((BitmapDrawable) inPhoto.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    //Upload Photo using cam and gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode == GALLERY_REQ_CODE){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            InPhoto.setImageBitmap(photo);
        }
        if(resultCode==RESULT_OK && requestCode == SELECT_IMAGE_CODE){
            Uri uri = data.getData();
            InPhoto.setImageURI(uri);
        }

    }

}