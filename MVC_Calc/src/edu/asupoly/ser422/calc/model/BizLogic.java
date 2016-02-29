package edu.asupoly.ser422.calc.model;

import java.util.concurrent.atomic.AtomicInteger;

public final class BizLogic {
	private static final AtomicInteger __theValue = new AtomicInteger(0);
	
	public static final int add(int val) {
		return __theValue.addAndGet(val);
	}
	public static final int subtract(int val) {
		return add(-1*val);
	}
	public static final int set(int val) {
		while(true){
			int current = __theValue.get();
			if (__theValue.compareAndSet(current, val)) {
				return val;
			}
		}
	}
	
	public static final int get() {
		return __theValue.get();
	}
}
