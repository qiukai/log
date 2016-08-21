package top.qiuk.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import top.qiuk.log.db.DBUtil;

public class TestLog4j {

	private Logger logger = Logger.getLogger(getClass());

	public static long time = 1L;

	public static void main(String[] args) throws IOException {
		new TestLog4j().insertError();
	}
	
	public void insertError() {
		RuntimeException runtimeException = new RuntimeException();
		runtimeException.printStackTrace();
		
		DBUtil.error("123", runtimeException);
	}

	public void insertDebug() {

		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						DBUtil.debug("342323");
					} catch (Exception e) {
						try {
							Thread.sleep(time);
							time *= 2;
							DBUtil.error("123", e);
							time = (long) time / 2;
						} catch (Exception e1) {
							try {
								Thread.sleep(time);
								time *= 2;
								DBUtil.error("123", e);
								time = (long) time / 2;
							} catch (Exception e2) {
								try {
									Thread.sleep(time);
									time *= 2;
									DBUtil.error("123", e);
									time = (long) time / 2;
								} catch (Exception e3) {
									try {
										Thread.sleep(time);
										time *= 2;
										DBUtil.error("123", e);
										time = (long) time / 2;
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
