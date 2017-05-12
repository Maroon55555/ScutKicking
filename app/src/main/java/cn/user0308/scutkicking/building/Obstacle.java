package cn.user0308.scutkicking.building;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import cn.user0308.scutkicking.Component.Line;
import cn.user0308.scutkicking.Utils.ImageConvertUtil;
import cn.user0308.scutkicking.activity.MainActivity;

/**
 * Created by Yuan Qiang on 2017/5/12.
 */

public class Obstacle extends Building {
    private Bitmap bitmap = null;
    private float width;
    private float height;

    public Obstacle(float positionX, float positionY, int resourceId, float width, float height) {
        super(positionX, positionY);
        this.width = width;
        this.height =height;
        bitmap = BitmapFactory.decodeResource(MainActivity.sContext.getResources(), resourceId);
        bitmap = ImageConvertUtil.Zoom(bitmap, width,height);
        initLines();
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, mPositionX, mPositionY, paint);
    }

    @Override
    public void initLines() {
        lines = new ArrayList<>();
        Line lineTop = new Line(mPositionX,mPositionY, mPositionX+width,mPositionY);//图片上方线段
        Line lineLeft = new Line(mPositionX,mPositionY,mPositionX,mPositionY+height);//左边
        Line lineBottom = new Line(mPositionX,mPositionY+height,mPositionX+width,mPositionY+height);//下方
        Line lineRight = new Line(mPositionX+width,mPositionY,mPositionX+width,mPositionY+height);//右方
        lines.add(lineTop);
        lines.add(lineLeft);
        lines.add(lineRight);
        lines.add(lineBottom);
    }
}
