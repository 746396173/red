package com.example.android_red;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android_red.Thread.CheckRedMessThread;
import com.android_red.Thread.GetRedUtil;
import com.android_red.Thread.MsgHandler;
import com.android_red.intenter.Const;
import com.android_red.intenter.Test;
import com.android_red.slideswitch.HightMainActivity;
import com.test.threads.ListenThread;
import com.test.threads.SendDataThread;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

@SuppressLint("NewApi")
public class QiangHongBaoService extends AccessibilityService
{

	public static Thread thread,threadlis;
	static final String TAG = "QiangHongBao";
	SQLiteDatabase db;
	/** ΢�ŵİ��� */
	static final String WECHAT_PACKAGENAME = "com.tencent.mm";
	/** �����Ϣ�Ĺؼ��� */
	static final String QQ_HONGBAO_TEXT_KEY = "[QQ���]";
	private boolean caihongbao = false;
	private static boolean chaikou = false;
	private static int chaikoustep = 0;
	private static boolean permit = false;
	private static AccessibilityNodeInfo nowRed=null;
	private static CheckRedMessThread checkRedThread=null;
	public static String putMess=null;
	public static List<AccessibilityNodeInfo> qqList=null;
	Handler handler1;
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event)
	{
		final int eventType = event.getEventType(); // ClassName:
													// com.tencent.mm.ui.LauncherUI
		
		if(putMess!=null){
		Toast.makeText(this, putMess, Toast.LENGTH_SHORT).show();
		Log.i(TAG, "--------------;"+putMess);
		putMess=null;
		}
		Log.i(TAG, "--->����AccessibilityEvent"+eventType);
		Log.i(TAG, "--->����class��"+event.getClassName());
		 if (eventType ==2048)
		{
			Log.i(TAG, "--->�µĺ��");
			// ����΢���������
			openHongBao(event);
		}else if(eventType ==32){
			  if("cooperation.qwallet.plugin.QWalletPluginProxyActivity".equals(event.getClassName()))
				{
					// ���������,ȥ���к��
					Log.i(TAG, "�����ֵ");
					Map<String,String> redMap= jianchalei(getRootInActiveWindow());
					 if(CheckRedMessThread.outWalletType==2){
					if(CheckRedMessThread.outWalletEnable){
					performGlobalAction(1);
					Log.i("QiangHongBaoThread", "�˳������¼");
					CheckRedMessThread.outWalletEnable=false;
					}
					CheckRedMessThread.outWalletType=0;
					 }else if(CheckRedMessThread.outWalletType==1){
						 CheckRedMessThread.outWalletType=2;
					 }
					if(checkRedThread!=null&&redMap!=null)
					checkRedThread.setRedMap(redMap);
					CheckRedMessThread.inRedMess=true;
				}else{
					openHongBao(event);
				}
		}
	}

	@Override
	public void onInterrupt()
	{
		Toast.makeText(this, "�ж����������", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onServiceConnected()
	{
		super.onServiceConnected();
		Toast.makeText(this, "�������������", Toast.LENGTH_SHORT).show();
		handler1 = new Handler() {   //����
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(QiangHongBaoService.this, msg.obj.toString(),
							1000).show();
					break;
				case 2:
					Toast.makeText(QiangHongBaoService.this, msg.obj.toString(),
							1000).show();
					break;
				default:
					break;
				}
			}

		};
		
		if(Const.ser == 1){    //���������߳�
		threadlis = new ListenThread(Const.port,handler1);
		threadlis.start();
		Log.i("server", "����������Ϣ�߳�");
		Const.ser++;
		 }
		
//		if(Const.qiang == 2){
//			handler.postDelayed(runnable, 4000);//���������߳�
//		}

		
		//handler2.postDelayed(runnable2, 500);//����ping
	}
	
//	Handler handler=new Handler();   //���������߳�
//	Runnable runnable=new Runnable() {  
//	    @Override  
//	    public void run() {  
//	        // TODO Auto-generated method stub  
//	        //Ҫ��������  
//	    	thread = new SendDataThread("192.168.0.102");
//	    	thread.start();  //�������η�����Ϣ
//	    	handler.postDelayed(this, 4000); 
//	    }  
//	};
	
//	Handler handler2=new Handler();  //����ping
//	Runnable runnable2=new Runnable() {  
//	    @Override  
//	    public void run() {  
//	        // TODO Auto-generated method stub  
//	        //Ҫ��������  
//	    	Process p,p1;
//	    	
//			try {
//				p = Runtime.getRuntime().exec("ping -c 1 -w 100 " +"192.168.0.102");
//				//p1 = Runtime.getRuntime().exec("ping -c 1 -w 100 " +"192.168.0.102");
//				int status = p.waitFor();
//				//int status2 = p1.waitFor();
//				Log.i("server", "ping�Լ����:"+status);	
//				//Log.i("server", "ping�Է����:"+status2);	
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				Log.i("server", "ping����1");	
//				e1.printStackTrace();
////				threadlis.interrupt();
////				
////				new ListenThread(Const.port,handler1).start();
//				
//			} catch (InterruptedException e) {
//				Log.i("server", "ping����2");	
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//			
//	    	handler2.postDelayed(this, 500); 
//	    }  
//	};
	
	/** ��֪ͨ����Ϣ */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void openNotify(AccessibilityEvent event)
	{
		if (event.getParcelableData() == null || !(event.getParcelableData() instanceof Notification))
		{
			return;
		}
		// ��΢�ŵ�֪ͨ����Ϣ��
		Notification notification = (Notification) event.getParcelableData();
		PendingIntent pendingIntent = notification.contentIntent;
		try
		{
			pendingIntent.send();
		} catch (PendingIntent.CanceledException e)
		{
			e.printStackTrace();
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void openHongBao(AccessibilityEvent event)
	{
		CharSequence className = event.getClassName();
		checkScreen(getApplicationContext());

		 if ( "com.tencent.mobileqq.activity.ChatActivity".equals(className))
		{
			Log.i(TAG, "���������");
			// ���������,ȥ���к��
			checkKey2();
		} else if ("com.tencent.mobileqq.activity.SplashActivity".equals(className))
		{
			Log.i(TAG, "�����������");
			// ���������,ȥ���к��
			checkKey2();
		}else{
			checkKey2();
			//checkNodeInfo(getRootInActiveWindow());
		}
	}

	private Map<String,String> jianchalei(AccessibilityNodeInfo nodeInfo){
		if(nodeInfo!=null&&nodeInfo.getChildCount()>3){
			Map<String,String> reMap=new HashMap<String, String>();
			
		AccessibilityNodeInfo redMessInfo=nodeInfo.getChild(3);
		String getStr=null;
			try{
				getStr=redMessInfo.getChild(0).getText().toString();
			}catch (Exception e){
				reMap.put("code","1");
				return reMap;
			}
		String remain="";
		int yuIndex=getStr.indexOf("��");
		if(yuIndex!=-1){
		remain=getStr.substring(yuIndex+1, getStr.length()-1); 
		Log.i(TAG, "ʣ��:"+remain);
		getStr=getStr.substring(3, 6);
		String[] getArr=getStr.split("/");
		Log.i(TAG, "��ȡ�ַ���:"+getStr);
		Log.i(TAG, "�����Ϣ:"+redMessInfo.getText());
		Log.i(TAG, "����б��С"+redMessInfo.getChildCount());
			reMap.put("code", "0");
			reMap.put("redSum", getArr[1]);
			reMap.put("redNow", getArr[0]);
			reMap.put("remain", remain);
		}else{
			reMap.put("code","2");
			Log.i(TAG, "���������");
		}
		return reMap;
		}else
			return null;
		
	}
	
	
	
	private void checkNodeInfo(AccessibilityNodeInfo nodeInfo){
		
		Log.i(TAG, "�����С��"+nodeInfo.getChildCount());
		if(nodeInfo!=null&&nodeInfo.getChildCount()>0)
		for(int i=0;i<nodeInfo.getChildCount();i++){
			if(nodeInfo.getChild(i).getChildCount()>0){
				jianchalei(nodeInfo.getChild(i));
			}else{
				Log.i(TAG, "--->��������˵��"+i+":"+ nodeInfo.getChild(i).getText());
			}
		}else
			Log.i(TAG, "--->û�к�������˵��:"+ nodeInfo.getText());
	}
	
	/**
	 * 
	 * @description: �����Ļ�Ƿ����Ų��һ�����Ļ
	 * @date: 2016-1-29 ����2:08:25
	 * @author: yems
	 */
	private void checkScreen(Context context)
	{
		// TODO Auto-generated method stub
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		if (!pm.isScreenOn())
		{
			wakeUpAndUnlock(context);
		}

	}

	private void wakeUpAndUnlock(Context context)
	{
		KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		// ����
		kl.disableKeyguard();
		// ��ȡ��Դ����������
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		// ��ȡPowerManager.WakeLock����,����Ĳ���|��ʾͬʱ��������ֵ,������LogCat���õ�Tag
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// ������Ļ
		wl.acquire();
		// �ͷ�
		wl.release();
	}


	
	private int chailei(String leiStr){
		Log.i(TAG, "--->���������ֵ"+leiStr);
		 String[] fenchar={"/","-",":","--","��",".","+","=","%","#","!","��"};
		 for(int i=0;i<fenchar.length;i++){
			 if(leiStr.indexOf(fenchar[i])!=-1){
				 Log.i(TAG, "--->�����ַ�");
				 String[] leiArr;
				 if(i==5||i==6)
					  leiArr=leiStr.split("\\"+fenchar[i]);
				 else
					  leiArr=leiStr.split(fenchar[i]);
				 
				 if(leiArr.length==2){
					 Log.i(TAG, "--->�ָ��ַ�");
					 int[] leiIntArr=new int[2];
					 leiIntArr[0]=Integer.parseInt(leiArr[0]);
					 leiIntArr[1]=Integer.parseInt(leiArr[1]);
					 if(leiIntArr[0]<leiIntArr[1])
						 return leiIntArr[0];
					 else
						 return leiIntArr[1];
				 }
			 }
		 }
		 return 10;
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void checkKey2()
	{
		try {
		Log.i(TAG, "--->����checkKey2");
		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
		GetRedUtil.mainInfo=nodeInfo.getChild(4);
		if (nodeInfo == null)
		{
			Log.w(TAG, "rootWindowΪ��");
			return;
		}
		Log.w(TAG, "������Ĵ�С��"+nodeInfo.getChildCount());
		
		qqList = nodeInfo.findAccessibilityNodeInfosByText("QQ���");
		
		if (!qqList.isEmpty())
		{
			Log.i(TAG, "--->�����:qqList�Ĵ�С" + qqList.size());
			for (AccessibilityNodeInfo n : qqList)
			{
				Log.i(TAG, "--->���������:"+ n.getParent().toString());
				//n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
			}
		}
		
		 if (!qqList.isEmpty())
		{
			// �����ϵĺ���ܸ���
			//int totalCount = qqList.size();
			// ��ȡ������ĺ��
				// ���Ϊ��ȡ���ú������ִ�е����
			 for(int i=0;i<qqList.size();i++){
				 AccessibilityNodeInfo	parent = qqList.get(i).getParent();
				Log.i(TAG, "-->������id:" + parent.getWindowId());
				if (parent != null&&parent.getChildCount()>1)
				{
					if(CheckRedMessThread.inRedMess==true&&checkRedThread!=null)
						checkRedThread.affirminRedMess=true;
					if(parent.getChild(1).getText().toString().equals("�����")){
						/*for(int i=0;i<parent.getChildCount();i++){
							Log.i(TAG, "-->�����������:" + parent.getChild(i).getContentDescription());
						}
						Log.i(TAG, "-->���������ı�:" + parent.getChild(1).getText());
						Log.i(TAG, "-->����������:" + parent.getClassName());
						if(nowRed!=null){
							Log.i(TAG, "�ϴκ��"+nowRed);
							Log.i(TAG, "��κ��"+parent);
						if(parent.equals(nowRed))
							Log.i(TAG, "�����ϴκ��");
						}else{
							Log.i(TAG, "�����º��");
							nowRed=parent;
						}*/
						if(Const.qiang==2){
						if(checkRedThread==null||!checkRedThread.isAlive()){
							Log.i(TAG, "��������¼���:"+parent.getChild(2).getText().toString());
							int lei= chailei(parent.getChild(0).getText().toString());
							Log.i(TAG, "��������ֵ:"+lei);
							if(lei!=10){
								boolean createTh=false;
								for(int k=0;k<CheckRedMessThread.infoList.size();k++){
									if(CheckRedMessThread.infoList.get(k).equals(parent)){
										createTh=true;
										Log.i(TAG, "���ظ�������˳�"+i);
										break;
									}
								}
								if(createTh)
									continue;
							checkRedThread=new CheckRedMessThread(parent,lei,nodeInfo.getChild(4));
							checkRedThread.start();
							}
							continue;
						}
						return;
					}
					}
//					else if(Const.qiang==1){
//						Log.i(TAG, "set1111111111111111111111");
//						if(GetRedUtil.getdata){
//						Log.i(TAG, "set2222222222222222");
//						AccessibilityNodeInfo parent1= parent.getParent();
//						if(parent1.getChild(parent1.getChildCount()-2).equals(GetRedUtil.redSend)){
//							Log.i(TAG, "set333333333333333");
//							GetRedUtil.setRedMess(parent);
//						}
//						return;
//						}
//					}
//					else if(parent.getChild(1).getText().toString().equals("������")){
//						if(!chaikou)
//						switch (chaikoustep) {
//						case 0:
//							parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//							chaikoustep=1;
//							break;
//						case 1:
//							List<AccessibilityNodeInfo> kouling = nodeInfo.findAccessibilityNodeInfosByText("����������");
//							if(kouling!=null&&kouling.size()!=0)
//							{
//								Log.i(TAG, "-->�������:i" + kouling.get(0).getText());
//								kouling.get(0).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//							}
//							chaikoustep=2;
//							break;
//						case 2:
//							List<AccessibilityNodeInfo> sendbnt = nodeInfo.findAccessibilityNodeInfosByText("����");
//							if(sendbnt!=null&&sendbnt.size()!=0)
//							{
//								Log.i(TAG, "-->�������:i" + sendbnt.get(0).getText());
//								sendbnt.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//							}
//							Log.i(TAG, "-->��ȡ������������:");
//							chaikou=true;
//							chaikoustep=0;
//							break;
//						default:
//							break;
//						}else{
//							chaikou=false;
//						}
//					}
				}
		}
	}
		 if(nodeInfo.getChild(4)!=null)
			 if(Const.qiang==2)
				 nodeInfo.getChild(4).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
		} catch (Exception e) {
			return;
		}
	}

    @Override 
    public int onStartCommand(Intent intent, int flags, int startId) { 
        return START_STICKY;
    }
    
	
}
