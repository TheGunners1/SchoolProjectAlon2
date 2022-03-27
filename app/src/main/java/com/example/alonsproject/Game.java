package com.example.alonsproject;

import android.graphics.Bitmap;

public class Game {
    public int num_players;
    public String place, court, time, name, phone, uid, key, gameType;
    public boolean has_started;
    private Bitmap bitmap;


    public Game(String uid, String type, String place, String court, String time, int num_players,
                String name, String phone, String key, boolean has_started, Bitmap bitmap) {

        this.uid = uid;
        this.place = place;
        this.court = court;
        this.time=time;
        this.num_players=num_players;
        this.name=name;
        this.phone=phone;
        this.key = key;
        this.has_started = has_started;
        this.gameType = type;
        this.bitmap = bitmap;
    }
    public boolean isHas_started() {
        return has_started;
    }

    public void setHas_started(boolean has_started) {
        this.has_started = has_started;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
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
    public Bitmap getBitmap() { return bitmap; }
    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }
}
