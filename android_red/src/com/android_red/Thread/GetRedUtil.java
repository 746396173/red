package com.android_red.Thread;

import com.android_red.intenter.Const;
import com.example.android_red.QiangHongBaoService;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

public class GetRedUtil {
	static final String TAG = "QiangHongBaoThread";
	public static AccessibilityNodeInfo redInfo;
	public static AccessibilityNodeInfo mainInfo;
	public static String redSend="";
	public static boolean getdata=false;
	public static boolean getEnable=false;
	
	
	public static void updateRedMess(String send,String leiStr){
		redSend=send;
		QiangHongBaoService.putMess="�յ�����������";      
		getdata=true;
		findRed(leiStr);
		
	}
	
	public static void findRed(String leiStr){
		Log.i(TAG, "set1111111111111111111111");
		int n=0;
		 AccessibilityNodeInfo	useParent =null;
		 if (!QiangHongBaoService.qqList.isEmpty())
			{
				 for(int i=0;i<QiangHongBaoService.qqList.size();i++){
					 AccessibilityNodeInfo	parent = QiangHongBaoService.qqList.get(i).getParent();
					Log.i(TAG, "-->������id:" + parent.getWindowId());
					if (parent != null&&parent.getChildCount()>1){
					if(parent.getChild(1).getText().toString().equals("�����")){
			if(Const.qiang==1){
			Log.i(TAG, "set1111111111111111111111");
			if(GetRedUtil.getdata){
			Log.i(TAG, "set2222222222222222");
			AccessibilityNodeInfo parent1= parent.getParent();
			if(parent1.getChild(parent1.getChildCount()-2).getText().toString().equals(redSend)&&
					parent1.getChild(parent1.getChildCount()-1).getChild(0).getText().toString().equals(leiStr)){
				Log.i(TAG, "set333333333333333");
				n++;
				Log.i(TAG, "set33333333333n��ֵ"+n);
				useParent=parent;
			}
			}
		}}}}
				 if(n>1){
					 QiangHongBaoService.putMess="�����������޷��жϣ�����";      
				 }else if(n==1){
					 setRedMess(useParent);
					 getEnable=true;
				 }else
				 mainInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
				 }
	}
	
	public static void setRedMess(AccessibilityNodeInfo redInfo1) {
		redInfo = redInfo1;
		QiangHongBaoService.putMess="���ҵ���Ӧ�ĺ��";
		Log.v(TAG, "=============2343242342=========");
		getdata=false;
	}
	
	public static  void getRed() {
		Log.v(TAG, "====================================");
		boolean end=true;
		if(getEnable)
		for(int i=0;i<2&&end;i++){
			try {
				redInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				QiangHongBaoService.putMess="�����˺��";
				CheckRedMessThread.outWalletEnable=true;
				CheckRedMessThread.outWalletType=2;
				getEnable=false;
				end=false;
			} catch (Exception e) {
				if(i==2){
					QiangHongBaoService.putMess="���û���ҵ�";
					Log.v(TAG, "���û���ҵ�:");
				}else
				mainInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
				
			}
		}
		if(!end){
			mainInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
		}
	}
	
	public static void nogetRed(){
		QiangHongBaoService.putMess="������ǰ���Ӻ��";
		mainInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
	}
}
