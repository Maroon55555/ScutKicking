package cn.user0308.scutkicking.building;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Collideable;
import cn.user0308.scutkicking.Component.Line;
import cn.user0308.scutkicking.Lineable;

/**
 * Created by Yuan Qiang on 2017/4/23.
 */

abstract public class Building extends Lineable {
    protected float mPositionX;
    protected float mPositionY;

    public Building(float positionX, float positionY) {
        super();
        this.mPositionX = positionX;
        this.mPositionY = positionY;
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
