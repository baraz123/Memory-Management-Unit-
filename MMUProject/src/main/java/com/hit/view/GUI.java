package com.hit.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class GUI  {

	private Shell shell;
	private Display display;

	private Table RamTable;
	private TableColumn[] RamTablecolumn;
	private TableItem[] RamTableitem;
	
	private TableItem[] ProcessesItems;
	//private static String Line;
	private Text txtCapacity;
	private Text txtPageFault;
	private Text txtPageReplacement;
	private Table ProcessesTable;
	private Text txtLine;
	private Group grpCurrentProcess;
	
	private Button btnPlay;
	private Button btnPlayAll;

	private int ramCapacity;
	private int numOfProcesses;
	private int sizeOfLines;
	private int GPcounter;
	private LogConverter Logger;
	
	private int playCounter;
	private static int PF;
	private static int PR;

	public GUI() {
		Logger = LogConverter.getInstance();
		ramCapacity = Logger.getRamcapacity();
		numOfProcesses = Logger.getNumOfProcesses();
		sizeOfLines = Logger.getSizeOfLines();
		GPcounter = Logger.getPageIdsSize();
		playCounter = 0;
		PF = 0;
		PR = 0;
		open();
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GUI window = new GUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {

		display = Display.getDefault();

		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				System.out.println("Bye!!");
				System.exit(0);
			}
		});
		shell.setSize(1051, 593);
		shell.setText("MMU Simulator");
		
		GridLayout gl_shell = new GridLayout(2, false);
		gl_shell.marginHeight = 0;
		gl_shell.marginWidth = 0;
		gl_shell.verticalSpacing = 15;
		gl_shell.horizontalSpacing = 15;
		gl_shell.marginBottom = 15;
		gl_shell.marginTop = 15;
		gl_shell.marginRight = 15;
		gl_shell.marginLeft = 15;
		shell.setLayout(gl_shell);
		//shell.setBackgroundImage(new Image(display, "Matrix.png"));
		shell.setBackground(new Color(display, 77, 130, 203));

		/**
		 * Content layout
		 */
		Composite content = new Composite(shell, SWT.NONE);
		content.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));		
		GridData gd_content = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_content.widthHint = 711;
		content.setLayoutData(gd_content);
		GridLayout gl_content = new GridLayout(1, false);
		gl_content.horizontalSpacing = 15;
		gl_content.verticalSpacing = 15;
		content.setLayout(gl_content);
		
		/**
		 * Sidebar layout 
		 */
		Composite sidebar = new Composite(shell, SWT.NONE);
		sidebar.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		GridData gd_sidebar = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_sidebar.widthHint = 273;
		sidebar.setLayoutData(gd_sidebar);
		
		GridLayout gl_sidebar = new GridLayout(1, false);
		gl_sidebar.horizontalSpacing = 15;
		gl_sidebar.verticalSpacing = 15;
		sidebar.setLayout(gl_sidebar);
		

		/**
		 * Ram Section
		 */
		Color blue = new Color(display, 66, 103, 178);
		Group grpRamStatus = new Group(content, SWT.NONE);
		grpRamStatus.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		//grpRamStatus.setBackground(blue);
		grpRamStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		GridLayout gl_grpRamStatus = new GridLayout(1, false);
		grpRamStatus.setLayout(gl_grpRamStatus);
		grpRamStatus.setText("Ram Status");

		RamTable = new Table(grpRamStatus, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		RamTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		RamTable.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		RamTable.setHeaderVisible(true);
		RamTable.setLinesVisible(true);
		

		RamTablecolumn = new TableColumn[GPcounter];
		for (int i = 0; i < GPcounter; i++) {
			RamTablecolumn[i] = new TableColumn(RamTable, SWT.NONE);
			RamTablecolumn[i].setToolTipText("Page ID");
			RamTablecolumn[i].setText(" ");

		}

		RamTableitem = new TableItem[6];
		for (int i = 0; i < 5; i++) {
			RamTableitem[i] = new TableItem(RamTable, SWT.NONE);
			for(int j = 0; j < GPcounter; j++) {
				RamTableitem[i].setText(j, "0");
			}
		}
		
		for (int i = 0; i < RamTable.getColumnCount(); i++) {
			RamTable.getColumn(i).pack();
		}

		/**
		 * Line Section
		 */
		Group grpLine = new Group(content, SWT.NONE);
		grpLine.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		grpLine.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpLine.setLayout(new GridLayout(1, false));
		grpLine.setText("Line");

		txtLine = new Text(grpLine, SWT.BORDER | SWT.READ_ONLY);
		txtLine.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		txtLine.setText(Logger.getLine(playCounter = 0));

		/**
		 * Next Process Section
		 */
		grpCurrentProcess = new Group(content, SWT.NONE);
		grpCurrentProcess.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		grpCurrentProcess.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpCurrentProcess.setLayout(new FillLayout());
		grpCurrentProcess.setText("Next Process");

		Button[] btnsNextProcess = new Button[numOfProcesses];
		for (int i = 0; i < numOfProcesses; i++) {
			btnsNextProcess[i] = new Button(grpCurrentProcess, SWT.TOGGLE);
			btnsNextProcess[i].setText("P " + i);
			btnsNextProcess[i].setEnabled(false);
		}

		/**
		 * Counter section (Ram Capacity | Page Fault | Page Replacement)
		 */
		Group grpCounters = new Group(sidebar, SWT.NONE);
		grpCounters.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		grpCounters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpCounters.setLayout(new GridLayout(2, false));
		grpCounters.setText("Counters");

		Label lblCapacity = new Label(grpCounters, SWT.NONE);
		lblCapacity.setText("Ram Capacity :");

		txtCapacity = new Text(grpCounters, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_txtCapacity = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_txtCapacity.widthHint = 27;
		txtCapacity.setLayoutData(gd_txtCapacity);
		txtCapacity.setText(Integer.toString(ramCapacity));

		Label lblPageFault = new Label(grpCounters, SWT.NONE);
		lblPageFault.setText("Page Fault :");

		txtPageFault = new Text(grpCounters, SWT.BORDER | SWT.READ_ONLY);
		txtPageFault.setText("0");
		GridData gd_txtPageFault = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_txtPageFault.widthHint = 27;
		txtPageFault.setLayoutData(gd_txtPageFault);

		Label lblPageReplacement = new Label(grpCounters, SWT.NONE);
		GridData gd_lblPageReplacement = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblPageReplacement.widthHint = 136;
		lblPageReplacement.setLayoutData(gd_lblPageReplacement);
		lblPageReplacement.setText("Page Replacement :");

		txtPageReplacement = new Text(grpCounters, SWT.BORDER | SWT.READ_ONLY);
		txtPageReplacement.setText("0");
		GridData gd_txtPageReplacement = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_txtPageReplacement.widthHint = 27;
		txtPageReplacement.setLayoutData(gd_txtPageReplacement);

		
		/**
		 * Processes Checkbox Section
		 */
		Group grpProcesses = new Group(sidebar, SWT.NONE);
		grpProcesses.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		grpProcesses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		GridLayout gl_grpProcesses = new GridLayout(1, false);
		gl_grpProcesses.marginBottom = 5;
		grpProcesses.setLayout(gl_grpProcesses);
		grpProcesses.setText("Processes");

		ProcessesTable = new Table(grpProcesses, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd_ProcessesTable = new GridData(SWT.FILL, SWT.TOP, true, false);
		gd_ProcessesTable.heightHint = 120;
		ProcessesTable.setLayoutData(gd_ProcessesTable);
		ProcessesItems = new TableItem[numOfProcesses];
		for (int i = 0; i < numOfProcesses; i++) {
			ProcessesItems[i] = new TableItem(ProcessesTable, SWT.NONE);
			ProcessesItems[i].setText("Procces " + i);
		}
		
		Button btnSelectAll = new Button(grpProcesses, SWT.NONE);
		btnSelectAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				for (int i = 0; i < numOfProcesses; i++) {
					ProcessesItems[i].setChecked(true);
				}
			}
		});
		btnSelectAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSelectAll.setText("Select All");

		/**
		 * Buttons Section (Play | Play All | Exit)
		 */
		Composite boxActions = new Composite(sidebar, SWT.NONE);
		boxActions.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		GridData gd_boxActions = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_boxActions.heightHint = 51;
		boxActions.setLayoutData(gd_boxActions);
		FillLayout fl_boxActions = new FillLayout(SWT.HORIZONTAL);
		fl_boxActions.spacing = 10;
		boxActions.setLayout(fl_boxActions);

		btnPlay = new Button(boxActions, SWT.NONE);
		btnPlay.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				
				if(validateCheckboxes()) {
					if (playCounter < sizeOfLines - 1) {
						PlayButton(playCounter);
						playCounter++;
					} else {
						System.out.println("done all file");
						btnPlay.setEnabled(false);
						btnPlayAll.setEnabled(false);
					}
				}
			}
		});
		btnPlay.setText("Play");

		
		btnPlayAll = new Button(boxActions, SWT.NONE);
		btnPlayAll.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnPlayAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(validateCheckboxes()) {
					while(playCounter < sizeOfLines - 1) {
						PlayButton(playCounter);
						playCounter++;
					}
					
					btnPlay.setEnabled(false);
					btnPlayAll.setEnabled(false);

					System.out.println("done all file");
				}
			}
		});
		btnPlayAll.setText("Play All");
		
		
		Composite boxAction2 = new Composite(sidebar, SWT.NONE);
		boxAction2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		GridData gd_boxAction2 = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_boxAction2.heightHint = 51;
		boxAction2.setLayoutData(gd_boxAction2);
		FillLayout fl_boxAction2 = new FillLayout(SWT.HORIZONTAL);
		fl_boxAction2.spacing = 10;
		boxAction2.setLayout(fl_boxAction2);
		
		Button btnReset = new Button(boxAction2, SWT.NONE);
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				playCounter = 0;
				PF = 0;
				PR = 0;
				
				// Erase counters
				txtPageFault.setText(Integer.toString(PF));
				txtPageReplacement.setText(Integer.toString(PR));
				
				// Erase ram table
				for (int i = 0; i < GPcounter; i++) {
					RamTablecolumn[i].setText(" ");
				}

				for (int i = 0; i < 5; i++) {
					for(int j = 0; j < GPcounter; j++) {
						RamTableitem[i].setText(j, "0");
						RamTableitem[i].setBackground(j, new Color(display, 255, 255, 255));
						RamTableitem[i].setForeground(j, new Color(display, 0, 0, 0));
					}
				}
				
				// Erase checkboxes
				for (int i = 0; i < numOfProcesses; i++) {
					ProcessesItems[i].setChecked(false);
				}
				
				// Erase next process
				for (int i = 0; i < numOfProcesses; i++) {
					btnsNextProcess[i].setSelection(false);
				}
				
				// Erase Text line
				txtLine.setText(Logger.getLine(playCounter = 0));
				
				//Erase play buttons
				btnPlay.setEnabled(true);
				btnPlayAll.setEnabled(true);
				
			}
		});
		btnReset.setText("Reset");
		btnReset.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		
		Button btnExit = new Button(boxAction2, SWT.NONE);
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println("Bye!!");
				System.exit(0);
			}
		});
		btnExit.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnExit.setText("Exit");
		btnExit.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));

	}

	
	
	protected void PlayButton(int lineNumber) {
		//allTheLinesSize++;
		Integer proccesNum;
		Map<String, String> map = Logger.lineBreaker(lineNumber);
		System.out.println(map);
		switch(map.get("action")) {
		case "PF":
			PF++;
			txtPageFault.setText(Integer.toString(PF));
			break;
			
		case "PR":
			PR++;
			txtPageReplacement.setText(Integer.toString(PR));
						
				System.out.println(map.get("pageId"));
				int MTH = Integer.parseInt(map.get("MTH"));
				int MTR = Integer.parseInt(map.get("MTR"));
				//Integer pageId = new Integer(map.get("PageId"));
				RamTablecolumn[MTH-1].setText(" ");// Changes
				for (int i = 0; i < 5; i++) {// Changes
					RamTableitem[i].setText(MTH-1, "0"); // Changes
					RamTableitem[i].setBackground(MTH-1, new Color(display, 255, 255, 255));
					RamTableitem[i].setForeground(MTH-1, new Color(display, 0, 0, 0));
				}
			
			break;
			
		case "GP":
			proccesNum = Integer.parseInt(map.get("process"));
			
			List<String> dataAsString = Arrays.asList(map.get("data").split(", "));
			List<Integer> data = new ArrayList<Integer>();
			data = dataAsString.stream().map(Integer::parseInt).collect(Collectors.toList());
			
			Control[] children = grpCurrentProcess.getChildren();
			for (int i = 0; i < children.length; i++) {
				Control child = children[i];
				((Button) child).setSelection(false);
			}

			((Button) children[proccesNum]).setSelection(true);
			
			if(ProcessesItems[proccesNum].getChecked()) {
				System.out.println(map.get("pageId"));
				int pageId = Integer.parseInt(map.get("pageId"));
				//Integer pageId = new Integer(map.get("PageId"));
				RamTablecolumn[pageId-1].setText(map.get("pageId"));// Changes
				for (int i = 0; i < 5; i++) {// Changes
					RamTableitem[i].setText(pageId-1, Integer.toString(data.get(i))); // Changes
					RamTableitem[i].setBackground(pageId-1, new Color(display, 98, 142, 202));
					RamTableitem[i].setForeground(pageId-1, new Color(display, 255, 255, 255));
				}
			}
			break;
		}
		

		String Line = Logger.getLine(lineNumber+1);
		txtLine.setText(Line);
		
	}
	
	/**
	 * 
	 * Validation for processes list
	 */
	protected boolean validateCheckboxes() {
		boolean selected = false;
		for (int i = 0; i < numOfProcesses; i++) {
			if(ProcessesItems[i].getChecked()) {
				selected = true;
			}
		}
		
		if(!selected) {
			
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
	        
	        messageBox.setText(" Validation");
	        messageBox.setMessage("Please select at least one process.");
	        messageBox.open();
	        			
		}
		
		return selected;
	}

}