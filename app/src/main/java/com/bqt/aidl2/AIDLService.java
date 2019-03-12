package com.bqt.aidl2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.bqt.aidl.Book;
import com.bqt.aidl.BookManager;

public class AIDLService extends Service {
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.i("bqt", "【Service-onBind】");
		getApplication();
		return new MyBind();
	}
	
	private class MyBind extends BookManager.Stub {
		
		@Override
		public synchronized Book addBookIn(Book book) throws RemoteException {
			modifyBook(book, 100, "Service-In");
			return book;
		}
		
		@Override
		public synchronized Book addBookOut(Book book) throws RemoteException {
			modifyBook(book, 200, "Service-Out");
			return book;
		}
		
		@Override
		public synchronized Book addBookInout(Book book) throws RemoteException {
			modifyBook(book, 300, "Service-Inout");
			return book;
		}
		
		private void modifyBook(Book book, int i, String s) {
			if (book != null) {
				Log.i("bqt", "【Service-接收到的Book】" + book);
				book.setPrice(i);//修改book，观察客户端的反馈
				book.setName(s);
				Log.i("bqt", "【Service-返回的Book】" + book);
			}
		}
	}
}