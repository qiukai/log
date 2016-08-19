package top.qiuk.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

public class TestLog4j {

	private Logger logger = Logger.getLogger(getClass());

	public static void main(String[] args) throws IOException {
		new TestLog4j().test();
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
