package com.android_red.intenter;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.os.AsyncTask;

//�첽����HTTP����ͨ��
/*
 * AsyncTask��Handler��������һЩ�������ڼ򵥵��첽����
 * AsyncTask����һ����װ���ĺ�̨�����࣬�����첽����
 * AsyncTask���������ַ������� Params��Progress��Result��
Params ��������ִ�е��������������HTTP�����URL��
Progress ��̨����ִ�еİٷֱȡ�
Result ��ִ̨���������շ��صĽ��������String��
 */
public class AsyncTaskSoap extends AsyncTask<String, Void, JSONObject> {
	
	public interface OnDataRecvListener {
		void onDataRecv(JSONObject jsonObj, int iCode);
	}

	private OnDataRecvListener oLsner = null;
	private int iRequestCode = 0;

	// login list add preAdd
	// method + "?" + param = url
	public void startAsyncTask(String method, String param,
			OnDataRecvListener lsner, int iCode) {
		oLsner = lsner;
		iRequestCode = iCode;
		// ...����ɱ�����������ж������
		execute(method, param);
	}

	public void cancelAsyncTask(boolean b) {
		oLsner = null;
		super.cancel(b);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		JSONObject json = null;
		String method = params[0];
		String param = params[1];
		// �ӳټ���
		try {
		   Thread.sleep(0);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);// ����ʱ��
		HttpPost request = new HttpPost(Const.WEB_PATH + method + "?" + param);

		try {
			// �������󣬵õ���Ӧ
			HttpResponse response = client.execute(request);

			// ����ɹ�
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String receive = EntityUtils.toString(response.getEntity());
				json = new JSONObject(receive);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		if (oLsner != null) {
			oLsner.onDataRecv(result, iRequestCode);
			oLsner = null;
		}
	}

}
