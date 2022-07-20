package com.example.plantor;

public class Plant {

    int plants_no, waterprogress, fertilizerprogress;
    String plants_name, plants_date, plantswater_repeat, plantswater_times, plantsfertilizer_repeat, plantsfertilizer_times, plants_location, plants_discription, waterswitch, fertilizerswitch;
    byte[] plants_pic;

    public Plant(int plants_no, String plants_name, String plants_date, String plantswater_repeat, String plantswater_times, String plantsfertilizer_repeat, String plantsfertilizer_times, String plants_location, String plants_discription, byte[] plants_pic, int waterprogress, int fertilizerprogress, String waterswitch, String fertilizerswitch) {
        this.plants_no = plants_no;
        this.plants_name = plants_name;
        this.plants_date = plants_date;
        this.plantswater_repeat = plantswater_repeat;
        this.plantswater_times = plantswater_times;
        this.plantsfertilizer_repeat = plantsfertilizer_repeat;
        this.plantsfertilizer_times = plantsfertilizer_times;
        this.plants_location = plants_location;
        this.plants_discription = plants_discription;
        this.plants_pic = plants_pic;
        this.waterprogress = waterprogress;
        this.fertilizerprogress = fertilizerprogress;
        this.waterswitch = waterswitch;
        this.fertilizerswitch = fertilizerswitch;
    }

    public int getPlants_no() {
        return plants_no;
    }

    public void setPlants_no(int plants_no) {
        this.plants_no = plants_no;
    }

    public String getPlants_name() {
        return plants_name;
    }

    public void setPlants_name(String plants_name) {
        this.plants_name = plants_name;
    }

    public String getPlants_date() {
        return plants_date;
    }

    public void setPlants_date(String plants_date) {
        this.plants_date = plants_date;
    }

    public String getPlantswater_repeat() {
        return plantswater_repeat;
    }

    public void setPlantswater_repeat(String plantswater_repeat) {
        this.plantswater_repeat = plantswater_repeat;
    }

    public String getPlantswater_times() {
        return plantswater_times;
    }

    public void setPlantswater_times(String plantswater_times) {
        this.plantswater_times = plantswater_times;
    }

    public String getPlantsfertilizer_repeat() {
        return plantsfertilizer_repeat;
    }

    public void setPlantsfertilizer_repeat(String plantsfertilizer_repeat) {
        this.plantsfertilizer_repeat = plantsfertilizer_repeat;
    }

    public String getPlantsfertilizer_times() {
        return plantsfertilizer_times;
    }

    public void setPlantsfertilizer_times(String plantsfertilizer_times) {
        this.plantsfertilizer_times = plantsfertilizer_times;
    }

    public String getPlants_location() {
        return plants_location;
    }

    public void setPlants_location(String plants_location) {
        this.plants_location = plants_location;
    }

    public String getPlants_discription() {
        return plants_discription;
    }

    public void setPlants_discription(String plants_discription) {
        this.plants_discription = plants_discription;
    }

    public byte[] getPlants_pic() {
        return plants_pic;
    }

    public void setPlants_pic(byte[] plants_pic) {
        this.plants_pic = plants_pic;
    }

    public int getWaterprogress() {
        return waterprogress;
    }

    public void setWaterprogress(int waterprogress) {
        this.waterprogress = waterprogress;
    }

    public int getFertilizerprogress() {
        return fertilizerprogress;
    }

    public void setFertilizerprogress(int fertilizerprogress) {
        this.fertilizerprogress = fertilizerprogress;
    }

    public String getWaterswitch() {
        return waterswitch;
    }

    public void setWaterswitch(String waterswitch) {
        this.waterswitch = waterswitch;
    }

    public String getFertilizerswitch() {
        return fertilizerswitch;
    }

    public void setFertilizerswitch(String fertilizerswitch) {
        this.fertilizerswitch = fertilizerswitch;
    }
}
