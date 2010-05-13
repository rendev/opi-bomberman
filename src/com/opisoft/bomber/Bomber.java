package com.opisoft.bomber;

import android.os.Bundle;
import com.opisoft.engine.GameEngine;
import com.stickycoding.Rokon.RokonActivity;

public class Bomber extends RokonActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      	createEngine("pisikak.png", 480, 320, true);
    	rokon.setBackgroundColor(1, 0, 0);
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
	public void onRestart() {
		//The engine will automatically pause time when it is hidden, so if you aren't handling this yourself you need to automatically unpause
		//It seems that some(?) devices are restarting the examples from the beginning, because the OpenGL activities aren't the main Activity
		rokon.unpause();
	}
}