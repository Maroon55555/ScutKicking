package cn.user0308.scutkicking.Component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.List;

import cn.user0308.scutkicking.Collideable;
import cn.user0308.scutkicking.Lineable;

/**
 * Created by Yuan Qiang on 2017/5/2.
 */

public class Rect extends Lineable {
    @Override
    public void initLines() {

    }

    public void onDraw(Canvas canvas, Paint paint){
        for (Line line:lines){
            canvas.drawLine(line.getX1(), line.getY1(),line.getX2(),line.getY2(),paint);
        }
    }

    public Rect(List<Line> lines) {
        this.lines = lines;
    }
}
