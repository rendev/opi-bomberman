package com.opisoft.engine;

import android.graphics.Point;

public class Metrics {
	private int _cellWidth;
	private int _cellHeight;
	
	public Metrics() {
		init();
	}
	
	public void init() {
		_cellWidth = _cellHeight = 32;
	}
	
	public int cellWidth() {
		return _cellWidth;
	}
	
	public int cellHeight() {
		return _cellHeight;
	}
	
	public Point measure(Point ptInCells) {
		return new Point(ptInCells.x*cellWidth(),ptInCells.y*cellHeight());
	}
}
