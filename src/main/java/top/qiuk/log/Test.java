package top.qiuk.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import top.qiuk.log.db.LogUtil;

public class Test {

	public static long time = 10L;

	public static void main(String[] args) throws IOException {
		new Test().insertDebug();
	}
	
	public void insertError() {
		RuntimeException runtimeException = new RuntimeException();
		runtimeException.printStackTrace();
		
		LogUtil.error("123", runtimeException);
	}

	public void insertDebug() {
		

		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						LogUtil.debug("342323");
					} catch (Exception e) {
						try {
							Thread.sleep(time);
							LogUtil.error("123", e);
						} catch (Exception e1) {
							try {
								Thread.sleep(time);
								LogUtil.error("123", e);
							} catch (Exception e2) {
								try {
									Thread.sleep(time);
									LogUtil.error("123", e);
								} catch (Exception e3) {
									try {
										Thread.sleep(time);
										LogUtil.error("123", e);
									} catch (Exception e4) {
										e4.printStackTrace();
									}
								}
							}
						}
					}
				}
			}).start();
		}

	}

	public void test() throws IOException {

		RuntimeException runtimeException = new RuntimeException("123");

		String stackTrace = null;

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
		runtimeException.printStackTrace(printWriter);

		printWriter.flush();
		stringWriter.flush();
		printWriter.close();
		stackTrace = stringWriter.toString();
		stringWriter.close();
		System.out.println(stackTrace);

	}
}
