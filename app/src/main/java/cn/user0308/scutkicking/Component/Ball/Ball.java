package cn.user0308.scutkicking.Component.Ball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cn.user0308.scutkicking.Collideable;
import cn.user0308.scutkicking.Component.Line;
import cn.user0308.scutkicking.Lineable;
import cn.user0308.scutkicking.activity.MainActivity;

/**
 * Created by Yuan Qiang on 2017/4/23.
 */

public class Ball implements Collideable {
    //小球球心位置x,y
    private float x;
    private float y;
    //小球在地图中的坐标
    //private int mapX,mapY;
    //小球上一帧的位置
    private float px;
    private float py;
    //小球运动角度,以角度为单位
    private float mAngle;
    private float mSpeed = 15;
    //小球半径
    private float mRadius = (float) (0.04* MainActivity.sWindowHeightPix);


    public float getAngle() {
        return mAngle;
    }

    public Ball(float x, float y, float degree) {
        this.x = x;
        this.y = y;
        mAngle = degree;
    }

    public void calculatePoint(){
        px = x;
        py = y;
        x += offsetX(mSpeed);
        y += offsetY(mSpeed);
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

    @Override
    public boolean collide(Collideable object) {
        if(object instanceof Ball){
            return checkBallCollide((Ball) object);
        }else if(object instanceof Line){
            return  checkLineCollide((Line) object);
//            return checkLineCollide((Line) object);
        }else if(object instanceof Lineable){
            return object.collide(this);
        }else{
            return false;
        }
    }
    /**
     * 检测小球与线段的碰撞
     *@author Yuan Qiang
     *created at 2017/5/1 14:15
     *@param 
     */
    public boolean checkLineCollide(Line line){
        return line.checkBallCollide(this);
    }
    /**
     * 检测小球与小球的碰撞
     *@author Yuan Qiang
     *created at 2017/5/1 14:15
     *@param 
     */
    public boolean checkBallCollide(Ball ball){
        float distance = (y - ball.getY()) * (y - ball.getY())
                    + (x - ball.getX()) * (x - ball.getX());
        if(distance < 4 * mRadius * mRadius){
            float temp = mAngle;
            mAngle = ball.getAngle();
            ball.setAngle(temp);
            back();
            return true;
        }
        return false;
    }
    /**
     * 回到上一帧的位置，目的在于防止一个bug
     *@author Yuan Qiang
     *created at 2017/5/1 14:16
     *@param 
     */
    public void back(){
        x = px;
        y = py;
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
