//package cn.user0308.scutkicking.bluetooth;
///**
// * Created by samsung on 2017/5/5.
// */
//
//import cn.user0308.scutkicking.bluetooth.*;
////import cn.user0308.scutkicking.MainView;
//import cn.user0308.scutkicking.Component.Ball;
//import cn.user0308.scutkicking.MainView;
//import cn.user0308.scutkicking.Utils.*;
//import cn.user0308.scutkicking.R;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.app.AppCompatActivity;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
//
//import static cn.user0308.scutkicking.MainView.mBallList;
//
//public class TransportData {
//
//    /* 一些常量，代表服务器的名称 */
//    public static final String PROTOCOL_SCHEME_RFCOMM = "btspp";
//    static int sendcnt = 0;
//    private ListView mListView;
//    private Button disconnectButton;
//    private ArrayAdapter<String> mAdapter;
//    private MainView myview;
//    private List<String> msgList = new ArrayList<String>();
//    private BluetoothServerSocket mserverSocket = null;
//    private BluetoothServerSocket oserverSocket = null;
//    private BluetoothServerSocket stocSocket = null;
//    private ServerThread startServerThread = null;
//    private clientThread clientConnectThread = null;
//    private BluetoothSocket socket = null;
//    private BluetoothSocket osocket = null;
//    private BluetoothSocket cgetsocket = null;
//    private BluetoothDevice device = null;
//    private readThread mreadThread = null;
//    ;
//    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//    private Handler LinkDetectedHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            //Toast.makeText(mContext, (String)msg.obj, Toast.LENGTH_SHORT).show();
//            if (msg.what == 1) {
//                msgList.add((String) msg.obj);
//            } else {
//                msgList.add((String) msg.obj);
//            }
//            mAdapter.notifyDataSetChanged();
//            mListView.setSelection(msgList.size() - 1);
//        }
//    };
//
//    boolean isDouble(String str) {
//        try {
//            Double.parseDouble(str);
//            return true;
//        } catch (NumberFormatException ex) {
//        }
//        return false;
//    }
//
//
//    /*
//    private void init() {
//
//        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, msgList);
//        mListView = (ListView) findViewById(R.id.list);
//        mListView.setAdapter(mAdapter);
//        mListView.setFastScrollEnabled(true);
//
//        disconnectButton = (Button) findViewById(R.id.btn_disconnect);
//        disconnectButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT) {
//                    shutdownClient();
//                } else if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.SERVICE) {
//                    shutdownServer();
//                }
//                BluetoothMsg.isOpen = false;
//                BluetoothMsg.serviceOrCilent = BluetoothMsg.ServerOrCilent.NONE;
//                Toast.makeText(mContext, "已断开连接！", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    */
//    /*
//    @Override
//    protected void onResume() {
//
//        if (BluetoothMsg.isOpen) {
//            Toast.makeText(mContext, "连接已经打开，可以通信。如果要再建立连接，请先断开！", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT) {
//            String address = BluetoothMsg.BlueToothAddress;
//            if (!address.equals("null")) {
//                device = mBluetoothAdapter.getRemoteDevice(address);
//                clientConnectThread = new clientThread();
//                clientConnectThread.start();
//                BluetoothMsg.isOpen = true;
//            } else {
//                Toast.makeText(mContext, "address is null !", Toast.LENGTH_SHORT).show();
//            }
//        } else if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.SERVICE) {
//            startServerThread = new ServerThread();
//            startServerThread.start();
//            BluetoothMsg.isOpen = true;
//        }
//        super.onResume();
//    }
//    */
//    ;
//
//    /* 停止服务器 */
//    private void shutdownServer() {
//        new Thread() {
//            @Override
//            public void run() {
//                if (startServerThread != null) {
//                    startServerThread.interrupt();
//                    startServerThread = null;
//                }
//                if (mreadThread != null) {
//                    mreadThread.interrupt();
//                    mreadThread = null;
//                }
//                try {
//                    if (socket != null) {
//                        socket.close();
//                        socket = null;
//                    }
//                    if (mserverSocket != null) {
//                        mserverSocket.close();/* 关闭服务器 */
//                        mserverSocket = null;
//                    }
//                    if (osocket != null) {
//                        osocket.close();
//                        osocket = null;
//                    }
//                    if (oserverSocket != null) {
//                        oserverSocket.close();/* 关闭服务器 */
//                        oserverSocket = null;
//                    }
//                    if (cgetsocket != null) {
//                        cgetsocket.close();
//                        cgetsocket = null;
//                    }
//                    if (stocSocket != null) {
//                        stocSocket.close();/* 关闭服务器 */
//                        stocSocket = null;
//                    }
//                } catch (IOException e) {
//                    Log.e("server", "mserverSocket.close()", e);
//                }
//            }
//
//            ;
//        }.start();
//    }
//
//    ;
//
//    /* 停止客户端连接 */
//    private void shutdownClient() {
//        new Thread() {
//            @Override
//            public void run() {
//                if (clientConnectThread != null) {
//                    clientConnectThread.interrupt();
//                    clientConnectThread = null;
//                }
//                if (mreadThread != null) {
//                    mreadThread.interrupt();
//                    mreadThread = null;
//                }
//                if (socket != null) {
//                    try {
//                        socket.close();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    socket = null;
//                }
//                if (cgetsocket != null) {
//                    try {
//                        cgetsocket.close();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    cgetsocket = null;
//                }
//                if (osocket != null) {
//                    try {
//                        osocket.close();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    osocket = null;
//                }
//            }
//
//            ;
//        }.start();
//    }
//
//    /*
//    @Override
//    protected void onDestroy() {
//        if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT) {
//            shutdownClient();
//        } else if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.SERVICE) {
//            shutdownServer();
//        }
//        BluetoothMsg.isOpen = false;
//        BluetoothMsg.serviceOrCilent = BluetoothMsg.ServerOrCilent.NONE;
//        super.onDestroy();
//    }
//    */
//    //开启客户端
//    private class clientThread extends Thread {
//        @Override
//        public void run() {
//            try {
//                //创建一个Socket连接：只需要服务器在注册时的UUID号
//                // socket = device.createRfcommSocketToServiceRecord(BluetoothProtocols.OBEX_OBJECT_PUSH_PROTOCOL_UUID);
//                socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
//                osocket = device.createRfcommSocketToServiceRecord(UUID.fromString("C83DA007-3A9F-4249-9A96-18CACE25F84D"));
//                cgetsocket = device.createRfcommSocketToServiceRecord(UUID.fromString("54B32C11-45BD-44A2-87BD-4DA72CB8E3EB"));
//                //连接
//                Message msg2 = new Message();
//                msg2.obj = "请稍候，正在连接服务器:" + BluetoothMsg.BlueToothAddress;
//                msg2.what = 0;
//                LinkDetectedHandler.sendMessage(msg2);
//
//                socket.connect();
//                osocket.connect();
//                cgetsocket.connect();
//
//                Message msg = new Message();
//                msg.obj = "已经连接上服务端！可以发送信息。";
//                msg.what = 0;
//                LinkDetectedHandler.sendMessage(msg);
//
//                //发送数据给服务器，说明可以接收数据
//                try {
//                    OutputStream os = cgetsocket.getOutputStream();
//                    os.write("ok".getBytes());
//                } catch (IOException e) {
//                    // Log.e("connect", "", e);
//                }
//
//
//                //启动接受数据
//                mreadThread = new readThread();
//                mreadThread.start();
//                Log.v("connect", "   readfinish");
//            } catch (IOException e) {
//                Log.e("connect", "", e);
//                Message msg = new Message();
//                msg.obj = "连接服务端异常！断开连接重新试一试。";
//                msg.what = 0;
//                LinkDetectedHandler.sendMessage(msg);
//            }
//        }
//    }
//
//    //开启服务器
//    private class ServerThread extends Thread {
//        @Override
//        public void run() {
//
//            try {
//                    /* 创建一个蓝牙服务器
//                     * 参数分别：服务器名称、UUID   */
//                mserverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM,
//                        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
//                oserverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM,
//                        UUID.fromString("C83DA007-3A9F-4249-9A96-18CACE25F84D"));
//                stocSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM,
//                        UUID.fromString("54B32C11-45BD-44A2-87BD-4DA72CB8E3EB"));
//                //  00001101-0000-1000-8000-00805F9B34FB   C83DA007-3A9F-4249-9A96-18CACE25F84D 54B32C11-45BD-44A2-87BD-4DA72CB8E3EB
//                Log.d("server", "wait cilent connect...");
//
//                Message msg = new Message();
//                msg.obj = "请稍候，正在等待客户端的连接...";
//                msg.what = 0;
//                LinkDetectedHandler.sendMessage(msg);
//
//                    /* 接受客户端的连接请求 */
//                socket = mserverSocket.accept();
//                osocket = oserverSocket.accept();
//                cgetsocket = stocSocket.accept();
//                Log.d("server", "accept success !");
//
//                Message msg2 = new Message();
//                String info = "客户端已经连接上！可以发送信息。";
//                msg2.obj = info;
//                msg.what = 0;
//                LinkDetectedHandler.sendMessage(msg2);
//                //启动接受数据
//                mreadThread = new readThread();
//                mreadThread.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //读取数据
//    private class readThread extends Thread {
//        @Override
//        public void run() {
//
//            byte[] buffer = new byte[1024];
//            int bytes;
//            InputStream mmInStream = null;
//
//            try {
//                if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT)
//                    mmInStream = osocket.getInputStream();
//                else mmInStream = socket.getInputStream();
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            while (true) {
//                try {
//                    Thread.sleep(50);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                try {
//
//                    //服务器发送ball数据
//                    //socket没数据时才写入
//                    if (cgetsocket.getInputStream().available() != 0 && BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.SERVICE) {
//                        sendcnt++;
//                        Log.v("MainView", "send  " + sendcnt + "  avail: " + cgetsocket.getInputStream().available());
//                        String balltext = "";
//                        //int ballnum = mBallList.size();
//                        //balltext+=ballnum;
//                        for (Ball ball :
//                                mBallList) {
//                            balltext += "," + ball.getX() + "," + ball.getY() + "," + ball.getAngle();
//                        }
//                        int bytecnt = balltext.getBytes().length;
//                        balltext = bytecnt + "" + balltext;
//                        try {
//                            OutputStream os;
//
//                            os = cgetsocket.getOutputStream();
//                            //os.write(bytecnt);
//                            os.write(balltext.getBytes());
//                            Log.v("MainView", " write and avai  " + cgetsocket.getInputStream().available());
//                            //Log.v("MainView","output avai  " + cgetsocket.getOutputStream())
//                            Log.v("output", "string   " + balltext);
//                            String tb = "";
//                            for (int i = 0; i < balltext.getBytes().length; i++)
//                                tb += balltext.getBytes()[i] + " ";
//                            Log.v("output", "bytes   " + tb);
//
//                            //cgetsocket.getInputStream().reset();
//                            InputStream myinput = cgetsocket.getInputStream();
//                            byte[] myt = new byte[5000];
//                            int k = myinput.read(myt);
//                            Log.v("MainView", "read     " + k);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    //客户端接收ball数据
//                    if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT) {
//                        InputStream stocStream = null;
//                        byte[] ballbuffer = new byte[409600];
//
//                        try {
//                            stocStream = cgetsocket.getInputStream();
//                        } catch (IOException e1) {
//                            // TODO Auto-generated catch block
//                            e1.printStackTrace();
//                        }
//
//
//                        try {
//                            //大于4个字节+2个字节才能读到第一个int表示字节数
//                            if (stocStream.available() > 30) {
//
//                                byte[] t0 = new byte[30];
//                                stocStream.read(t0);
//
//                                String tt = new String(t0);
//                                Log.v("MainView", "   " + tt);
//                                int ff = tt.indexOf(",");
//                                String t2 = tt.substring(0, ff);
//                                Log.v("MainView", "t2   " + t2);
//                                bytes = Integer.parseInt(t2);
//                                Log.v("MainView", "bytes   " + bytes);
//                                String t3 = tt.substring(ff + 1, tt.length());
//                                Log.v("MainView", "t3   " + t3);
//                                //bytes += t2.length();
//                                //bytes = new DataInputStream(stocStream).readInt();
//                                byte[] buf_data = new byte[bytes];
//                                byte[] t4 = t3.getBytes();
//
//                                // bytes-=t4.length;
//                                for (int i = 0; i < t4.length && i < bytes; i++)
//                                    buf_data[i] = t4[i];
//                                int readCount = t4.length+1; // 已经成功读取的字节的个数
//                                Log.v("MainView", " no wrong  "+readCount+"  "+bytes );
//                                while (readCount < bytes) {
//                                    readCount += stocStream.read(ballbuffer, readCount, bytes - readCount);
//                                    Log.v("MainView","readcnt:  "+readCount+"   needs  "+bytes);
//                                    //if(readCount==bytes)break;
//                                }
//                                Log.v("MainView", "read ok   "+ballbuffer.length);
//                                String tr = "";
//                                for (int i = 0; i < bytes; i++)
//                                    tr += ballbuffer[i] + " ";
//                                Log.v("MainView", "recive   " + tr);
//                                Log.v("MainView", "rds ok   ");
//                                for (int i = 0; i < bytes; i++) {
//                                    buf_data[i] = ballbuffer[i];
//                                }
//                                String s = new String(buf_data);
//                                String[] z = s.split(",");
//                                int len = z.length / 3, pos = 0 ;
//                                mBallList.clear();
//                                for (int i = 0; i < len; i++) {
//                                    if (pos + 2 < z.length && isDouble(z[pos]) && isDouble(z[pos + 1]) && isDouble(z[pos + 2])) {
//                                        Ball tem = new Ball(Float.parseFloat(z[pos]), Float.parseFloat(z[pos + 1]), Float.parseFloat(z[pos + 2]),true);
//                                        mBallList.add(tem);
//                                        pos += 3;
//                                    } else pos++;
//                                }
//                                //发送数据给服务器，说明可以接收数据
//                                try{
//                                    OutputStream os=cgetsocket.getOutputStream();
//                                    os.write("ok".getBytes());
//                                }catch (IOException e){
//                                    // Log.e("connect", "", e);
//                                }
//                            }
//                        } catch (IOException e1) {
//                            // TODO Auto-generated catch block
//                            Log.v("MainView","  wrong");e1.printStackTrace();
//
//                        }
//
//
//                    }
//
//
//                    //写入角色数据
//                    String msgText;
//                    msgText = myview.mHero.getmAngle() + "," + myview.mHero.getmSpeed();
//                    try {
//                        OutputStream os;
//                        if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT)
//                            os = socket.getOutputStream();
//                        else os = osocket.getOutputStream();
//                        os.write(msgText.getBytes());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    // Read from the InputStream
//                    if ((bytes = mmInStream.read(buffer)) > 0) {
//                        byte[] buf_data = new byte[bytes];
//                        for (int i = 0; i < bytes; i++) {
//                            buf_data[i] = buffer[i];
//                        }
//                        String s = new String(buf_data);
//                        String[] z = s.split(",");
//                        //if(BluetoothMsg.serviceOrCilent==BluetoothMsg.ServerOrCilent.CILENT)
//                        if (isDouble(z[0]))
//                            myview.oHero.setAngle(Double.parseDouble(z[0]));
//                        if (isDouble(z[1]))
//                            myview.oHero.setmSpeed(Double.parseDouble(z[1]));
//
//                    }
//                } catch (IOException e) {
//                    try {
//                        mmInStream.close();
//                    } catch (IOException e1) {
//                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//    }
//
//
//}