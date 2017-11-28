package com.nike.reporting.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nrt_user")
public class Nrt_user {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "fname")
	private String fname;

	@Column(name = "lname")
	private String lname;

	@Column(name = "nikeid")
	private String nikeid;

	@Column(name = "infyid")
	private String infyid;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	@Column(name = "status")
	private String status;

	@Column(name = "nikeemail")
	private String nikeEmail;

	@Column(name = "infyemail")
	private String infyEmail;

	@Embedded
	private AuditInfo auditInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getNikeid() {
		return nikeid;
	}

	public void setNikeid(String nikeid) {
		this.nikeid = nikeid;
	}

	public String getInfyid() {
		return infyid;
	}

	public void setInfyid(String infyid) {
		this.infyid = infyid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public String getNikeEmail() {
		return nikeEmail;
	}

	public void setNikeEmail(String nikeEmail) {
		this.nikeEmail = nikeEmail;
	}

	public String getInfyEmail() {
		return infyEmail;
	}

	public void setInfyEmail(String infyEmail) {
		this.infyEmail = infyEmail;
	}

}