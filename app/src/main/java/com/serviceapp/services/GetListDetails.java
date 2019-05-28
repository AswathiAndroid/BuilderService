package com.serviceapp.services;

//Custom Class
public class GetListDetails {

	public String service;
	public Integer imgLogo;
	public Integer imgStatus;
	public String des;
	public Integer rs;
	
	public String getService() {
		return service;
	}
 
	public void setService(String service) {
		this.service = service;
	}
	
	public int getLogo() {
		return imgLogo;
	}
	
	public void setLogo(int imgLogo) {
		this.imgLogo = imgLogo;
	}
	
	public int getStatus() {
		return imgStatus;
	}
	
	public void setStatus(int imgStatus) {
		this.imgStatus = imgStatus;
	}
	
	public String getDes() {
		return des;
	}
	
	public void setDes(String des) {
		this.des = des;
	}
	
	public int getRs() {
		return rs;
	}
	
	public void setRs(int rs) {
		this.rs = rs;
	}
}
