package com.hit.processes;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;
import com.hit.util.MMULogger;

import javafx.scene.chart.PieChart.Data;

public class Process extends java.lang.Object implements java.util.concurrent.Callable<java.lang.Boolean> {

	private int ProcessId;
	private MemoryManagementUnit mmu;
	private List<ProcessCycle> processCycles;

	public Process(int id, MemoryManagementUnit mmu, ProcessCycles processCycles) {
		setId(id);// an ID of a process
		this.mmu = mmu;
		this.processCycles = processCycles.getProcessCycles();
		// System.out.println("Process constructor - "+getId());
	}

	public int getId() {
		return ProcessId;
	}

	public void setId(int id) {
		this.ProcessId = id;
	}

	public java.lang.Boolean call() throws java.lang.Exception {

		System.out.println("Process number - " + getId() + " is starting now");// Process is begin
		
		Page<byte[]>[] pages;
		for(ProcessCycle Cycle : processCycles) {
			System.out.println(getId() + " Is starting Cycle! this is the pages in the current Cycle: "+ Cycle.getPages().toString());

			boolean[] writePages = new boolean[Cycle.getPages().size()];
			for(int i = 0; i < Cycle.getPages().size(); i++) {
				if(Cycle.getData().get(i).length > 0) {
					writePages[i] = true;
				} else {
					writePages[i] = false;
					System.out.println("this blabla");
				}
			}
			
			pages = mmu.getPages(Cycle.getPages().toArray(new Long[Cycle.getPages().size()]), writePages);
			
			synchronized(pages)
			{
				for(int i = 0; i < pages.length; i++) {
					MMULogger.getInstance().write("GP: P" + getId() + " " + pages[i].getPageId() + " " + Arrays.toString(Cycle.getData().get(i)), Level.INFO);
					if(writePages[i] == true) {
						pages[i].setContent(Cycle.getData().get(i));
					} 
				}
			}

			System.out.println(getId() + " Is going to sleep for -" + Cycle.getSleepMs() + "MS");
			Thread.sleep(Cycle.getSleepMs());
		}

		return true;
	}


}
