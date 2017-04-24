package cn.user0308.scutkicking.Utils;

import android.graphics.Point;

/**
 * Created by User0308 on 2017/4/23.
 */

public class RuddyMathUtils {

    //计算两点(x1,y1),(x2,y2)之间距离
    public static int getLength(int x1,int y1,float x2,float y2){
        return (int)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    //计算定圆圆心与动圆圆心连线交于定圆弧长某点坐标
    public static Point getBorderPoint(Point a, Point b, int r){
        float radian = getRadian(a, b);
        return new Point(a.x + (int)(r * Math.cos(radian)), a.y - (int)(r * Math.sin(radian)));
    }

    //计算两点连续与X轴形成的夹角弧度值
    public static float getRadian(Point a,Point b){
        float lenA = b.x-a.x;
        float lenB = b.y-a.y;
        float lenC = (float)Math.sqrt(lenA*lenA+lenB*lenB);
        float ang = (float)Math.acos(lenA/lenC);
        ang = ang * (b.y > a.y ? -1 : 1);
        return ang;
    }
}
