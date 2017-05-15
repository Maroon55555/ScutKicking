package cn.user0308.scutkicking.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import cn.user0308.scutkicking.activity.MainActivity;

/**
 * Created by user0308 on 5/15/17.
 */

public class TransportData2 {

    //常量
    public static final String PROTOCOL_SCHEME_RFCOMM = "btspp";

    //蓝牙设备
    private BluetoothAdapter localAdapter = null;
    private BluetoothDevice remoteDevice = null;

    //负责连接到服务器的socket
    private BluetoothSocket clientTransportSocket = null;
    //负责监听是否有新的socket接入,有则accept,不负责传输数据
    private BluetoothServerSocket serverSocket = null;
    //复制服务器传输数据
    private BluetoothSocket serverTransportSocket = null;

    //读写数据
    OutputStream os = null;
    InputStream is = null;

    //判断连接状态
    public boolean isConnected = false;

    public TransportData2(){
        localAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //open the bluetooth
    public void openBluetooth(){
        if (BluetoothMsg.isOpen) {
            //Toast.makeText(mContext, "连接已经打开，可以通信。如果要再建立连接，请先断开！", Toast.LENGTH_SHORT).show();
            Log.d("TransportData","连接已经打开，可以通信。如果要再建立连接，请先断开！");
            return;
        }
        if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT) {
            String address = BluetoothMsg.BlueToothAddress;
            if (!address.equals("null")) {
                remoteDevice = localAdapter.getRemoteDevice(address);
                clientConnected2Server();
                BluetoothMsg.isOpen = true;
            } else {
                //Toast.makeText(mContext, "address is null !", Toast.LENGTH_SHORT).show();
                Log.d("TransportData","address is null");
            }
        } else if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.SERVICE) {
            serverAccept2Client();
            BluetoothMsg.isOpen = true;
        }
    }

    //if is client, connect to the server
    private void clientConnected2Server(){
        try {
            //创建一个Socket连接：只需要服务器在注册时的UUID号
            clientTransportSocket = remoteDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            clientTransportSocket.connect();
            //connect 时阻塞的,运行到下面这句说明已经connect上了
            isConnected=true;//通
            //发送数据给服务器，说明可以接收数据
            try {
                os = clientTransportSocket.getOutputStream();
                os.write("ok".getBytes());
                Toast.makeText(MainActivity.sContext,"writing ok",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.sContext,"os.write error",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.sContext,"clientSocket.connect error",Toast.LENGTH_SHORT).show();
        }
    }

    //if is server, accept the client
    private void serverAccept2Client(){
        try {
            /* 创建一个蓝牙服务器
            * 参数分别：服务器名称、UUID   */
            serverSocket = localAdapter.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM,
                    UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            /* 接受客户端的连接请求 */
            serverTransportSocket = serverSocket.accept();
            //accept is block, if it turn to next statment, it should be connected;
            isConnected=true;//if without this, no main view
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientGetDataFromServer(){
        try {
            is = serverTransportSocket.getInputStream();
            if(is.available()==0){//如果没有数据,返回
                return;
            }
            byte[] tmpByte = new byte[50];
            int k = is.read(tmpByte);
            Toast.makeText(MainActivity.sContext,"from server "+tmpByte.toString(),Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void clientSendData2Server(){
        try{
            os = clientTransportSocket.getOutputStream();
            Byte tmpByte = new Byte("from client,2 server");
            os.write(tmpByte);
            os.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void serverGetDataFromClient(){
        try {
            is = serverTransportSocket.getInputStream();
            if(is.available()==0){
                return;
            }
            byte[] tmpByte = new byte[50];
            int k = is.read(tmpByte);
            Toast.makeText(MainActivity.sContext,"from client "+tmpByte.toString(),Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void serverSendData2Client(){
        try{
            os = serverTransportSocket.getOutputStream();
            Byte tmpByte = new Byte("from server,2 client");
            os.write(tmpByte);
            os.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void shutdownServer(){
        try {
            if(serverSocket!=null){
                serverSocket.close();
                serverSocket=null;
            }
            if(serverTransportSocket!=null){
                serverTransportSocket.close();
                serverTransportSocket=null;
            }
        }catch (IOException e){
            Log.d("TransportData2","shutdown server error");
        }
    }

    public void shutdownClient(){
        try {
            if(clientTransportSocket!=null){
                clientTransportSocket.close();
                clientTransportSocket=null;
            }
        }catch (IOException e){
            Log.d("TransportData2","shutdown client error");
        }
    }
}
