package com.hit.driver;

import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;

import com.hit.controller.MMUController;
import com.hit.memoryunits.HardDisk;
import com.hit.model.MMUModel;
import com.hit.view.View;

public class CLI extends java.util.Observable implements java.lang.Runnable, View {

	public static final java.lang.String START = "start";
	public static final java.lang.String STOP = "stop";
	public static final java.lang.String LRU = "LRU";
	public static final java.lang.String NFU = "NFU";
	public static final java.lang.String RANDOM = "RANDOM";

	private Scanner input;
	private PrintWriter output;
	private String startOrstop;

	public CLI(java.io.InputStream in, java.io.OutputStream out) {
		input = new Scanner(in);
		output = new PrintWriter(out);
	}

	@Override
	public void run() {

		boolean flag = false; // check start/stop command
		// String startOrstop = null;
		String []command = new String[3];
		// In this string will be command[0]=start/stop, command[1]=LRU,NFU,Random,
		// Command[2]=Capacity size
		HardDisk HD = HardDisk.getInstance();

		write("Please enter start or stop: ");// goes to write function and cleaning the buffer
		while (flag == false) {

			String startOrstop = input.nextLine().toLowerCase();
			// making the words as a lower case words according to the define above.
			while (!startOrstop.equals(START) && !startOrstop.equals(STOP)) {// If it's not start/stop plz try again
				write("Please enter again start or stop without any extensions");
				startOrstop = input.nextLine().toLowerCase();
			}

			switch (startOrstop) {
			case START:
				write("Please enter required algorithm and RAM capacity");
				// The client needs to write here LRU/NFU/Random then " " and then the capacity
				// size
				String algoAndCapacity = input.nextLine().toUpperCase();
				String[] parts = algoAndCapacity.split(" ");

				while ((!parts[0].equals(LRU) && !parts[0].equals(NFU) && !parts[0].equals(RANDOM))
						||parts.length != 2  || !algoAndCapacity.contains(" ")
						|| Integer.parseInt(parts[1]) > HD.getSize()) {
					// If the client didn't write 
					System.out.println("fdfss");
					write("Not a valid command");
					algoAndCapacity = input.nextLine().toUpperCase();
					parts = algoAndCapacity.split(" ");// split the String to array

				}
				
				command[0] = CLI.START;
				command[1] = parts[0]; // algo name
				command[2] = parts[1]; // capacity
				//MMUController.update(command);
				setChanged();
				notifyObservers(command);
				break;

			case STOP:
				write("Thank you!");
				input.close();
				flag = true;
				break;

			}

		}

	}

	public void write(java.lang.String string) {
		System.out.println(string);
		//output.write(string+"/r/n")//writing a string and move to the next line
		output.flush();
	}

	@Override
	public void start() {
		run();
		// TODO Auto-generated method stub
		
	}
	
	public void setStartOrstop(String action){
		this.startOrstop = action;
		
	}

}
