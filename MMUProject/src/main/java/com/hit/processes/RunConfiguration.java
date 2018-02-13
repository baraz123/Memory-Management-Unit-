package com.hit.processes;

import java.util.List;

public class RunConfiguration extends java.lang.Object {

	private List<ProcessCycles> processesCycles;

	public RunConfiguration(java.util.List<ProcessCycles> processesCycles) {

		setProcessesCycles(processesCycles);
	}

	public java.util.List<ProcessCycles> getProcessesCycles() {

		return processesCycles;
	}

	public void setProcessesCycles(java.util.List<ProcessCycles> processesCycles) {
		System.out.println("uygyiybbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		this.processesCycles = processesCycles;
	}

	@Override
	public java.lang.String toString() {
		return "processCycles [processCycles=" + processesCycles + "]";
	}

}
