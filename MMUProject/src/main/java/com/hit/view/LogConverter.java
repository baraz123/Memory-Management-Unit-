package com.hit.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.hit.util.MMULogger;

public class LogConverter {
	private static LogConverter instance = null;
	private static int numOfProcesses;
	private static int Ramcapacity;
	private static int clicksData = 0;// change
	private static int clicksPages = 0;// change
	private static int clicksLog = 1;// change
	private static int clicksProcesses = 0;
	private static int allLineSize;
	private static ArrayList<Integer> pageWhenGP;
	private static ArrayList<Integer> ProcessNum;
	private static ArrayList<String> allTheLines;
	private static List<Integer> PFcounter;
	private static List<Integer> PRcounter;
	private static List<Integer> data;
	private static List<List<Integer>> AllLines;
	
	private String file;
	private static ArrayList<String> allLines;
	private static int GPcounter;
	private static TreeSet<Integer> pageIds; // O(Logn)
	

	public LogConverter() {
		file = MMULogger.getInstance().getLogFile();
		allLines = new ArrayList<String>();
		
		FileReader fileR = null;
		BufferedReader bufferR = null;
		String Line = null;
		String partOfLine = null;
			try {
				fileR = new FileReader(file);
				bufferR = new BufferedReader(fileR);
				
				Line = bufferR.readLine();
				if(Line == null) 
					throw new IOException();
				
				//allLines.add(Line); // Add line to list
				partOfLine = Line.substring(3, Line.length());
				setRamcapacity(Integer.parseInt(partOfLine)); // Ram Capacity!
				
				Line = bufferR.readLine();
				//allLines.add(Line); // Add line to list
				partOfLine = Line.substring(4, Line.length());
				setNumOfProcesses(Integer.parseInt(partOfLine)); // Number of processes
				
				GPcounter = 0;
				pageIds = new TreeSet<Integer>();
				while ((Line = bufferR.readLine()) != null) {
					allLines.add(Line);
					partOfLine = Line.substring(0, 2);
					switch (partOfLine) {

					case "PF":
						partOfLine = Line.substring(3, Line.length());
						pageIds.add(Integer.parseInt(partOfLine));
						//allTheLines.add("PF");
						break;
					case "PR":
						if (Line.charAt(13) == ' ')
							pageIds.add(Integer.parseInt(partOfLine = Line.substring(14, Line.length())));
						else
							pageIds.add(Integer.parseInt(partOfLine = Line.substring(15, Line.length())));
						//allTheLines.add("PR");
						break;
					case "GP":
						GpData(Line);
						//allTheLines.add("GP");
						break;

					}
					
					/*if(partOfLine.equals("GP")) {
						GPcounter++;
						System.out.println(GPcounter);
					}*/
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// TODO Auto-generated catch block
				if (bufferR != null)
					try {
						bufferR.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				if (fileR != null)
					try {
						fileR.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
	}

	public static LogConverter getInstance() {
		if (instance == null) {
			instance = new LogConverter();
		}

		return instance;
	}
	
	public void generateAllLines() {
		allLines = new ArrayList<String>();
		
		FileReader fileR = null;
		BufferedReader bufferR = null;
		String Line = null;
		String partOfLine = null;
			try {
				fileR = new FileReader(file);
				bufferR = new BufferedReader(fileR);
				
				Line = bufferR.readLine();
				allLines.add(Line); // Add line to list
				partOfLine = Line.substring(3, Line.length());
				setRamcapacity(Integer.parseInt(partOfLine)); // Ram Capacity!
				
				Line = bufferR.readLine();
				allLines.add(Line); // Add line to list
				partOfLine = Line.substring(4, Line.length());
				setNumOfProcesses(Integer.parseInt(partOfLine)); // Number of processes
				
				while ((Line = bufferR.readLine()) != null) {
					allLines.add(Line);
					
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				// TODO Auto-generated catch block
				if (bufferR != null)
					try {
						bufferR.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				if (fileR != null)
					try {
						fileR.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		
	}

	/*public static void logData(String file) {

		String Line = null;
		String partOfLine = null;

		allTheLines = new ArrayList<>();
		PFcounter = new ArrayList<Integer>();
		PRcounter = new ArrayList<Integer>();

		FileReader fileR = null;
		BufferedReader bufferR = null;

		try {
			fileR = new FileReader(file);
			bufferR = new BufferedReader(fileR);
			
			Line = bufferR.readLine();
			if (Line == null) {
				throw new IOException();
			} else {
				partOfLine = Line.substring(3, Line.length());
				Ramcapacity = Integer.parseInt(partOfLine);// Ram Capacity!
				allTheLines.add("RC");
				System.out.println(Ramcapacity);
				Line = bufferR.readLine();
				partOfLine = Line.substring(4, Line.length());
				setNumOfProcesses(Integer.parseInt(partOfLine));// Number of processes
				allTheLines.add("PN");
				Line = bufferR.readLine();
				partOfLine = Line.substring(0, 2);
				AllLines = new ArrayList<List<Integer>>(45);
				pageWhenGP = new ArrayList<>();
				ProcessNum = new ArrayList<>();
				while ((Line = bufferR.readLine()) != null) {
					partOfLine = Line.substring(0, 2);
					switch (partOfLine) {

					case "PF":
						partOfLine = Line.substring(3, Line.length());
						PFcounter.add(Integer.parseInt(partOfLine));
						allTheLines.add("PF");
						break;
					case "PR":
						if (Line.charAt(13) == ' ')
							PRcounter.add(Integer.parseInt(partOfLine = Line.substring(14, Line.length())));
						else
							PRcounter.add(Integer.parseInt(partOfLine = Line.substring(15, Line.length())));
						allTheLines.add("PR");
						break;
					case "GP":
						GpData(Line);
						allTheLines.add("GP");
						break;

					}
				}

			}
			System.out.println(allTheLines);
			allLineSize = allTheLines.size();
		} catch (FileNotFoundException f) {
			f.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (bufferR != null)
					bufferR.close();
				if (fileR != null)
					fileR.close();
			} catch (IOException ex) {
				ex.printStackTrace();

			}
		}
	}*/

	public static void GpData(String logLine) {

		List<String> dataAsString;
		data = new ArrayList<Integer>();
		int startingData;
		String DataPartLine;
		String ProcesspartLine;
		String PagePartLine;
		if (logLine.charAt(6) == ' ') {
			ProcesspartLine = logLine.substring(5, 6);// Process number between 0-9
			if (logLine.charAt(8) == ' ') {
				PagePartLine = logLine.substring(7, 8);// Page number between 0-9
				startingData = 10;
			} else {
				PagePartLine = logLine.substring(7, 9);// Page number between 10-99
				startingData = 11;
			}
		} else {
			ProcesspartLine = logLine.substring(5, 7);// if Process number between 10-99
			if (logLine.charAt(9) == ' ') {
				PagePartLine = logLine.substring(8, 9);// Page number between 0-9
				startingData = 11;
			} else {
				PagePartLine = logLine.substring(8, 10);// Page number between 10-99
				startingData = 12;
			}
		}

		//ProcessNum.add(Integer.parseInt(ProcesspartLine));

		//DataPartLine = logLine.substring(startingData, logLine.length() - 1);
		//dataAsString = Arrays.asList(DataPartLine.split(", "));

		//data = dataAsString.stream().map(Integer::parseInt).collect(Collectors.toList());
		//AllLines.add(data);
		pageIds.add(Integer.parseInt(PagePartLine));

	}
	
	public int getNumOfProcesses() {
		return numOfProcesses;
	}
	
	public static void setNumOfProcesses(int number) {
		numOfProcesses = number;
	}

	public int getRamcapacity() {
		return Ramcapacity;
	}
	
	public static void setRamcapacity(int number) {
		Ramcapacity = number;

		System.out.println(Ramcapacity);
	}
	
	public String getLine(int index) {
		return allLines.get(index);
	}
	
	public int getSizeOfLines() {
		return allLines.size();
	}
	
	public int getPageIdsSize() {
		return pageIds.size();
	}
	
	
	public Map<String, String> lineBreaker(int lineNumber) {
		String Line = getLine(lineNumber);
		String partOfLine = Line.substring(0, 2);
		Map<String, String> map = new HashMap<String, String>();
		String pageId;
		
		map.put("line", Line);
		switch (partOfLine) {
		
		case "PF":
			pageId = Line.substring(3, Line.length());
			map.put("action", "PF");
			map.put("pageId", pageId);
			break;
		case "PR":
			String str = Line.replaceAll("[^0-9]+", " ");
			String[] parts = str.split(" ");

			map.put("action", "PR");
			map.put("MTH", parts[1]);
			map.put("MTR", parts[2]);
			
			break;
		case "GP":
			int startingData;
			String DataPartLine;
			String ProcesspartLine;
			String PagePartLine;
			
			if (Line.charAt(6) == ' ') {
				ProcesspartLine = Line.substring(5, 6);// Process number between 0-9
				if (Line.charAt(8) == ' ') {
					//PagePartLine = Line.substring(7, 8);// Page number between 0-9
					PagePartLine = Line.substring(7, 8);
					startingData = 10;
				} else {
					//PagePartLine = Line.substring(7, 9);// Page number between 10-99
					PagePartLine = Line.substring(7, 9);
					startingData = 11;
				}
			} else {
				ProcesspartLine = Line.substring(5, 7);// if Process number between 10-99
				if (Line.charAt(9) == ' ') {
					//PagePartLine = Line.substring(8, 9);// Page number between 0-9
					PagePartLine = Line.substring(8, 9);
					startingData = 11;
				} else {
					//PagePartLine = Line.substring(8, 10);// Page number between 10-99
					PagePartLine = Line.substring(8, 10);
					startingData = 12;
				}
			}
			
			map.put("action", "GP");
			map.put("process", ProcesspartLine);
			map.put("pageId", PagePartLine);
			DataPartLine = Line.substring(Line.indexOf("[") + 1, Line.indexOf("]"));
			map.put("data", DataPartLine);
			//DataPartLine = Line.substring(startingData, Line.length() - 1);
			//ProcessNum.add(Integer.parseInt(ProcesspartLine));

			//DataPartLine = logLine.substring(startingData, logLine.length() - 1);
			//dataAsString = Arrays.asList(DataPartLine.split(", "));

			//data = dataAsString.stream().map(Integer::parseInt).collect(Collectors.toList());
			//AllLines.add(data);
			break;

		}
		
		return map;
	}
	
	
	

	public static List<Integer> getData() {
		return AllLines.get(clicksData);

	}

	public static List<Integer> updateData() {
		return AllLines.get(clicksData++);

	}

	public String getAllTheLines() {
		return allTheLines.get(clicksLog);
	}

	public String updateAllTheLines() {
		return allTheLines.get(++clicksLog);
	}

	public Integer getPageWhenGP() {
		return pageWhenGP.get(clicksPages);
	}

	public Integer updatePageWhenGP() {
		return pageWhenGP.get(clicksPages++);
	}

	

	public Integer getProcessNum() {
		return ProcessNum.get(clicksProcesses);
	}

	public Integer updateProcessNum() {
		return ProcessNum.get(clicksProcesses++);
	}

	public int getAllLineSize() {
		return allLineSize - 2;
	}

}