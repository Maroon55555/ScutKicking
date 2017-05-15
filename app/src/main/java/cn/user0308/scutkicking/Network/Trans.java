package cn.user0308.scutkicking.Network;

import android.util.Log;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by user0308 on 5/15/17.
 */

public class Trans {

    ServerSocket serverSocket = null;
    Socket socket = null;
    public  Trans(){

    }

    public void connect(){
        if(Status.serviceOrCilent==Status.serviceOrCilent.SERVICE){
            try {
                serverSocket = new ServerSocket(13579);//只用于连接,不用于传输数据
                socket = serverSocket.accept();//会一直等待
            }catch (java.io.IOException e){
                Log.d("","bind error ");
            }

        }else if(Status.serviceOrCilent==Status.ServerOrCilent.CILENT){

        }
    }
}
