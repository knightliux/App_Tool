package com.ex.app_tool.model;

public class Model_up {
	private String code;// 0 need to up or not;
	private String url;// down url
	private String msg;// up msg
	private String version;// new ver
	private String type;// 0 mandatory to UP,1 Select upgrade

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
