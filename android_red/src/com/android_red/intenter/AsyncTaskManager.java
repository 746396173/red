package com.android_red.intenter;


public class AsyncTaskManager {
	// AsyncTaskSoap������һ���Ե�
	private AsyncTaskSoap ats = null;

	public void startAsyncTask(String method, String param,
			AsyncTaskSoap.OnDataRecvListener lsn, int iCode) {
		if (ats != null) {
			ats.cancelAsyncTask(true);
		}
		ats = new AsyncTaskSoap();
		ats.startAsyncTask(method, param, lsn, iCode);
	}

	public void cancelAsyncTask() {
		if (ats != null) {
			ats.cancelAsyncTask(true);
			ats = null;
		}
	}

}
