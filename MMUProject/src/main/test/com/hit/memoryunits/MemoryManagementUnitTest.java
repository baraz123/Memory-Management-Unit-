
package com.hit.memoryunits;

import java.io.IOException;
import java.util.HashMap;

import com.hit.algorithm.*;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class MemoryManagementUnitTest {
	@Test
	public void test() throws IOException {

		Long[] pagesObj = new Long[] { (long) 20, (long) 35, (long) 82, (long) 83, (long) 84, (long) 85, (long) 86,
				(long) 87 };

		HardDisk HD = HardDisk.getInstance();

		for (int i = 0; i < pagesObj.length; i++) {
			// System.out.println(pagesObj[i]);
			Page<byte[]> P1 = new Page<byte[]>(pagesObj[i],
					new byte[] { (byte) (i + 1), (byte) ((i + 1) * 5), (byte) ((i + 1) * 10) });
			HD.addHdPages(pagesObj[i], P1);
			// Test.put(pagesObj[i], P1);
		}

		int Capacity = 3;
		IAlgoCache<Long, Long> LRU = new LRUAlgoCacheImpl<>(Capacity);
		MemoryManagementUnit MMU = new MemoryManagementUnit(Capacity, LRU);
		boolean[] bArray = { true, true, true, true, true, true, true, true };
		// RAM ram = new RAM(Capacity);
		/* FirstPages= */// MMU.getPages(pagesObj,bArray);

		Assert.assertEquals(pagesObj.length, MMU.getPages(pagesObj, bArray).length);

		System.out.println("//* RAM *//");
		System.out.println(MMU.getRamPages());

		System.out.println("//* HD *//");
		System.out.println(HD.HDmap);

	}
}