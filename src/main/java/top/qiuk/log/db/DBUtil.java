package top.qiuk.log.db;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import top.qiuk.log.constant.LogLevelEnum;
import top.qiuk.log.util.Id;

public class DBUtil {

	public static DBUtil dbUtil = new DBUtil();

	public DBUtil() {
		super();
	}

	/**
	 * 得到集合
	 * 
	 * @param level
	 * @return
	 */
	private static MongoCollection<Document> getCollection(MongoDatabase mongoDatabase, String level) {
		return mongoDatabase.getCollection(level);
	}

	public static void debug(String message) {
		dbUtil.addLog(message, LogLevelEnum.DEBUG);
	}

	public static void info(String message) {
		dbUtil.addLog(message, LogLevelEnum.INFO);
	}

	public static void warn(String message) {
		dbUtil.addLog(message, LogLevelEnum.WARN);
	}

	public static void error(String message) {
		dbUtil.addLog(message, LogLevelEnum.ERROR);
	}

	public static void error(String message, Throwable throwable) {

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
		throwable.printStackTrace(printWriter);
		message += (System.getProperty("line.separator") + stringWriter);
		DBUtil.error(message);
	}

	public static void fatal(String message) {
		dbUtil.addLog(message, LogLevelEnum.FATAL);
	}

	public void addLog(String message, LogLevelEnum logLevelEnum) {
		String id = Id.next();
		int num = id.hashCode() % DBList.getDBListSize();
		MongoDatabase mongoDatabase = DBList.getMongoDatabase(num);
		MongoCollection<Document> collection = getCollection(mongoDatabase, logLevelEnum.getValue());
		Document document = new Document();
		document.append("_id", id);
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			if (stack[i].getClassName().equals(getClass().getName())) {
				String className = stack[i + 1].getClassName();
				String[] split = className.split("\\.");
				String classSimpleName = split[split.length - 1];
				String methodName = stack[i + 1].getMethodName();
				int lineNumber = stack[i + 1].getLineNumber();
				StringBuilder sb = new StringBuilder();
				sb.append(className).append(".").append(methodName).append("(").append(classSimpleName)
						.append(".java:").append(lineNumber).append(")");
				document.append("class", sb.toString());
			}
		}
		document.append("message", message);
		collection.insertOne(document);
	}
}