package cn.user0308.scutkicking.Utils;

import cn.user0308.scutkicking.Collision;
import cn.user0308.scutkicking.MainActivity;

/**
 * Created by Yuan Qiang on 2017/4/27.
 * 我爱数学 XD
 */

public class LineSegmentUtil implements Collision{
    private float mAngle;  //线段的角度，应该在-90度到90度之间，可以用角度来简单的改变碰撞小球的方向

    private float x1, y1;  //线段左边点的坐标
    private float x2, y2;

    private float A;//直线方程 Ax + By + C = 0，该直线方程用来检查两线段是否相交
    private float B;
    private float C;

    private float intersectionX;//两线段的交点
    private float intersectionY;

    public LineSegmentUtil(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        A = y2 - y1;
        B = x1 - x2;
        C = y1 * x2 - x1 *y2;

        if( B != 0){
            mAngle = (float) Math.toDegrees(Math.atan(A / (-B)));
        }else {
            mAngle = 90;
        }
    }

    public float getAngle() {
        return mAngle;
    }

    /**
     *  用来确定两直线是否相交
     *  @author Yuan Qiang
     *  created at 2017/4/27 16:49
     *  @param
     */

    public boolean checkIntersect(LineSegmentUtil line){//判断两线段是否相交
        if( this.checkOnLine(line.getX1(), line.getY1()) //该判断条件指line的两端点在本线两端
                * this.checkOnLine(line.getX2(), line.getY2()) < 0)
        {//但是由于这是两根线段因此需要判断这根线段的两端点也应在line的两端
            if( line.checkOnLine(x1, y1) * line.checkOnLine(x2, y2) < 0){//两线段相交
                float A1 = line.getA();
                float B1= line.getB();
                float C1 = line.getC();
                //计算两线段的交点，用于设置球的位置
                intersectionX = (C1 * B - C * B1)/(A * B1 - A1 * B);
                intersectionY = (C1 * A - C * A1)/(A1 * B - A * B1);
                return true;
            }
        }
        return false;
    }

    /**
     *  用于检测点是否在线段上，返回0即为是
     *  @author Yuan Qiang
     *  created at 2017/4/27 19:57
     *  @param
     */

    public float checkOnLine(float x, float y){//用来检查点是否在线上，返回0代表是
        return A * x + B * y + C ;
    }

    public float calDistance(float x, float y){
        //点到两线段端点的距离
        float a = (float) Math.sqrt( (y - y1) * (y - y1) + (x - x1) * (x - x1));
        float b = (float) Math.sqrt( (y - y2) * (y - y2) + (x - x2) * (x - x2));
        //点到直线最短距离
        float c = (float) (Math.abs(A * x+ B * y + C) / Math.sqrt(A * A + B * B));
        //点到线段最短距离是以上三者最短的一个
        return Math.min(a, Math.min(b, c));
    }

    public float getX1() {
        return x1;
    }


    public float getY1() {
        return y1;
    }


    public float getX2() {
        return x2;
    }


    public float getY2() {
        return y2;
    }

    public float getA() {
        return A;
    }

    public float getB() {
        return B;
    }

    public float getC() {
        return C;
    }

    public float getIntersectionX() {
        return intersectionX;
    }

    public float getIntersectionY() {
        return intersectionY;
    }
}
