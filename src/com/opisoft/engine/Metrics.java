package com.opisoft.engine;

import android.app.Activity;
import android.graphics.Point;

public class Metrics {
	private Integer _cellWidth;
	private Integer _cellHeight;
	
	public static final Metrics self = new Metrics();
		
	protected Metrics() {
	}
	
	public void init() {
		
	}
	
	public Integer cellWidth() {
		return _cellWidth;
	}
	
	public Integer cellHeight() {
		return _cellHeight;
	}
	
	public Point measure(Point pt) {
		return new Point(pt.x*cellWidth(),pt.y*cellHeight());
	}
}
