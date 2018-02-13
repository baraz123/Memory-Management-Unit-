package com.hit.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MMULogger extends java.lang.Object {

	public static final String DEFAULT_FILE_NAME = "logs/log.txt";
	private FileHandler handler;
	private static MMULogger instance;
	
	private MMULogger(){
		try {
			handler = new FileHandler(DEFAULT_FILE_NAME);
			handler.setFormatter(new OnlyMessageFormatter());
			handler.setLevel(Level.ALL);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

	public String getLogFile() {
		return DEFAULT_FILE_NAME;
	}



	public static MMULogger getInstance()
	{
		if (instance==null)
		{
			instance = new MMULogger();
		}
		return instance;
	}

	public synchronized void write(String command, Level level) {
		command+="\r\n";
		LogRecord Line= new LogRecord(level, command);
		handler.publish(Line);
		handler.flush();
	}
	
	public synchronized void write(String command, Level level ,Exception exp) {

		if (exp!=null)
			command = command + exp.getMessage() + "\r\n" ; 
		LogRecord Line = new LogRecord(level,command);
		handler.publish(Line);
		handler.flush();
	}

	public class OnlyMessageFormatter extends Formatter {
		OnlyMessageFormatter() {
			super();
		}

		@Override
		public String format(LogRecord record) {
			// TODO Auto-generated method stub
			return record.getMessage();
		}
	}

}
