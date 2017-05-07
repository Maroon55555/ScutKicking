package cn.user0308.scutkicking.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

import cn.user0308.scutkicking.MainActivity;
import cn.user0308.scutkicking.R;

/**用于产生指定范围内随机数
 * Created by Yuan Qiang on 2017/4/23.
 */

public class RandomUtil {
    public static float randomNum(float begin, float end){
        return (float) (Math.random() * (end - begin ) + begin);
    }

    public static Point createRandomPoint(){
        //Bitmap bg = BitmapFactory.decodeResource(MainActivity.sContext.getResources(), R.drawable.bg);
        //int w = bg.getWidth();
        //int h = bg.getHeight();
        int w = MainActivity.sWindowWidthPix;
        int h = MainActivity.sWindowHeightPix;
        Log.d("CreateRandomPoint","w h is " + w + " " + h);
        int randomX =(int)(Math.random()*w);
        int randomY =(int)(Math.random()*h);
        Log.d("CreateRandomPoint","random X,Y is " + randomX + " " + randomY);
        return new Point(randomX,randomY);
    }

    public static boolean isPointInRect(Point point, Rect rect){
        return rect.contains(point.x,point.y);
    }
}
