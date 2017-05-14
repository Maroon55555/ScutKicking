package cn.user0308.scutkicking;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Component.Line;

/**
 * Created by Yuan Qiang on 2017/5/1.
 */

public abstract class Lineable implements Collideable {
    protected List<Line> lines = new ArrayList<>();

    @Override
    public boolean collide(Collideable object) {
        for (Line line:lines){
            if (line.collide(object)){
                return true;
            }
        }
        return false;
    }

    abstract public void initLines();
}
