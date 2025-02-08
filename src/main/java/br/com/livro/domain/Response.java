package br.com.livro.domain;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {
	private String status;
	private String msg;
	
	public Response() {}
	
	public static Response ok(String msg) {
		Response r = new Response();
		r.setStatus("OK");
		r.setMsg(msg);
		return r;
	}
	
	public static Response error(String msg) {
		Response r = new Response();
		r.setStatus("ERROR");
		r.setMsg(msg);
		return r;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
