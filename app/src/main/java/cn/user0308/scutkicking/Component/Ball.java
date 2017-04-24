package cn.user0308.scutkicking.Component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import cn.user0308.scutkicking.Collision;
import cn.user0308.scutkicking.building.Building;

/**
 * Created by Yuan Qiang on 2017/4/23.
 */

public class Ball implements Collision {
    private float x;
    private float y;
    private float mDegree;
    private float mSpeed = 50;
    private float mRadius = 30f;

    public float getDegree() {
        return mDegree;
    }



    public Ball(float x, float y, float degree) {
        this.x = x;
        this.y = y;
        mDegree = degree;
    }

    public void run(){
        x += (mSpeed*Math.cos(Math.toRadians(mDegree)));
        y += (mSpeed*Math.sin(Math.toRadians(mDegree)));
    }

    public void collision(){}

    public void bounce(Collision collision){
        if(collision instanceof Building)
            mDegree = 2 * collision.getDegree() - mDegree;
        else
            mDegree = collision.getDegree();
    }

    public void onDraw(Canvas canvas, Paint paint){
        //canvas.drawColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, mRadius ,paint);
    }
}
