package com.example.android_red;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.android_red.intenter.AsyncTaskManager;
import com.android_red.intenter.CustomProgressDialog;
import com.android_red.intenter.AsyncTaskSoap.OnDataRecvListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PowerSuccActivity extends Activity {

	AsyncTaskManager atm = new AsyncTaskManager(); // �첽ͨ�Ź����� �����������������ʱʹ�ã�
	// Handler��AsyncTask������Ϊ�˲��������̣߳�UI�̣߳�����UI�ĸ���ֻ�������߳�����ɣ�����첽�����ǲ��ɱ���
	// ProcessDlgAction pda = new ProcessDlgAction(); // ���ȶԻ��򣨼��أ�
	CustomProgressDialog pda;
	Button btnpower, btnfanhui, btnpowersucc;
	TextView txqqlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.powersucc);
		pda =new CustomProgressDialog(this, "",R.anim.frame);   //���ض���
		pda.show();
		atm.startAsyncTask("qqlist", "imei=" + PowerActivity.imei, odrLsn, 1);
		btnfanhui = (Button) findViewById(R.id.fanhui);
		btnfanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ����
				Intent in = getIntent();
				setResult(RESULT_OK, in);
				finish();
			}
		});
	}

	OnDataRecvListener odrLsn = new OnDataRecvListener() {
		@Override
		public void onDataRecv(JSONObject jsonObj, int code) {
			pda.dismiss();
			if(null==jsonObj){   //���粻��ʱ����ֳ���������������ȡ����ret
				Toast.makeText(PowerSuccActivity.this, "��������", Toast.LENGTH_LONG).show();
			}else{
			try {
				int ret = jsonObj.getInt("ret");
				if (0 == ret) {
					List list = new ArrayList();
					String qqlist = "\n";
					JSONArray arr = jsonObj.getJSONArray("list");
					for(int i=0;i<arr.length();i++){
						list.add(arr.get(i));
						qqlist = qqlist+"�Ѿ���Ȩqq:"+arr.get(i).toString()+"\n";
					}
					txqqlist = (TextView) findViewById(R.id.qqlist);
					txqqlist.setText(qqlist);
				}else{
					Toast.makeText(PowerSuccActivity.this, "��ǰ�ֻ�û�о�����Ȩ��", Toast.LENGTH_LONG)
					.show();
					Intent intent = new Intent(PowerSuccActivity.this,
							PowerActivity.class);
					startActivity(intent);
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "�����������쳣��",
						Toast.LENGTH_LONG).show();
			  }
			}
		}
	};
	
	// ʹ��ǰActivity���ؼ���Ч
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
