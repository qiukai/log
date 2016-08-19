package top.qiuk.log.db;

import java.util.Date;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import top.qiuk.log.constant.LogLevelEnum;
import top.qiuk.log.util.Id;

public class DBUtil {
	
	public static DBUtil dbUtil= new DBUtil();

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

	public static void fatal(String message) {
		dbUtil.addLog(message, LogLevelEnum.FATAL);
	}

	public void dbUtil(String message, LogLevelEnum logLevelEnum) {
		int hashCode = message.hashCode();
		int num = hashCode % DBList.getDBListSize();
		MongoDatabase mongoDatabase = DBList.getMongoDatabase(num);
		MongoCollection<Document> collection = getCollection(mongoDatabase, logLevelEnum.getValue());
		Document document = new Document();
		document.append("_id", Id.next());
		document.append("message", message);

		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			if (stack[i].getClassName().equals(getClass().getName())) {
				System.out.println(stack[i+1].getClassName());
			}
		}

		collection.insertOne(document);
	}

}