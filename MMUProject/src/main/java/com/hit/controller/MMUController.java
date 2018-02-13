package com.hit.controller;

import java.util.Arrays;
import java.util.List;

import com.hit.driver.CLI;
import com.hit.model.MMUModel;
import com.hit.model.Model;
import com.hit.view.LogConverter;
import com.hit.view.View;

public class MMUController extends java.lang.Object implements Controller, java.util.Observer {
	private Model model;
	private View view;
	
	

	public MMUController(Model model, View view) {
		super();
		this.model = model;
		this.view = view;

	}

	@Override
	public void update(java.util.Observable o,Object arg) {
		System.out.println(o);
		if (o instanceof CLI) {//CLI finished and he sent the command String.
			List<String> configurations = Arrays.asList((String[])arg);
			((MMUModel)model).setConfiguration(configurations);
			
			model.start();
			
		}
		else if (o instanceof MMUModel)//MMUmodel finish the paging and now we can read from the log file
		{
			
			LogConverter.getInstance();
			view.start();
		}
		else if(o == view) {
			CLI cli = null;
			((CLI)cli).setStartOrstop("stop");
			System.out.println("yes!!");
		}

	}



}