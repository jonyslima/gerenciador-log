package br.com.jonylima.leitorlog.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOG")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "DATA")
	private LocalDateTime data;
	@Column(name = "IP")
	private String ip;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "USER_AGENT")
	private String userAgent;

	public Log() {
		super();
	}

	public Log(LocalDateTime data, String ip, Integer status, String userAgent) {
		super();
		this.data = data;
		this.ip = ip;
		this.status = status;
		this.userAgent = userAgent;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", data=" + data + ", ip=" + ip + ", status=" + status + ", userAgent=" + userAgent
				+ "]";
	}

}
