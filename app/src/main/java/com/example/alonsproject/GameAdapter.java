package com.example.alonsproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends ArrayAdapter<Game> {
    Context context;
    List<Game> objects;

    public GameAdapter( Context context, int resource, int textViewResourceId, List<Game> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.gamesentry,parent,false);

        TextView tvGameType = (TextView)view.findViewById(R.id.tvGameType);
        TextView tvPlace = (TextView)view.findViewById(R.id.tvPlace);
        ImageView ivImg=(ImageView)view.findViewById(R.id.ivImg);
        TextView tvDate = (TextView)view.findViewById(R.id.tvDate);

        Game temp = objects.get(position);

        tvGameType.setText(temp.getGameType());
        tvPlace.setText(String.valueOf(temp.getPlace()));
        tvDate.setText(temp.getDate());
        ivImg.setImageBitmap(temp.getBitmap(parent.getResources()));
        return view;
    }

}