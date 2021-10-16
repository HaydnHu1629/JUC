/**
 * reentrantlock用于替代synchronized
 * 本例中由于m1锁定this,只有m1执行完毕的时候,m2才能执行
 * 这里是复习synchronized最原始的语义
 * @author mashibing
 */
package com.mashibing.juc.c_020;

import java.util.concurrent.TimeUnit;

public class T01_ReentrantLock1 {
	synchronized void m1() {
		for(int i=0; i<10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
			// 锁重入是一个线程里面，两个对同一对象加锁的方法，可以重入
			if(i == 2) m2();
		}
		
	}
	
	synchronized void m2() {
		System.out.println("m2 ...");
	}
	
	public static void main(String[] args) {
		T01_ReentrantLock1 rl = new T01_ReentrantLock1();
		new Thread(rl::m1).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 竞争锁的不同线程，还是要等拿到锁之后才能执行
		new Thread(rl::m2).start();
	}
}
