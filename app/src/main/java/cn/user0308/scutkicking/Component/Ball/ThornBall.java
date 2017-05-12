package cn.user0308.scutkicking.Component.Ball;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import cn.user0308.scutkicking.R;
import cn.user0308.scutkicking.Utils.ImageConvertUtil;
import cn.user0308.scutkicking.activity.MainActivity;

/**
 * Created by Yuan Qiang on 2017/5/10.
 */

public class ThornBall extends Ball{
    private Bitmap image = null;
    private float width;
    private float height;

    public ThornBall(float x, float y, float degree) {
        super(x, y, degree);
        width =  getRadius() * 2 + 10;
        height = getRadius() * 2 + 10;
        image = BitmapFactory.decodeResource(MainActivity.sContext.getResources(), R.drawable.ciqiu);
        image = ImageConvertUtil.Zoom(image, width, height);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(image, x - width/2, y - height/2, paint);
    }
}
