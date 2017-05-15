package cn.user0308.scutkicking.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import cn.user0308.scutkicking.MainView;
import cn.user0308.scutkicking.R;
import cn.user0308.scutkicking.bluetooth.BluetoothMsg;
import cn.user0308.scutkicking.bluetooth.TransportData;

public class MainActivityCopy extends AppCompatActivity {

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

    //public static int sMapWidth;
    //public static int sMapHeight;
    //public static Bitmap bg;

    public static MainView mainView;
    public static TransportData TD;
    public static boolean ViewCreated = false;//标记View是否已经可见（避免蓝牙在View不可见时传数据）
    private LoadingThread loadingThread;//判断连接是否成功的线程
    private Handler loadingHandler;//改动setviewcontent的Handler

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
        //Log.d("MainActivity", "宽：" + sWindowWidthPix + " 高：" + sWindowHeightPix);
        //bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        //sMapWidth = bg.getWidth();
        //sMapHeight = bg.getHeight();

        sContext = this;
        mainView= new MainView(this);
        //setContentView(new MainView(this));
        setContentView(R.layout.activity_loading);

        TD = new TransportData();

        TD.openBluetooth();
        loadingThread=new LoadingThread();
        loadingThread.start();
        loadingHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if(msg.what == 1)  // handler接收到相关的消息后
                {
                    setContentView(mainView); // 显示真正的应用界面
                    ViewCreated=true;
                }
            }
        };
    }

    private class LoadingThread extends Thread {
        @Override
        public void run() {
            Log.d("LoadingTest", "Loading start");
            Log.v("LoadingTest","flag1");
            while(!TD.connected2);
            Log.v("LoadingTest","flag2");
            //setContentView(mainView);

            loadingHandler.sendEmptyMessage(1);
        }
    }

    @Override
    protected void onDestroy() {
        Log.v("MainActivity", "onDestroy");
        if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT)TD.shutdownClient();
        else TD.shutdownServer();
        //why twice
        TD.shutdownServer();
        TD.shutdownClient();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity", "onStop");
        if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT)TD.shutdownClient();
        else TD.shutdownServer();
        //why twice
        TD.shutdownServer();
        TD.shutdownClient();
        super.onStop();
    }
}
