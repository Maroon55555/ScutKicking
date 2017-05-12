package cn.user0308.scutkicking.building;

import android.graphics.Canvas;
import android.graphics.Paint;

import cn.user0308.scutkicking.Component.Ball.Ball;
import cn.user0308.scutkicking.Component.Ball.BubbleBall;
import cn.user0308.scutkicking.MainView;
import cn.user0308.scutkicking.Utils.RandomUtil;

/**
 * 边角上的洞
 * Created by Yuan Qiang on 2017/4/23.
 */

public class Hole extends Building implements Attackable {
    private float begin = 0;//发射球的起始角度
    private float end = 0;
    private float mRadius = 50;

    public Hole( float positionX, float positionY) {
        super(positionX, positionY);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
       // canvas.drawCircle(mPositionX, mPositionY, mRadius, paint);
    }

    public Hole(float positionX, float positionY, float begin, float end) {
        super(positionX, positionY);
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void attack() {
        float medium = (begin + end)/2;
        Ball ball = new BubbleBall(
                 mPositionX+ (float) ((mRadius+2) *(Math.cos(Math.toRadians(medium))/Math.abs(Math.cos(Math.toRadians(medium))))),
                 mPositionY+ (float) ((mRadius+2) *(Math.sin(Math.toRadians(medium))/Math.abs(Math.sin(Math.toRadians(medium))))),
                RandomUtil.randomNum(begin, end));
        //Log.d("Hole", "随机数："+RandomUtil.randomNum(0,90));
        //Log.d("Hole", "发射球");
        MainView.addBall(ball);
    }

    public float getBegin() {
        return begin;
    }

    public void setBegin(float begin) {
        this.begin = begin;
    }

    public float getEnd() {
        return end;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    @Override
    public void initLines() {

    }
}
