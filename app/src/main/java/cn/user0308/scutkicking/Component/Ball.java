package cn.user0308.scutkicking.Component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import cn.user0308.scutkicking.Collision;
import cn.user0308.scutkicking.MainActivity;
import cn.user0308.scutkicking.Utils.LineSegmentUtil;
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
        x += offsetX(mSpeed);
        y += offsetY(mSpeed);
    }

    /**
     *  小球反弹
     *  @author Yuan Qiang
     *  created at 2017/4/27 18:07
     *  @param collision 可碰撞对象，小球根据此来决定要发生的动作
     */

    public void rebound(Collision collision){
        if(collision instanceof LineSegmentUtil){
            x = (((LineSegmentUtil) collision).getIntersectionX() - offsetX(mRadius));
            y = (((LineSegmentUtil) collision).getIntersectionY() - offsetY(mRadius));
            mAngle = 2 * ((LineSegmentUtil) collision).getAngle() - mAngle;
        }
    }

    public float offsetX(float x){//观察到很多地方都需要用到这样的语句，因此特意提出来。。。
        return (float) (x * Math.cos(Math.toRadians(mAngle)));
    }
    public float offsetY(float y){
        return (float) (y * Math.sin(Math.toRadians(mAngle)));
    }

    public void onDraw(Canvas canvas, Paint paint){
        //设置抗锯齿属性,白色
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, mRadius ,paint);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public void setSpeed(float speed) {
        mSpeed = speed;
    }
}
