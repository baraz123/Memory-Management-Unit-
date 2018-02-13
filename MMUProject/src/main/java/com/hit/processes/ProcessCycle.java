package com.hit.processes;

public class ProcessCycle extends java.lang.Object {
	
	private int sleepMs;
	private java.util.List<java.lang.Long> pages;
	private java.util.List<byte[]> data;
	
	public ProcessCycle(java.util.List<java.lang.Long> pages, int sleepMs, java.util.List<byte[]> data) {
		setSleepMs(sleepMs);
		setPages(pages);
		setData(data);

		//System.out.println(toString());
	}
	
	public java.util.List<java.lang.Long> getPages() {
		return pages;
		
	}
	
	public void setPages(java.util.List<java.lang.Long> pages) {
		this.pages = pages;
	}
	
	public int getSleepMs() {
		return sleepMs;
		
	}

	public void setSleepMs(int sleepMs) {
		this.sleepMs = sleepMs;
	}
	
	public java.util.List<byte[]> getData() {
		return data;	
	}
	
	public void setData(java.util.List<byte[]> data) {
		this.data = data;
	}
	
	@Override
	public java.lang.String toString() {
		return "ProcessCycle [pages=" + pages + ", sleepMs=" + sleepMs + ", data=" + data + "]";	
	}
}
