package com.example.sahil.memorygame;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Sahil on 18-02-2016.
 */
public class GameTemplate implements Parcelable {

    //An array for the area of the
    ArrayList<Integer> color_num = new ArrayList<Integer>();
    ArrayList<Integer> box_num = new ArrayList<Integer>();
    ArrayList<Integer> pos_num = new ArrayList<Integer>();

    public GameTemplate(ArrayList solution, ArrayList box_num, ArrayList pos_num) {
        this.color_num = solution;
        this.box_num = box_num;
        this.pos_num = pos_num;
    }

    public int returnColorAt(int num) {
        return color_num.get(num - 1);
    }

    public int returnBoxNumbersAt(int num) {
        return box_num.get(num - 1);
    }

    public int returnPosNumbersAt(int num) {
        return pos_num.get(num - 1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Storing the Student data to Parcel object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(color_num);
        dest.writeList(box_num);
        dest.writeList(pos_num);
    }

    /*Retrieving Student data from Parcel object. This constructor is invoked by the method
        createFromParcel(Parcel source) of the object CREATOR*/
    private GameTemplate(Parcel in){
        this.color_num = in.readArrayList(null);
        this.box_num = in.readArrayList(null);
        this.pos_num = in.readArrayList(null);
    }

    public static final Parcelable.Creator<GameTemplate> CREATOR = new Parcelable.Creator<GameTemplate>() {

        @Override
        public GameTemplate createFromParcel(Parcel source) {
            return new GameTemplate(source);
        }

        @Override
        public GameTemplate[] newArray(int size) {
            return new GameTemplate[size];
        }
    };
}
