package com.ygs.virtualfittingroom.entity;

import java.io.Serializable;

public class ProductCategory implements Serializable{

	private String id;
	private String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
