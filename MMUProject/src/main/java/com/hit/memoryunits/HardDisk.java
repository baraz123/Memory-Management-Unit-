
package com.hit.memoryunits;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.hit.util.MMULogger;

@SuppressWarnings("serial")
public class HardDisk extends java.lang.Object implements java.io.Serializable {

	private final int _SIZE = 300;// HD SIZE
	final int initializationHD = _SIZE - 30;// If HD not exists We create new HD file and initialize him.
	final String NAME_FILE_DEFAULT = "src/main/HardDiskContent.txt";// The file of HD
	private static HardDisk instance = null;
	protected Map<java.lang.Long, Page<byte[]>> HDmap;

	private HardDisk() throws IOException {
		HDmap = new HashMap<java.lang.Long, Page<byte[]>>(_SIZE);
		File file = new File(NAME_FILE_DEFAULT);
		System.out.println(file.length());
		// if file does not exists, then create it
		if (!file.exists() || file.length() == 0) {
			
			for (Long i = (long) 0; i < initializationHD; i++) {
				byte[] content;
				content = new byte[3];
				content[0] = 0;
				Page<byte[]> memoryPage = new Page<byte[]>(i, content);
				HDmap.put(i, memoryPage);
			}
			saveHDmapIntoFile();

		} else {
			System.out.println("HardDisk Constructor - file exists loading data from file");
			// Deserialize and copy file into HDmap
			readHDmapFromFile();
		}

	}

	public static HardDisk getInstance() {
		if (instance == null) {
			try {
				instance = new HardDisk();
			} catch (IOException e) {
				MMULogger.getInstance().write("HardDisk Error", Level.SEVERE);
			}
		}
		return instance;
	}

	public Page<byte[]> pageFault(java.lang.Long pageId) throws java.io.FileNotFoundException, java.io.IOException {

		if (!HDmap.containsKey(pageId))
			throw new IOException("Page does not exists in HD.");

		MMULogger.getInstance().write("PF " + pageId, Level.INFO);

		return HDmap.get(pageId);
	}

	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, java.lang.Long moveToRamId)
			throws java.io.FileNotFoundException, java.io.IOException {
		
		// readHDmapFromFile();
		if (!HDmap.containsKey(moveToRamId) || !HDmap.containsKey(moveToHdPage.getPageId()))
			throw new IOException("Page does not exists in HD!");

		// addHdPages(moveToHdPage.getPageId(), moveToHdPage);
		HDmap.replace(moveToHdPage.getPageId(), moveToHdPage);
		saveHDmapIntoFile();
		

		MMULogger.getInstance().write("PR MTH " + moveToHdPage.getPageId() + "  MTR "+ moveToRamId, Level.INFO);
		// Returns: the page with the given pageId
		return HDmap.get(moveToRamId);
	}

	public void addHdPages(java.lang.Long Long, Page<byte[]> Page) {// Add pages to HD

		HDmap.put(Long, Page);

		try {
			saveHDmapIntoFile();
		} catch (FileNotFoundException F) {
			MMULogger.getInstance().write("File not found Error in AddHdPages method", Level.SEVERE,F);
		} catch (IOException i) {
			i.printStackTrace();
			MMULogger.getInstance().write("IO Error in AddHdPages method", Level.SEVERE,i);
		}

	}

	private void saveHDmapIntoFile() throws FileNotFoundException, IOException {

		FileOutputStream hdfileStream = new FileOutputStream(NAME_FILE_DEFAULT);
		BufferedOutputStream bufferedHDStream = new BufferedOutputStream(hdfileStream);
		ObjectOutputStream objectHDStream = new ObjectOutputStream(bufferedHDStream);
		try {
			objectHDStream.writeObject(HDmap);
			objectHDStream.close();
			bufferedHDStream.close();
			hdfileStream.close();

		} catch (FileNotFoundException F) {
			MMULogger.getInstance().write("File not found Error in saveHDmapIntoFile method", Level.SEVERE,F);
		} catch (IOException i) {
			i.printStackTrace();
			MMULogger.getInstance().write("IO Error in saveHDmapIntoFile method", Level.SEVERE,i);
		}
	}

	@SuppressWarnings("unchecked")
	private void readHDmapFromFile() throws FileNotFoundException, IOException {

		FileInputStream hdfileStream = new FileInputStream(NAME_FILE_DEFAULT);
		BufferedInputStream bufferedHDStream = new BufferedInputStream(hdfileStream);
		ObjectInputStream objectHDStream = new ObjectInputStream(bufferedHDStream);
		try {
			HDmap = (HashMap<java.lang.Long, Page<byte[]>>) objectHDStream.readObject();
			objectHDStream.close();
			bufferedHDStream.close();
			hdfileStream.close();

		} catch (FileNotFoundException F) {
			MMULogger.getInstance().write("File not found Error in readHDmapFromFile method", Level.SEVERE,F);
		} catch (ClassNotFoundException | IOException i) {
			i.printStackTrace();
			MMULogger.getInstance().write("IO Error in readHDmapFromFile method", Level.SEVERE,i);
			return;
		}

	}

	public int getSize() {
		return _SIZE;
	}

}