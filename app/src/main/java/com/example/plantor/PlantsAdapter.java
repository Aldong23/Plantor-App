package com.example.plantor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class PlantsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Plant> arrayList;

    public PlantsAdapter(Context context, ArrayList<Plant> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.showplantlayout, null);
        ImageView plant_Image = convertView.findViewById(R.id.plantsImage);
        TextView plant_Name = convertView.findViewById(R.id.plansNames);
        Plant plants = arrayList.get(position);
        String plantsName = plants.getPlants_name();
        byte[] plantsImage = plants.getPlants_pic();
        Bitmap bitmap = BitmapFactory.decodeByteArray(plantsImage, 0, plantsImage.length);
        plant_Name.setText(plantsName);
        plant_Image.setImageBitmap(bitmap);

        return convertView;
    }

    public void searchFilter(ArrayList<Plant> searchList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(searchList);
        notifyDataSetChanged();
    }
}
