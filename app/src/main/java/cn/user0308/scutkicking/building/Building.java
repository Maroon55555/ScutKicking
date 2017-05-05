package cn.user0308.scutkicking.building;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Collideable;
import cn.user0308.scutkicking.Component.Line;
import cn.user0308.scutkicking.MainActivity;

import static android.R.attr.bitmap;

/**
 * Created by Yuan Qiang on 2017/4/23.
 */

abstract public class Building implements Collideable {
    //protected int mId; //资源文件id
    protected float mPositionX;
    protected float mPositionY;
    protected List<Line> mLineList;

    public Building(float positionX, float positionY) {
        //this.mId = id;
        this.mPositionX = positionX;
        this.mPositionY = positionY;
        mLineList = new ArrayList<>();
    }

    abstract public void onDraw(Canvas canvas, Paint paint);

    @Override
    public boolean collide(Collideable object) {
        for (Line line :mLineList){
            if (line.collide(line)){
                return true;
            }
        }
        return false;
    }

    public float getPositionX() {
        return mPositionX;
    }

    public void setPositionX(float positionX) {
        this.mPositionX = positionX;
    }

    public float getPositionY() {
        return mPositionY;
    }

    public void setPositionY(float positionY) {
        this.mPositionY = positionY;
    }

}
