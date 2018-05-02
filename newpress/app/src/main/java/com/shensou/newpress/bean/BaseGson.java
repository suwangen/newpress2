package com.shensou.newpress.bean;

public class BaseGson {
	
	private String msg;//返回信息this.status = statu
	private String code;//0失败，1成功

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
