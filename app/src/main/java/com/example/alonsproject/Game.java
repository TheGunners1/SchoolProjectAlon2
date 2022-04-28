package com.example.alonsproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public int num_players, Date_In_Numbers;
    public String place, date, time, name, phone, uid, key, gameType;
    public List<String> players;

    boolean court;

    public Game() {
        this.players = new ArrayList<String>();
    }

    public Game(String uid, String type, String place, boolean court, String date, int Date_In_Numbers, String time, int num_players,
                String name, String phone, String email) {
        this.uid = uid;
        this.place = place;
        this.court = court;
        this.date = date;
        this.Date_In_Numbers = Date_In_Numbers;
        this.time = time;
        this.num_players = num_players;
        this.players = new ArrayList<String>();
        this.players.add(email);
        this.name = name;
        this.phone = phone;
        this.gameType = type;
    }
    public String getPlace() {
        return place;
    }

    public boolean getCourt() {
        return court;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getNum_players() {
        return num_players;
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUid() {
        return uid;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() { return key; };


    public String getGameType() { return gameType; }
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
