package com.hit.memoryunits;

import java.util.HashMap;

public class RAM extends java.lang.Object {
	private int inCapacity;
	private java.util.Map<java.lang.Long, Page<byte[]>> Pages;

	public RAM(int initialCapacity) {
		this.inCapacity = initialCapacity;
		Pages = new HashMap<Long, Page<byte[]>>(initialCapacity);
		
	}

	public java.util.Map<java.lang.Long, Page<byte[]>> getPages() {
		return this.Pages;
	}
	// setPages

	public void setPages(java.util.Map<java.lang.Long, Page<byte[]>> pages) {
		this.Pages = pages;
	}
	// getPage

	public Page<byte[]> getPage(java.lang.Long pageId) {
		// System.out.println(pageId);
		return Pages.get(pageId);

	}
	// addPage

	public void addPage(Page<byte[]> addPage) {
		// System.out.println("Ram.addPageFunction");
		if (addPage == null)
			System.out.println("Page is null before");
		else {
			System.out.println(addPage + " Added to Ram successfully!");
			Pages.put(addPage.getPageId(), addPage);
		}

	}
	// removePage

	public void removePage(Page<byte[]> removePage) {
		Pages.remove(removePage.getPageId());
	}
	// getPages

	public Page<byte[]>[] getPages(java.lang.Long[] pageIds) {
		@SuppressWarnings("unchecked")
		Page<byte[]>[] PagesArr = new Page[pageIds.length];
		for (int i = 0; i < pageIds.length; i++) {
			PagesArr[i] = Pages.get(pageIds[i]);
		}
		return PagesArr;
	}
	// addPages

	public void addPages(Page<byte[]>[] addPages) {
		for (int i = 0; i < addPages.length; i++) {
			Pages.put(addPages[i].getPageId(), addPages[i]);
		}
	}
	// removePages

	@SuppressWarnings("unlikely-arg-type")
	public void removePages(Page<byte[]>[] removePages) {
		for (int i = 0; i < removePages.length; i++) {
			Pages.remove(removePages[i]);
		}
	}
	// getInitialCapacity

	public int getInitialCapacity() {
		return this.inCapacity;
	}
	// setInitialCapacity

	public void setInitialCapacity(int initialCapacity) {
		this.inCapacity = initialCapacity;
	}

}
