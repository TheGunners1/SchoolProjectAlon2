package com.example.alonsproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Game {
    public int num_players;
    public String place, time, name, phone, uid, key, gameType;
    boolean court;

    public Game() {

    }

    public Game(String uid, String type, String place, boolean court, String time, int num_players,
                String name, String phone) {

        this.uid = uid;
        this.place = place;
        this.court = court;
        this.time=time;
        this.num_players=num_players;
        this.name=name;
        this.phone=phone;
        this.gameType = type;
    }
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean getCourt() {
        return court;
    }

    public void setCourt(boolean court) {
        this.court = court;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum_players() {
        return num_players;
    }

    public void setNum_players(int num_players) {
        this.num_players = num_players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getGameType() { return gameType; }
    public void setGameType(String type) { this.gameType = type; }
    public Bitmap getBitmap(Resources resources) {
        switch(this.gameType) {
            case "Basketball":
                return BitmapFactory.decodeResource(resources, R.drawable.basketball);
            case "Football":
                return BitmapFactory.decodeResource(resources, R.drawable.football);
            case "Tennis":
                return BitmapFactory.decodeResource(resources, R.drawable.tennis);
            case "Volleyball":
                return BitmapFactory.decodeResource(resources, R.drawable.volleyball);
            default:
                return null;
        }
    }
}
