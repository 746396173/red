package com.test.threads;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.android_red.intenter.Const;
import com.example.android_red.QiangHongBaoService;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class SendDataThread extends Thread{
	Socket socket;
	public SendDataThread(String address)
	{
		this.address=address;
	}
	String address;
	@Override
	public void run() {
		try {
			Log.i("server", "���뷢����Ϣ");
			Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " +address); 
			try {
				int status = p.waitFor();
				Log.i("server", "ping���:"+status);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Log.i("server", "ping����");	
				e.printStackTrace();
			}
			
			    socket = new Socket();   
				//socket=new Socket(address, 12345);//���͵�������ĳ��Ip�Ķ˿���
				SocketAddress socketAddress = new InetSocketAddress(address, Const.port);
				socket.connect(socketAddress,500);
			
		} catch (UnknownHostException e) {
			Log.d("aaa", "SendDataThread.init() has UnknownHostException"+e.getMessage() );	        
			QiangHongBaoService.thread.interrupt();
			try {
				sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new SendDataThread(Const.ip).start(); 
		} catch (IOException e) {
			Log.d("aaa", "SendDataThread.init().IOException:"+e.getMessage());
			QiangHongBaoService.thread.interrupt();
			try {
				sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new SendDataThread(Const.ip).start(); 
		}

		if (socket!=null) {
			try {
				PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
//				if(Const.cs==1){
//					out.println("abc");  //�������� 
//				}else if(Const.cs==2){
//					out.println("123");  //�������� 
//				}else{
				Log.i("server", "��ʼ������Ϣ"+Const.mess);
                out.println(Const.mess);  //�������� 
				//out.println("456");  //�������� 
                Const.mess="";
                //Const.mess      //��Ҫ���͵�����
				//}
                out.close();
                //socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}