package com.hit.processes;

import java.util.List;

public class ProcessCycles extends java.lang.Object {

	private List<ProcessCycle> processCycles;

	public ProcessCycles(java.util.List<ProcessCycle> processCycles) {
		setProcessCycles(processCycles);
		// System.out.println(toString());
	}

	public java.util.List<ProcessCycle> getProcessCycles() {
		return processCycles;

	}

	public void setProcessCycles(java.util.List<ProcessCycle> processCycles) {
		this.processCycles = processCycles;
	}

	@Override
	public java.lang.String toString() {
		return "ProcessCycles [processCycles=" + this.processCycles + "]";

	}
}
