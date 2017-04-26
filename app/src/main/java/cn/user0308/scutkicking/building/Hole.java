package cn.user0308.scutkicking.building;

import android.util.Log;

import cn.user0308.scutkicking.Component.Ball;
import cn.user0308.scutkicking.MainView;
import cn.user0308.scutkicking.Utils.RandomUtil;

/**
 * 边角上的洞
 * Created by Yuan Qiang on 2017/4/23.
 */

public class Hole extends Building implements Attackable {
    private float begin = 0;//发射球的起始角度
    private float end = 0;

    public Hole( float positionX, float positionY) {
        super(positionX, positionY);
    }

    public Hole(float positionX, float positionY, float begin, float end) {
        super(positionX, positionY);
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void attack() {
        Ball ball = new Ball(mPositionX, mPositionY,
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
}
