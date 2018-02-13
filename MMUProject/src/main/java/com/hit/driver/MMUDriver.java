package com.hit.driver;

import com.hit.controller.MMUController;
import com.hit.model.MMUModel;
import com.hit.view.MMUView;

public class MMUDriver extends java.lang.Object {


	public static void main(java.lang.String[] args) {
		/**
		 * Build MVC model to demonstrate MMU system actions
		 */
		//RunConfiguration configuration = readConfigurationFile();
		CLI cli = new CLI(System.in, System.out);// Creating a new CLI with input and output abilities
		MMUModel model = new MMUModel();
		MMUView view = new MMUView();
		MMUController controller = new MMUController(model, view);// Controller Implement Observer.
		model.addObserver(controller);
		cli.addObserver(controller);
		view.addObserver(controller);
		new Thread(cli).start();// Starting CLI as a Thread (starting the run method at CLI)
	}



}
