package com.hit.view;

public class MMUView extends java.util.Observable implements View {
		
	public MMUView() {
		//start();// TODO Auto-generated constructor stub
	}

	@Override
	public void start() {
			/**
			 * Launch the application.
			 */
			try {
				new GUI();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}

