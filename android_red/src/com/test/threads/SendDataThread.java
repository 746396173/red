package com.test.threads;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.android_red.intenter.Const;

import android.os.AsyncTask;
import android.util.Log;

public class SendDataThread extends Thread{
	Socket socket;
	public SendDataThread(String address)
	{
		this.address=address;
	}String address;
	@Override
	public void run() {
		try {
			socket=new Socket(address, 12345);//���͵�������ĳ��Ip�Ķ˿���
		} catch (UnknownHostException e) {
			Log.d("aaa", "SendDataThread.init() has UnknownHostException"+e.getMessage() );
		} catch (IOException e) {
			Log.d("aaa", "SendDataThread.init().IOException:"+e.getMessage());
		}
		if (socket!=null) {
			try {
				PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
//				if(Const.cs==1){
//					out.println("abc");  //�������� 
//				}else if(Const.cs==2){
//					out.println("123");  //�������� 
//				}else{
                out.println(Const.mess);  //�������� 
                Const.mess="";
                //Const.mess      //��Ҫ���͵�����
				//}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
