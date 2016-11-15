package com.test.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.json.JSONException;
import org.json.JSONObject;

import com.android_red.Thread.GetRedUtil;
import com.android_red.intenter.Const;
import com.android_red.slideswitch.HightMainActivity;
import com.test.service.LocalService;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ListenThread extends Thread {
	static final String TAG = "QiangHongBaoThread";
	ServerSocket socket=null;
	public ListenThread(int port, Handler handler)
	{
		try {
			port=12345;
			socket=new ServerSocket(port);//����������12345�˿�
			this.handler=handler;
		} catch (IOException e) {
			Log.d("aaa", "ListenThread ServerSocket init() has exception");
		}
		
	}
	 Handler handler;
	@Override
	public void run() {
		while (true) {
			try {
				
				 Message msg=new Message();
				 /*msg.what=2;
				 msg.obj="ServerSocket ���ڵȴ����ݴ���";
				 this.handler.sendMessage(msg);*/
				final Socket soc=socket.accept();//�ȴ���Ϣ
				String ip = soc.getRemoteSocketAddress().toString();
				//�ã������н�ȡ ��ȡ��IP ����
				String a[] = ip.split(":");
				ip = a[0].substring(1);
				InputStream is=soc.getInputStream();//��ȡ��Ϣ
				/*msg.what=2;
				 msg.obj="ServerSocket ����ɹ�";
				 this.handler.sendMessage(msg);*/
				if (is!=null) {
					 BufferedReader in=  new BufferedReader(new InputStreamReader(is,"UTF-8"));
					 PrintWriter out = new PrintWriter(soc.getOutputStream());//�����Ϣ
					 String str="";
					 str=in.readLine();
					 if(str.equals("abc")){
						 str="��������";
						 Const.mess = "123";
						 new SendDataThread(ip).start();
					 }else if(str.equals("123")){
						 str=ip+"���Գɹ�";
					 }else{
						 if(!str.equals("")){
						 try {
							JSONObject dataJson = new JSONObject(str);
							if(dataJson.getString("code").equals("10001")){
								Log.i(TAG, "code==10001");
								String redsend = dataJson.getString("redsend");
								GetRedUtil.updateRedMess(redsend,dataJson.getString("leiStr"));
								Log.i(TAG, "code==10001,redsend=="+redsend);
							}else if(dataJson.getString("code").equals("10002")){
								Log.i(TAG, "code==10002");
								GetRedUtil.getRed();	
							}else{
								if(GetRedUtil.mainInfo!=null)
								GetRedUtil.nogetRed();
								//����
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
						 }
					 }
					 
					 msg.what=1;
					 msg.obj=str;
					 this.handler.sendMessage(msg);
					 soc.close();
				}else
				{
					Log.d("aaa", "û�н��յ�����");
				}
				
			} catch (IOException e) {
				Log.d("aaa", "ListenThread.run() -->final Socket soc=socket.accept();has exception");
			}
			
		}
	}
}
