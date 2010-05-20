package com.opisoft.engine;

import android.os.Handler;
import android.os.Looper;

public class LooperThread extends Thread {
	private Class<? extends Handler> _handlerClass;
    private Handler _handler;
    
    public LooperThread(Class<? extends Handler> handlerClass) {
    	_handlerClass = handlerClass;
    }
    
    public Handler handler() {
    	return _handler;
    }
    
    public void run() {
        Looper.prepare();                
        try {
        	_handler = _handlerClass.newInstance(); //handler will communicate with thread's looper itself
	        Looper.loop();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} 
    }
}
