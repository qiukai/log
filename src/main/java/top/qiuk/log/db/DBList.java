package top.qiuk.log.db;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import top.qiuk.log.util.PropertiesUtil;

public class DBList {

	private static List<MongoDatabase> DBList = new ArrayList<MongoDatabase>();

	static {
		String dbs = PropertiesUtil.getPropertyValue("db", "db");
		String[] dbsplit = dbs.split(",");
		for (String db : dbsplit) {
			String host = PropertiesUtil.getPropertyValue("db", db + ".host");
			int port = Integer.valueOf(PropertiesUtil.getPropertyValue("db", db + ".port")).intValue();
			String database = PropertiesUtil.getPropertyValue("db", db + ".database");
			String userName = PropertiesUtil.getPropertyValue("db", db + ".userName");
			String password = PropertiesUtil.getPropertyValue("db", db + ".password");
			createDB(host, port, database, userName, password);
		}
		if (DBList.size() < 1) {
			throw new IllegalArgumentException("最少配置一个数据源！");
		}
		System.out.println(dbs + "---" + DBList);

	}

	public static void createDB(String host, int port, String database, String userName, String password) {
		try {
			// 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
			// ServerAddress()两个参数分别为 服务器地址 和 端口
			ServerAddress serverAddress = new ServerAddress(host, port);
			List<ServerAddress> addrs = new ArrayList<ServerAddress>();
			addrs.add(serverAddress);

			// MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
			MongoCredential credential = MongoCredential.createScramSha1Credential(userName, database,
					password.toCharArray());
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			credentials.add(credential);

			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient(addrs, credentials);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
			DBList.add(mongoDatabase);
			System.out.println("连接数据成功--host：" + host + "--port:" + port + "--database:" + database + "--userName:" + userName
					+ "--password:" + password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getDBListSize() {
		return DBList.size();
	}

	public static MongoDatabase getMongoDatabase(int i) {
		return DBList.get(i);
	}
}
