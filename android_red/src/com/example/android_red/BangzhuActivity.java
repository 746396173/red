package com.example.android_red;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BangzhuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bangzhu);
		init();  
		 } 
	
	private void init(){
		WebView webView = (WebView) findViewById(R.id.webView01);  
        //WebView����web��Դ
       webView.loadUrl("http://baidu.com");
        //����WebViewĬ��ʹ�õ�������ϵͳĬ�����������ҳ����Ϊ��ʹ��ҳ��WebView��
       webView.setWebViewClient(new WebViewClient(){
           @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
               //����ֵ��true��ʱ�����ȥWebView�򿪣�Ϊfalse����ϵͳ�����������������
             view.loadUrl(url);
            return true;
        }
       });
    }
	// ʹ��ǰActivity���ؼ���Ч
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (KeyEvent.KEYCODE_BACK == keyCode) {
//			return false;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
	}
