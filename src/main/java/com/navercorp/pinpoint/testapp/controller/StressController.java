//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.navercorp.pinpoint.testapp.controller;

import com.navercorp.pinpoint.testapp.util.Description;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StressController {
	public StressController() {
	}

	@RequestMapping({"/consumeCpu"})
	@ResponseBody
	@Description("Call that consumes a lot of cpu time.")
	public Map<String, Object> consumeCpu() throws InterruptedException {
		int cpuCount = Runtime.getRuntime().availableProcessors();
		int threadSize = Math.max(1, cpuCount - 1);
		long limitTime = 10000L;
		CountDownLatch latch = new CountDownLatch(threadSize);

		for(int i = 0; i < threadSize; ++i) {
			Thread thread = new Thread(new ConsumeCpu(latch, limitTime));
			thread.setDaemon(true);
			thread.start();
		}

		latch.await();
		Map<String, Object> map = new HashMap();
		map.put("message", "ok");
		return map;
	}

	@RequestMapping({"/consumeMemory"})
	@ResponseBody
	@Description("Call that consumes some memory that may trigger a few garbage collections.")
	public Map<String, Object> consumeMemory() throws InterruptedException {
		this.consumeMemory(16384, 20);
		Map<String, Object> map = new HashMap();
		map.put("message", "ok");
		return map;
	}

	@RequestMapping({"/consumeMemoryLarge"})
	@ResponseBody
	@Description("Call that consumes a large amount of memory that will most likely trigger multiple garbage collections.")
	public Map<String, Object> consumeMemoryLarge() throws InterruptedException {
		this.consumeMemory(16384, 100);
		Map<String, Object> map = new HashMap();
		map.put("message", "ok");
		return map;
	}

	private void consumeMemory(int byteArraySize, int createMaxHeapCount) {
		long heapSize = Runtime.getRuntime().maxMemory();
		int count = (int)Math.max(1L, heapSize / (long)byteArraySize * (long)createMaxHeapCount);
		List<WeakReference<byte[]>> weakReferece = new ArrayList();

		for(int i = 0; i < count; ++i) {
			weakReferece.add(new WeakReference(new byte[byteArraySize]));
		}

		System.gc();
	}

	class ConsumeCpu implements Runnable {
		private final CountDownLatch latch;
		private final long limitTime;

		public ConsumeCpu(CountDownLatch latch, long limitTime) {
			this.latch = latch;
			this.limitTime = limitTime;
		}

		public void run() {
			long startTime = System.currentTimeMillis();

			try {
				BigDecimal decimal = new BigDecimal(0);

				for(int num = 1; num < Integer.MAX_VALUE; ++num) {
					long currentTimeMillis = System.currentTimeMillis();
					if (currentTimeMillis - startTime > this.limitTime) {
						break;
					}

					decimal.add(new BigDecimal(num));
				}
			} finally {
				this.latch.countDown();
			}

		}
	}
}
