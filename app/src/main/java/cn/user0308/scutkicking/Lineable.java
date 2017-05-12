package cn.user0308.scutkicking;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Component.Hero;
import cn.user0308.scutkicking.Component.Line;

/**
 * Created by Yuan Qiang on 2017/5/1.
 */

public abstract class Lineable implements Collideable {
    protected List<Line> lines ;
    protected Boolean tag = true;//如果为真说明该对象上一桢被碰撞过
    protected int lastLine;//上一次被碰撞的线段

    @Override
    public boolean collide(Collideable object) {
        if(tag == true && object instanceof Hero) {
            if (object.collide(lines.get(lastLine))) {
                tag = true;
                return true;
            } else {
                tag = false;
                return false;
            }
        }
        for(int i=lastLine;i<lastLine+lines.size();i++){
            int current = i%lines.size();
            if(object.collide(lines.get(current))){
                if(object instanceof Hero){
                    tag = true;
                    lastLine = current;
                }
                return true;
            }
        }
        return false;
    }

    abstract public void onDraw(Canvas canvas, Paint paint);

    abstract public void initLines();
}
