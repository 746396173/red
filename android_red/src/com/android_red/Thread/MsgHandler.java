package com.android_red.Thread;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MsgHandler extends Handler {  
    private Activity activity;  
    public MsgHandler(Activity activity){  
        this.activity=new WeakReference<Activity>(activity).get();//ʹ��������<span style="font-family: Arial, Helvetica, sans-serif;">WeakReferenc</span>����<span style="color:#ff0000;">����Handler�ڴ�й¶</span>  
    }  
    @Override  
    public void handleMessage(Message msg) {  
  
        switch (msg.arg1) {  
        case 1:  
            showInfo("��¼�ɹ���");  
            break;  
        case 2:  
            showInfo("�û����ƻ�������������������룡");  
            break;  
        case 3:  
            showInfo("�����¼�ɹ�,�Ժ��˺ź�������Զ�����!");  
            break;  
        case 4:  
            showInfo("����δ����!");  
            break;  
        case 5:  
            showInfo("�û��˻��Ǳ����");  
            break;  
        case 6:  
            showInfo("�û������Ǳ����");  
            break;  
        default:  
            break;  
        }  
        super.handleMessage(msg);  
    }  
      
    /**  
     * ��ʾ��ʾ��Ϣ  
     * @param info  
     */  
    public void showInfo(String info)  
    {  
        Toast.makeText(activity.getApplicationContext(),info, Toast.LENGTH_SHORT).show();  
    }  
  
}  
