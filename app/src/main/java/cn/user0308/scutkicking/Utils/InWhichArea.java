package cn.user0308.scutkicking.Utils;
import android.util.Log;
/**
 * Created by user0308 on 5/5/17.
 */


public class InWhichArea {
    ///////////////////////////////////////////////////
    public static boolean isInA1(int x,int y,int screen_width,int screen_height){
        return (x<screen_width/2)&&(y<screen_height/2);
    }

    public static boolean isInA2(int x,int y,int screen_width,int screen_height,int map_w,int map_h){
        return (map_w-x<screen_width/2)&&(y<screen_height/2);
    }

    public static boolean isInA3(int x,int y,int screen_width,int screen_height,int map_w,int map_h){
        return (x<screen_width/2)&&(map_h-y<screen_height/2);
    }

    public static boolean isInA4(int x,int y,int screen_width,int screen_height,int map_w,int map_h){
        return (map_w-x<screen_width/2)&&(map_h-y<screen_height/2);
    }

    //---------------------------------------------------------------

    public static boolean isInB1(int x,int y,int screen_width,int screen_height){

        return (x>screen_width/2)&&(y<screen_height/2);
    }

    public static boolean isInB2(int x,int y,int screen_width,int screen_height,int map_w,int map_h){

        return (map_w-x>screen_width/2)&&(y<screen_height/2);
    }

    public static boolean isInB3(int x,int y,int screen_width,int screen_height,int map_w,int map_h){

        return (x>screen_width/2)&&(map_h-y<screen_height/2);
    }

    public static boolean isInB4(int x,int y,int screen_width,int screen_height,int map_w,int map_h){

        return (map_w-x>screen_width/2)&&(map_h-y<screen_height/2);
    }

    //------------------------------------------------------------------

    public static boolean isInC1(int x,int y,int screen_width,int screen_height){

        return (x<screen_width/2)&&(y>screen_height/2);
    }

    public static boolean isInC2(int x,int y,int screen_width,int screen_height,int map_w,int map_h){

        return (map_w-x<screen_width/2)&&(y>screen_height/2);
    }

    public static boolean isInC3(int x,int y,int screen_width,int screen_height,int map_w,int map_h){

        return (x<screen_width/2)&&(map_h-y>screen_height/2);
    }

    public static boolean isInC4(int x,int y,int screen_width,int screen_height,int map_w,int map_h){

        return (map_w-x<screen_width/2)&&(map_h-y>screen_height/2);
    }

    //--------------------判断是否在D区域,排除了在ABC区域后一般不用判断--------------------

    public static boolean isInD1(int x,int y,int screen_width,int screen_height){

        return (x>screen_width/2)&&(y>screen_height/2);
    }

    public static boolean isInD2(int x,int y,int screen_width,int screen_height,int map_w,int map_h){

        return (map_w-x>screen_width/2)&&(y>screen_height/2);
    }

    public static boolean isInD3(int x,int y,int screen_width,int screen_height,int map_w,int map_h){

        return (x>screen_width/2)&&(map_h-y>screen_height/2);
    }

    public static boolean isInD4(int x,int y,int screen_width,int screen_height,int map_w,int map_h){

        return (map_w-x>screen_width/2)&&(map_h-y>screen_height/2);
    }

    ////////////////////////////////////////////////////
}
