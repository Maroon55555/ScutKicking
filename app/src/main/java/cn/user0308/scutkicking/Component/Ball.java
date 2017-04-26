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
    //小球球心位置x,y
    private float x;
    private float y;
    //小球运动角度,以角度为单位
    private float mAngle;
    private float mSpeed = 50;
    //小球半径
    private float mRadius = 30f;

    public float getAngle() {
        return mAngle;
    }



    public Ball(float x, float y, float degree) {
        this.x = x;
        this.y = y;
        mAngle = degree;
    }

    public void calculatePoint(){
        x += (mSpeed*Math.cos(Math.toRadians(mAngle)));
        y += (mSpeed*Math.sin(Math.toRadians(mAngle)));
    }

    public void collision(){}

    //碰撞到Building改变自身角度
    public void changeAngle(Collision collision){
        if(collision instanceof Building)
            mAngle = 2 * collision.getAngle() - mAngle;
        else
            mAngle = collision.getAngle();
    }

    public void onDraw(Canvas canvas, Paint paint){
        //设置抗锯齿属性,白色
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, mRadius ,paint);
    }
}
