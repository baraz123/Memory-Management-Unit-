package com.hit.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.NFUAlgoCacheImpl;
import com.hit.algorithm.Random;
import com.hit.driver.CLI;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycle;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.util.MMULogger;

public class MMUModel extends java.util.Observable implements Model {
	private java.lang.String[] command;
	final static String CONFIG_FILE_NAME = "src/main/resources/com/hit/config/Configuration.json";// Json file that has all the data
	private static MMULogger logger;
	
	public MMUModel() {
		
	}
	


	@Override
	public  void start() {
		// command[0]=start, command[1]=LRU,NFU,Random, Command[2]=Capacity size
		IAlgoCache<Long, Long> algo = null;
		System.out.println("hi");
		/**
		 * Initialize capacity and algo
		 */

		String startOrstop = command[0];
		String algoName = command[1];// setting the cache algorithm that we going to use
		Integer ramCapacity = Integer.parseInt(command[2]);// command[2]=capacity size he is a String so we convert him to an


		if (startOrstop == CLI.START) {
			switch (algoName) {// setting the cache algorithm with the capacity
			case CLI.LRU:
				algo = new LRUAlgoCacheImpl<>(ramCapacity);
				break;

			case CLI.NFU:
				algo = new NFUAlgoCacheImpl<>(ramCapacity);
				break;

			case CLI.RANDOM:
				algo = new Random<>(ramCapacity);
				break;
			}
		}

		/**
		 * Build MMU and initial RAM (content)
		 */
		MemoryManagementUnit mmu = new MemoryManagementUnit(ramCapacity, algo);
		// define MMU and cache algorithm that will manage the paging.
		RunConfiguration runConfig = readConfigurationFile();
		// Insert to a list at runConfig constructor the data that located at Json file
		List<ProcessCycles> processCycles = runConfig.getProcessesCycles();

		List<Process> processes = createProcesses(processCycles, mmu);
		
		runProcesses(processes);
		mmu.shutdown();
		
		setChanged();
		notifyObservers();
	}

	
	public static void runProcesses(java.util.List<Process> applications) {
		MMULogger.getInstance().write("PN: " + applications.size(), Level.INFO);
		
		ExecutorService executor = Executors.newCachedThreadPool();// Pool of threads
		List<Future<Boolean>> futures = new ArrayList<>();

System.out.println("hi");
		for (Process process : applications) {
			// System.out.println("Processes number "+process.getId()+" is starting");
			futures.add(executor.submit(process));
		}

		for (Future<Boolean> future : futures) {
			try {

				System.out.println(future.get()+" Process is now done");
				
			} catch (InterruptedException | ExecutionException e) {
				logger.write("Process isn't done yet", Level.SEVERE,e);
				e.printStackTrace();
			}
		}

		executor.shutdown();
	}

	public static java.util.List<Process> createProcesses(java.util.List<ProcessCycles> appliocationsScenarios,
			com.hit.memoryunits.MemoryManagementUnit mmu) {
		List<Process> processesList = new ArrayList<Process>();

		int i = 0;
		for (ProcessCycles p : appliocationsScenarios) {
			new ProcessCycles(p.getProcessCycles());

			for (ProcessCycle c : p.getProcessCycles()) {
				new ProcessCycle(c.getPages(), c.getSleepMs(), c.getData());
			}

			processesList.add(new Process(i, mmu, appliocationsScenarios.get(i)));
			i++;
		}
		
		return processesList;
	}

	public static RunConfiguration readConfigurationFile() {
		RunConfiguration runConfig = null;

		try {
			runConfig = new Gson().fromJson(new JsonReader(new FileReader(CONFIG_FILE_NAME)), RunConfiguration.class);
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			logger.write("Could not open RunConfiguration from config file",Level.SEVERE,e);
			e.printStackTrace();
		}

		return runConfig;
	}
	public void setConfiguration(java.util.List<java.lang.String> configuration){
		this.command = configuration.toArray(new String[configuration.size()]);
		
	}

	
}
