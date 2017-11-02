package com.bqt.aidl2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.bqt.aidl.Book;
import com.bqt.aidl.BookManager;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {
	
	private List<Book> mBooks;
	
	@Override
	public void onCreate() {
		mBooks = new ArrayList<>();
		Book book = new Book();
		book.setName("你妹");
		book.setPrice(28);
		mBooks.add(book);
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.i("bqt", "onBind");
		return new MyBind();
	}
	
	private class MyBind extends BookManager.Stub {
		@Override
		public List<Book> getBooks() throws RemoteException {
			synchronized (this) {
				Log.i("bqt", "【getBooks】" + mBooks.toString());
				return mBooks;
			}
		}
		
		@Override
		public Book addBookIn(Book book) throws RemoteException {
			synchronized (this) {
				if (book == null) book = new Book();
				
				//尝试修改book的参数，主要是为了观察其到客户端的反馈
				book.setPrice(1111);
				if (!mBooks.contains(book)) mBooks.add(book);
				
				//打印mBooks列表，观察客户端传过来的值
				Log.i("bqt", "【addBookIn】" + mBooks.toString());
				return book;
			}
		}
		
		@Override
		public Book addBookOut(Book book) throws RemoteException {
			synchronized (this) {
				if (book == null) book = new Book();
				book.setPrice(2222);
				if (!mBooks.contains(book)) mBooks.add(book);
				Log.i("bqt", "【addBookOut】 " + mBooks.toString());
				return book;
			}
		}
		
		@Override
		public Book addBookInout(Book book) throws RemoteException {
			synchronized (this) {
				if (book == null) book = new Book();
				book.setPrice(3333);
				if (!mBooks.contains(book)) mBooks.add(book);
				Log.i("bqt", "【addBookInout】" + mBooks.toString());
				return book;
			}
		}
	}
}