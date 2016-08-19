package top.qiuk.log.constant;

public enum LogLevelEnum {

	DEBUG("debug"), INFO("info"), WARN("warn"), ERROR("error"), FATAL("fatal");

	private String value;

	private LogLevelEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static LogLevelEnum getLogLevel(String value) {
		for (LogLevelEnum logLevel : LogLevelEnum.values()) {
			if (logLevel.getValue().equals(value)) {
				return logLevel;
			}
		}
		throw new IllegalArgumentException("LogLevelEnum中没有存在值为：" + value + "的枚举");
	}

}
