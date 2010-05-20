package com.opisoft.bomber;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.opisoft.engine.Entity;
import com.opisoft.engine.GameEngine;
import com.opisoft.engine.commands.MoveCommand;
import com.opisoft.engine.components.Move;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Handlers.InputHandler;

public class Bomber extends RokonActivity {
	private static boolean _firstLaunch = true;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
//        if(!_firstLaunch)
//    		return;
//        _firstLaunch = false;
      	createEngine("loading.png", 480, 320, true);
      	rokon.setInputHandler(touchHandler);
    	rokon.setBackgroundColor(1, 0, 0);
    	rokon.fps(true);
    }
    
    @Override
    public void onLoad() {
		try {
	        GameEngine.self.loadMap("maps/testmap");	        
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Override
	public void onLoadComplete() { 
	}
    
    @Override
    public void onPause() {
    	super.onPause();    	
    }
    
//	@Override
//	public void onResume() {
//		super.onResume();
//		if (rokon != null) {
//			rokon.unpause();
//		}
//	}
	
	@Override
	public void onRestart() {
		super.onRestart();

		if (rokon != null) {
			rokon.unpause();
	    	rokon.freeze();
	    	rokon.onResume();
	    	rokon.unpause();
	    	rokon.unfreeze();
		}
	}
	
	@Override
	public void onGameLoop() {

	} 
	
	  public InputHandler touchHandler = new InputHandler() {
	    	public void onTouchEvent(MotionEvent event, boolean hotspot) {
	    		
	    	}
	  };
	  
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int dx=0,dy=0;
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
			dx = -1;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			dx = 1;
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			dy = 1;
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			dy = -1;
			break;
		}
		
		if (dx != 0 || dy != 0) {//move action
			Entity bomber = GameEngine.self.getEntity("bomber");
			
			if (bomber != null) {
				MoveCommand move = new MoveCommand(dx, dy, 0);
				move.setEntity(bomber);
				GameEngine.self.process(move);
			}
		}
		return true;
	}
}