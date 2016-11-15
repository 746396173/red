//�����ʱ��ͼƬ����ҳ��

package com.android_red.shoye;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android_red.intenter.AsyncTaskManager;
import com.android_red.intenter.AsyncTaskSoap.OnDataRecvListener;
import com.android_red.intenter.CustomProgressDialog;
import com.android_red.intenter.NetWork;
import com.example.android_red.MainActivity;
import com.example.android_red.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ���ܣ�ʹ��ViewPagerʵ�ֳ��ν���Ӧ��ʱ������ҳ
 * 
 * (1)�ж��Ƿ����״μ���Ӧ��--��ȡ��ȡSharedPreferences�ķ��� (2)�ǣ����������activity���������MainActivity
 * (3)5s��ִ��(2)����
 * 
 * @author sz082093
 * 
 */
public class SplashActivity extends Activity{

	AsyncTaskManager atm = new AsyncTaskManager(); // �첽ͨ�Ź�����  �����������������ʱʹ�ã�
//  Handler��AsyncTask������Ϊ�˲��������̣߳�UI�̣߳�����UI�ĸ���ֻ�������߳�����ɣ�����첽�����ǲ��ɱ���
//	ProcessDlgAction pda = new ProcessDlgAction(); // ���ȶԻ��򣨼��أ�
	CustomProgressDialog pda;
	
	String fileEx, fileNa, filename;
	static int fileSize;
	static int downLoadFileSize;
	private final static String TAG = "SplashActivity";  
    private final static String ALBUM_PATH  
            = Environment.getExternalStorageDirectory() + "/download_image/";  
    private Bitmap mBitmap;  
    private String mFileName;  
    private String mSaveMessage; 
    private static List list = new ArrayList();
    Timer timer = new Timer();  
    private int recLen = 6;  
    private TextView txtView;   
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.imagelogin);
		txtView = (TextView)findViewById(R.id.texttime);    
		boolean mFirst = isFirstEnter(SplashActivity.this, SplashActivity.this
				.getClass().getName());
		//�жϴ�ʱ�Ƿ�������
//	if (NetWork.isNetworkAvailable(SplashActivity.this)) {  //�жϴ�ʱ�Ƿ�������
//				pda =new CustomProgressDialog(this, "",R.anim.frame);   //���ض���
//				pda.show();
//				// �����첽�̣߳��ص������ʷ���������ȡ���������ص�ret���ж��Ƿ��¼�ɹ�
//				atm.startAsyncTask("imagelist", "", odrLsn, 1);//���ʷ�����,����������һ��list
//		
//	}else{
//		Toast.makeText(getApplicationContext(), "��ǰû�п������磡",
//				Toast.LENGTH_LONG).show();
//	}
//		 new Thread(connectNet).start(); //����ͼƬ
		Message message = mHandler.obtainMessage(1);     // Message  
		if (mFirst)
		mHandler.sendEmptyMessageDelayed(1, 1000);
	else
		mHandler.sendEmptyMessageDelayed(1, 1000);
		

 
	}

	// �ж�Ӧ���Ƿ���μ��أ���ȡSharedPreferences�е�guide_activity�ֶ�

	private static final String SHAREDPREFERENCES_NAME = "my_pref";
	private static final String KEY_GUIDE_ACTIVITY = "guide_activity";

	private boolean isFirstEnter(Context context, String className) {
		if (context == null || className == null
				|| "".equalsIgnoreCase(className))
			return false;
		String mResultStr = context.getSharedPreferences(//ͨ��Context.getSharedPreferences������ȡSharedPreferences����,�����ֱ�Ϊ�洢���ļ����ʹ洢ģʽ��
				SHAREDPREFERENCES_NAME, Context.MODE_WORLD_READABLE).getString(
				KEY_GUIDE_ACTIVITY, "");// ȡ���������� �� com.my.MainActivity
		if (mResultStr.equalsIgnoreCase("false")) //�ַ����Ƚ�
			return false;
		else
			return true;
	}

	// *************************************************
	// Handler:��ת����ͬҳ��
	// *************************************************
	private final static int SWITCH_MAINACTIVITY = 1000;
	private final static int SWITCH_GUIDACTIVITY = 1001;
	
	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:   //�Ѿ����ڣ���ת���
				recLen--;    
                txtView.setText("���ڼ�����Դ�ļ�������ʱ��" + recLen+"��");    
   
                if(recLen > 0){    
                    Message message = mHandler.obtainMessage(1);    
                    mHandler.sendMessageDelayed(message, 1000);      // send message    
                }else{    
                    txtView.setVisibility(View.GONE);
            		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            		startActivity(intent);
                }  
			super.handleMessage(msg);
		}
	};
	};
	
	// �첽����������ϻص����̴߳���
		OnDataRecvListener odrLsn = new OnDataRecvListener() {
			@Override
			public void onDataRecv(JSONObject jsonObj, int code) {
				pda.dismiss();
				if(null==jsonObj){   //���粻��ʱ����ֳ���������������ȡ����ret
					Toast.makeText(SplashActivity.this, "��������", Toast.LENGTH_LONG).show();
				}else{
				try {
					int ret = jsonObj.getInt("ret");
					if (0 == ret) {
						JSONArray arr = jsonObj.getJSONArray("list"); //6��ͼƬ��url
						if(arr.length()==6){
							File root = new File(Environment.getExternalStorageDirectory()+ "/download_image");
							deleteAllFiles(root);
							Toast.makeText(getApplicationContext(), "��ʱͼƬ��6�ţ���ʼ��",
									Toast.LENGTH_LONG).show();
						}
						for(int i=0;i<arr.length();i++){
							list.add(arr.get(i));	
						}
						Toast.makeText(getApplicationContext(), "����ͼƬ��",
								Toast.LENGTH_LONG).show();
						        new Thread(connectNet).start(); //����ͼƬ
					} else {
						Toast.makeText(SplashActivity.this, "�������˹��ͼƬ���󣬴ӱ��ؽ��л�ȡ", Toast.LENGTH_LONG)
								.show();
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "�����������쳣��",
							Toast.LENGTH_LONG).show();
				  }
				}
			}
		};
    /** 
     * Get image from newwork 
     * @param path The path of image 
     * @return byte[] 
     * @throws Exception 
     */  
    public byte[] getImage(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5 * 1000);  
        conn.setRequestMethod("GET");  
        InputStream inStream = conn.getInputStream();  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return readStream(inStream);  
        }  
        return null;  
    }  
  
    /** 
     * Get image from newwork 
     * @param path The path of image 
     * @return InputStream 
     * @throws Exception 
     */  
    public InputStream getImageStream(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5 * 1000);  
        conn.setRequestMethod("GET");  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return conn.getInputStream();  
        }  
        return null;  
    }  
    /** 
     * Get data from stream 
     * @param inStream 
     * @return byte[] 
     * @throws Exception 
     */  
    public static byte[] readStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1){  
            outStream.write(buffer, 0, len);  
        }  
        outStream.close();  
        inStream.close();  
        return outStream.toByteArray();  
    }  
  
    /** 
     * �����ļ� 
     * @param bm 
     * @param fileName 
     * @throws IOException 
     */  
    public void saveFile(Bitmap bm, String fileName) throws IOException {  
        File dirFile = new File(ALBUM_PATH);  
        if(!dirFile.exists()){  
            dirFile.mkdir();  
        }  
        File myCaptureFile = new File(ALBUM_PATH + fileName);  
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
        bos.flush();  
        bos.close();  
    }  
  
    
    private Runnable saveFileRunnable = new Runnable(){  
        @Override  
        public void run() {  
            try {  
                saveFile(mBitmap, mFileName); 
                System.out.println(mFileName+"ͼƬ����ɹ���");
                mSaveMessage = "ͼƬ����ɹ���";  
            } catch (IOException e) {  
            	System.out.println("ͼƬ����ʧ�ܣ�");
                mSaveMessage = "ͼƬ����ʧ�ܣ�";  
                e.printStackTrace();  
            }   
        }  
    };  

    /* 
     * �������� 
     * ������4.0�в����������߳��з������磬������Ҫ�����߳��з��� 
     */  
    private Runnable connectNet = new Runnable(){  
        @Override  
        public void run() {  
            try { 
            	for(int i = 0;i<list.size();i++){
            		String filePath = "http://test.tx15.eccentertool.com:39011/ueditor/jsp/upload/image/"+list.get(i).toString(); 
                    mFileName =i+".jpg";  
                    System.out.println(mFileName);
                    //////////////// ����1��ȡ�õ���byte����, ��byte��������bitmap  
                    byte[] data = getImage(filePath);  
                    if(data!=null){  
                        mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap  
                        new Thread(saveFileRunnable).start(); 
                    }else{  
                    	System.out.println(mFileName+"error");
                        Toast.makeText(SplashActivity.this, "Image error!", 1).show();  
                    }
                    if(i == list.size()-1){
                    	filePath = "http://test.tx15.eccentertool.com:39011/ueditor/jsp/upload/image/"+list.get(5).toString(); 
                        mFileName =6+".jpg";  
                        System.out.println(mFileName);
                        data = getImage(filePath);  
                        if(data!=null){  
                            mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap  
                            new Thread(saveFileRunnable).start(); 
                        }else{  
                        	System.out.println(mFileName+"error");
                            Toast.makeText(SplashActivity.this, "Image error!", 1).show();  
                        } 
                    }
            	} 
            } catch (Exception e) {  
                Toast.makeText(SplashActivity.this,"�޷��������磡", 1).show();  
                e.printStackTrace();  
            }  
        }  
    };  
   
    //ɾ���ļ����������ļ�
	private void deleteAllFiles(File root) {  
        File files[] = root.listFiles();  
        if (files != null) 
            for (File f : files) {  
                    if (f.exists()) {         // �ж��Ƿ����  
                        deleteAllFiles(f);  
                        try {  
                            f.delete();  

                        } catch (Exception e) {  
                        }  
                    }  
            }  
    } 
}