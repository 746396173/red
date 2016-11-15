package com.android_red.intenter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.android_red.R;

public class IcsTestActivity extends Activity {  
  
    private final static String TAG = "IcsTestActivity";  
    private final static String ALBUM_PATH  
            = Environment.getExternalStorageDirectory() + "/download_test/";  
    private ImageView mImageView;  
    private Button mBtnSave;  
    private ProgressDialog mSaveDialog = null;  
    private Bitmap mBitmap;  
    private String mFileName;  
    private String mSaveMessage;  
  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.mainimg);  
  
        mImageView = (ImageView)findViewById(R.id.imgSource);  
        mBtnSave = (Button)findViewById(R.id.btnSave);  
  
        new Thread(connectNet).start();  
  
        // ����ͼƬ  
        mBtnSave.setOnClickListener(new Button.OnClickListener(){  
            @Override
			public void onClick(View v) {  
                mSaveDialog = ProgressDialog.show(IcsTestActivity.this, "����ͼƬ", "ͼƬ���ڱ����У����Ե�...", true);  
                new Thread(saveFileRunnable).start();  
        }  
        });  
    }  
  
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
                mSaveMessage = "ͼƬ����ɹ���";  
            } catch (IOException e) {  
                mSaveMessage = "ͼƬ����ʧ�ܣ�";  
                e.printStackTrace();  
            }  
            messageHandler.sendMessage(messageHandler.obtainMessage());  
        }  
    };  
  
    private Handler messageHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            mSaveDialog.dismiss();  
            Log.d(TAG, mSaveMessage);  
            Toast.makeText(IcsTestActivity.this, mSaveMessage, Toast.LENGTH_SHORT).show();  
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
                String filePath = "http://ds.tx15.dianshangshikong.com:39003/Testimg/image/back.jpg";  
                mFileName = "test2.jpg";  
  
                //������ȡ��ͼƬ�����ַ���  
                //////////////// ����1��ȡ�õ���byte����, ��byte��������bitmap  
                byte[] data = getImage(filePath);  
                if(data!=null){  
                    mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap  
                }else{  
                    Toast.makeText(IcsTestActivity.this, "Image error!", 1).show();  
                }  
                ////////////////////////////////////////////////////////  
  
                //******** ����2��ȡ�õ���InputStream��ֱ�Ӵ�InputStream����bitmap ***********/  
                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));  
                //********************************************************************/  
  
                // ������Ϣ��֪ͨhandler�����߳��и���UI  
                connectHanlder.sendEmptyMessage(0);  
                Log.d(TAG, "set image ...");  
            } catch (Exception e) {  
                Toast.makeText(IcsTestActivity.this,"�޷��������磡", 1).show();  
                e.printStackTrace();  
            }  
  
        }  
  
    };  
  
    private Handler connectHanlder = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            Log.d(TAG, "display image");  
            // ����UI����ʾͼƬ  
            if (mBitmap != null) {  
                mImageView.setImageBitmap(mBitmap);// display image  
            }  
        }  
    };  
  
}  