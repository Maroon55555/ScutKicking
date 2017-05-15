package cn.user0308.scutkicking.Network;

/**
 * Created by user0308 on 5/15/17.
 */

public class Status {
    public enum ServerOrCilent{
        NONE,
        SERVICE,
        CILENT
    };
    //连接方式
    public static ServerOrCilent serviceOrCilent = ServerOrCilent.NONE;
    //连接地址
    public static String Address = null,lastAddress=null;
    //通信线程是否开启
    public static boolean isOpen = false;
}
