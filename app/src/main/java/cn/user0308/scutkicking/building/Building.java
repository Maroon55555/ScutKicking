package cn.user0308.scutkicking.building;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import cn.user0308.scutkicking.Collision;
import cn.user0308.scutkicking.MainActivity;

import static android.R.attr.bitmap;

/**
 * Created by Yuan Qiang on 2017/4/23.
 */

public class Building implements Collision {
    //protected int mId; //资源文件id
    protected float mPositionX;
    protected float mPositionY;
    protected float mAngle;
    protected float mRadius = 50;

    public Building(float positionX, float positionY) {
        //this.mId = id;
        this.mPositionX = positionX;
        this.mPositionY = positionY;
    }

    public void onDraw(Canvas canvas, Paint paint){
        canvas.drawCircle(mPositionX, mPositionY, mRadius, paint);
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

    @Override
    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

}
