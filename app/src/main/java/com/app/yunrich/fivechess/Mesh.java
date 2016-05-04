package com.app.yunrich.fivechess;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/3.
 * map for chess
 */
public class Mesh {
    ArrayList<ArrayList<Integer>>  mesh = new ArrayList<ArrayList<Integer>>();
    int width = 9;
    int height = 13;

    public Mesh(){
        init();
    }

    public void init(){
        mesh.clear();
        for (int index =0; index < height; index++){
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            for (int indexh=0; indexh < width; indexh++)
                tmp.add(0);
            mesh.add(tmp);
        }
    }

    public int getPoint(int x, int y){
        return mesh.get(y).get(x);
    }

    public boolean add(int x, int y, int v){
        if (mesh.get(y).get(x).intValue() == 0){
            mesh.get(y).set(x, v);
            return true;
        }
        return false;
    }

    public boolean revert(int x, int y){
         mesh.get(y).set(x, 0);
         return true;
    }

    public void draw(Canvas canvas){
    }
}
