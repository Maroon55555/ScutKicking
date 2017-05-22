package cn.user0308.scutkicking.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import cn.user0308.scutkicking.MainView;
import cn.user0308.scutkicking.R;
import cn.user0308.scutkicking.bluetooth.BluetoothMsg;
import cn.user0308.scutkicking.bluetooth.TransportData;

public class MainActivity extends AppCompatActivity {

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

    public static void startMainAcitivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        context.startActivity(intent);
    }

    public static final float WIDTH = 1280;//将游戏逻辑限定在1280*720的矩形中
    public static final float HEIGHT = 720;
    public static float widthScale;//用于将逻辑矩形画在实际手机屏幕上，即将逻辑矩形按照这种
    public static float heightScale;//比例缩放

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
        widthScale = sWindowWidthPix/WIDTH;
        heightScale = sWindowHeightPix/HEIGHT;
        //Log.d("MainActivity", "宽：" + sWindowWidthPix + " 高：" + sWindowHeightPix);
        //bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        //sMapWidth = bg.getWidth();
        //sMapHeight = bg.getHeight();
        sContext = this;
        mainView= new MainView(this);
        //setContentView(new MainView(this));
        setContentView(mainView);

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//禁用返回键
        if (keyCode == event.getKeyCode())
            return true;
        return false;
    }
}
