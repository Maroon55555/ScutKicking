package cn.user0308.scutkicking.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import cn.user0308.scutkicking.MainView;
import cn.user0308.scutkicking.R;
import cn.user0308.scutkicking.bluetooth.BluetoothMsg;
import cn.user0308.scutkicking.bluetooth.TransportData2;

public class MainActivityDrop extends AppCompatActivity {

    public static int XMOD=2020;
    public static int YMOD=2020;

    public static int transferX(int x){
        double tem=(double)x/XMOD*sWindowWidthPix;
        return (int)tem;
    }

    public static int transferY(int y){
        double tem=(double)y/YMOD*sWindowHeightPix;
        return (int)tem;
    }

    public static int toUnit(int l){
        double tem=(double)l*XMOD/sWindowWidthPix;
        return (int )tem;
    }


    public static Context sContext;
    public static int sWindowHeightPix;//设备分辨率
    public static int sWindowWidthPix;
    public static MainView mainView;
    public static TransportData2 TD2;
    public static boolean ViewCreated = false;//标记View是否已经可见（避免蓝牙在View不可见时传数据）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        sWindowHeightPix = displayMetrics.heightPixels;
        sWindowWidthPix = displayMetrics.widthPixels;
        sContext = this;
        mainView= new MainView(this);
        //setContentView(new MainView(this));
        setContentView(R.layout.activity_loading);

        //初始化transportData
        TD2 = new TransportData2();
        TD2.openBluetooth();
//        while (!TD2.isConnected)
//            ;//如果还没连接上,则死循环
        setContentView(mainView);//连接上了,改变view
        ViewCreated = true;
        //初始化球,人信息
        //mainview.init();
        //传输初始化信息
        transportData();
    }

    private void transportData(){
        if(BluetoothMsg.serviceOrCilent==BluetoothMsg.ServerOrCilent.SERVICE){
            //if has message to send, send it, if has message to read, read it
            TD2.serverSendData2Client();
            TD2.serverGetDataFromClient();
        }else if(BluetoothMsg.serviceOrCilent==BluetoothMsg.ServerOrCilent.CILENT){
            //if has message to send, send it, if has message to read, read it
            TD2.clientGetDataFromServer();
            TD2.clientSendData2Server();
        }
    }

    @Override
    protected void onDestroy() {
        Log.v("MainActivity", "onDestroy");
        if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT)
            TD2.shutdownClient();
        else
            TD2.shutdownServer();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity", "onStop");
        if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT)
            TD2.shutdownClient();
        else
            TD2.shutdownServer();
        super.onStop();
    }
}
