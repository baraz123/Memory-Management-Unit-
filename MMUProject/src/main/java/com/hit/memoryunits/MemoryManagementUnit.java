package com.hit.memoryunits;

//Bar Azoulay 203107461
//Netanel Snir 037046588
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.hit.util.MMULogger;

public class MemoryManagementUnit extends java.lang.Object {

	private RAM ram;
	private com.hit.algorithm.IAlgoCache<Long, Long> IAlgoCache;
	MMULogger logger = MMULogger.getInstance();

	public MemoryManagementUnit(int ramCapacity, com.hit.algorithm.IAlgoCache<java.lang.Long, java.lang.Long> algo) {
		ram = new RAM(ramCapacity);
		logger.write("RC " + ram.getInitialCapacity(), Level.INFO);
		IAlgoCache = algo;
	}

	@SuppressWarnings("unchecked")
	public synchronized Page<byte[]>[] getPages(java.lang.Long[] pageIds, boolean[] writePages)
			throws java.io.IOException {
		HardDisk HD = HardDisk.getInstance();
		List<Page<byte[]>> Pages = new ArrayList<Page<byte[]>>();
		int i = 0;
		for (Long pageId : pageIds) {
			// Read method //
			if (writePages[i] == false && IAlgoCache.getElement(pageId) != null) {
				// in case of read method && IAlgoCache contain PageId - just add from ram
				System.out.println("this Page " + ram.getPage(pageId) +" AND "+ram.getPage(pageId).getContent()+" is already located in the Ram ");
				Pages.add(new Page<byte[]>(pageId, ram.getPage(pageId).getContent().clone()));
			} else {

				// while IAlgoCache does not contain pageId //
				do {
					// if ram is full IAlgoCache return page ID to remove otherwise return NULL //
					Long removePageId = IAlgoCache.putElement(pageId, pageId);
					// if ram is not full //
					
					if (removePageId == null) {
						System.out.println("--pageFault--");

						System.out.println(pageId + " will add to Ram soon");
						ram.addPage(HD.pageFault(pageId));
						System.out.println("--END pageFault--\n");
					} else {
						// if ram is full we use pageReplacement//
						System.out.println("--pageReplacement--");
						System.out.println("remove from ram - " + removePageId);
						System.out.println(pageId + " will add to Ram soon after Page Replacement");
						System.out.println("--START pageReplacement--\n");
						Page<byte[]> moveToHDPage = ram.getPage(removePageId);
						ram.removePage(moveToHDPage);
						ram.addPage(HD.pageReplacement(moveToHDPage, pageId));
						// System.out.println("--END pageReplacement--\n");
					}

				} while (IAlgoCache.getElement(pageId) == null);

				Pages.add(ram.getPage(pageId));
			}
			
			i++;
		}
		return Pages.toArray((Page<byte[]>[]) new Page<?>[Pages.size()]);
	}

	public java.util.Map<java.lang.Long, Page<byte[]>> getRamPages() {
		return ram.getPages();
	}

	public void shutdown() {
		HardDisk HD = HardDisk.getInstance();
		System.out.println("Saving");
		for (Long page : ram.getPages().keySet()) {
			HD.addHdPages(page, ram.getPages().get(page));
			System.out.println("Saving to HD existents pages from ram - " + page + "--" + ram.getPages().get(page));
		}
	}

}
