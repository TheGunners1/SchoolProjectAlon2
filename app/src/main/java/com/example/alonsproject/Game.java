package com.example.alonsproject;

import android.graphics.Bitmap;

public class Game {
    private String gameType;
    private String place;
    private String time;
    private Bitmap bitmap;

    public Game(String type, String place, String time, Bitmap bitmap) {
        this.gameType = type;
        this.place = place;
        this.time = time;
        this.bitmap = bitmap;
    }
    public String getGameType() { return gameType; }
    public void setGameType(String type) { this.gameType = type; }
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public Bitmap getBitmap() { return bitmap; }
    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }
}
