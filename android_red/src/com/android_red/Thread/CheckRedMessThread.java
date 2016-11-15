package com.android_red.Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Rect;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.android_red.intenter.Const;
import com.android_red.intenter.Test;
import com.android_red.slideswitch.HightMainActivity;
import com.example.android_red.QiangHongBaoService;
import com.test.threads.SendDataThread;

public class CheckRedMessThread extends Thread{
	static final String TAG = "QiangHongBaoThread";
	public AccessibilityNodeInfo redInfo;
	public AccessibilityNodeInfo mainInfo;
	public static List<AccessibilityNodeInfo> infoList=new ArrayList<AccessibilityNodeInfo>();
	private Map<String,String> redMap;
	private boolean disEnd=true;
	public static boolean inRedMess=true;
	public static boolean fistCreate=true;
	private  int leizhi=10;
	public static boolean outWalletEnable = false; 
	public static int outWalletType = 0; 
	public  boolean affirminRedMess = true; 
	private  int saotimes=0;
	
	
	
	public CheckRedMessThread(AccessibilityNodeInfo redInfo,int leizhi,AccessibilityNodeInfo mainInfo) {
		super();
		QiangHongBaoService.putMess="���ڼ���º��";
		this.redInfo = redInfo;
		this.mainInfo = mainInfo;
		this.leizhi = leizhi;
		infoList.add(redInfo);
		if(infoList.size()>1)
			infoList.remove(0);
		AccessibilityNodeInfo parent1=redInfo.getParent();
		JSONObject json=new JSONObject();
		try {
			String redsend=parent1.getChild(parent1.getChildCount()-2).getText().toString();
			String leiStr=parent1.getChild(parent1.getChildCount()-1).getChild(0).getText().toString();
			
			json.put("code", 10001);
			json.put("redsend", redsend);
			json.put("leiStr", leiStr);
			String putStr=json.toString();
			
			Const.mess = putStr;
			new SendDataThread(HightMainActivity.textip.getText().toString()).start();  //�������η�����Ϣ
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//redBottomThread=new RedBottomThread(redInfo);
		//redBottomThread.start();
	}


	public void setLeizhi(int leizhi) {
		this.leizhi = leizhi;
	}


	public void setRedMap(Map<String, String> redMap) {
		this.redMap = redMap;
			if(redMap.get("code").equals("0")){
			int code= new Test().AA1(redMap.get("remain"), leizhi, redMap.get("redNow"), redMap.get("redSum"));
			Log.i(TAG, "--->����Ƿ�����ֵ:" + code);
			if(code==0){
				disEnd=false;
				Log.w(TAG, "--->��������:");
				JSONObject json=new JSONObject();
				try {
					json.put("code", 10002);
					String putStr=json.toString();
					
				//	QiangHongBaoService.putMess="��֪ͨȥ�����";	
					Const.mess = putStr;
					new SendDataThread(HightMainActivity.textip.getText().toString()).start();  //�������η�����Ϣ
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//redBottomThread.setRedEnd(false);
			}else if(code==2){
				Log.w(TAG, "--->����ֵ��������:");
				
				disEnd=false;
				JSONObject json=new JSONObject();
				try {
					json.put("code", 20001);
					
					String putStr=json.toString();
					
					QiangHongBaoService.putMess="��֪ͨ����ֵ";
					Const.mess = putStr;
					new SendDataThread(HightMainActivity.textip.getText().toString()).start();  //�������η�����Ϣ
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Tobottom(mainInfo).start();
			}
				//redBottomThread.setRedEnd(false);
			}else{
				Log.w(TAG, "--->��������ˣ�������:");
				disEnd=false;
				JSONObject json=new JSONObject();
				try {
					json.put("code", 20002);
					String putStr=json.toString();
					QiangHongBaoService.putMess="��֪ͨ���������";
					
					Const.mess = putStr;
					new SendDataThread(HightMainActivity.textip.getText().toString()).start();  //�������η�����Ϣ
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//redBottomThread.setRedEnd(false);
			}
	}



	public void run() {
		
		Random ran=new Random();
		int i=0;
		final int checktimes=Const.ws+1;
		int delaytime=80;
		int randelay=(Const.jdt_max-Const.jdt_min)*100/Const.jdt_min;
//		if(fistCreate){
//			delaytime=80;
//			fistCreate=false;
//		}else{
//			delaytime=40;
//		}
		if(!Const.switch5){
			try {
				sleep((ran.nextInt(6)+3)*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//redInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
		}else{
		
		while(disEnd&&i<checktimes){
			Log.i(TAG, "�ٴν���ѭ��:");
			i++;
			for(int k=0;k<Const.jdt_min;k++){
			try {
				sleep(100);
				sleep(randelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
			 if(disEnd&&affirminRedMess&&i<checktimes){
				 boolean re=true;
				 int k=0;
				 while(re&&k<3){
					 try{
					 redInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					 inRedMess=false;
					 affirminRedMess=false;
					outWalletEnable=true;
					if(i==1){
						outWalletType=2;
					}else
					outWalletType=1;
					 Log.i(TAG, "ɨ�����:"+saotimes);
					 saotimes++;
					 re=false;
					 }catch(Exception e){
						 try {
							 mainInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
						} catch (Exception e2) {
							break;
						}
						
						 k++;
					 }
				 }
			if(k==3){
				Log.w(TAG, "--->����Ҳ����ˣ�������:");
				JSONObject json=new JSONObject();
				try {
					json.put("code", 10003);
					
					String putStr=json.toString();
					
					QiangHongBaoService.putMess="����Ҳ����ˣ�����";
					Const.mess = putStr;
					new SendDataThread(HightMainActivity.textip.getText().toString()).start();  //�������η�����Ϣ
					
				} catch (JSONException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mainInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
				break;
			}
			Log.i(TAG, "���ڼ���߳�:");
			}else{
				Log.i(TAG, "ɨ������affirminRedMess:"+affirminRedMess);
				Log.i(TAG, "ɨ������disEnd:"+disEnd);
				Log.i(TAG, "ɨ������i:"+i);
			}
		}
		if(i==checktimes){
			Log.w(TAG, "--->���ɨ��������ˣ�������:");
			JSONObject json=new JSONObject();
			try {
				json.put("code", 10004);
				String putStr=json.toString();
				
				QiangHongBaoService.putMess="���ɨ��������ˣ�����";
				Const.mess = putStr;
				new SendDataThread(HightMainActivity.textip.getText().toString()).start();  //�������η�����Ϣ
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mainInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
		}
		}
		Log.i(TAG, "����߳̽���:");
		super.run();
	}
}
