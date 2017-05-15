package cn.user0308.scutkicking.Utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import cn.user0308.scutkicking.activity.MainActivity;

/**
 * Created by user0308 on 5/11/17.
 */

public class ImageConvertUtil {

    public static Bitmap Zoom(Bitmap bitmap,float x,float y){
        int bgWidth =  bitmap.getWidth();
        int bgHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float sx = x / bgWidth;
        float sy = y / bgHeight;
        matrix.postScale(sx,sy);
        Bitmap tmp = Bitmap.createBitmap(bitmap,0,0,bgWidth,
                bgHeight,matrix,false);

        //可能发生null
        return tmp;
    }
}
