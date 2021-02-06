package com.codesp07.RestApiImplementation.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Posts {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int aid;
	private String title;
	private String pcontent;
	
	
	
	public Posts() {
		super();
	}

	public Posts(String title, String pcontent) {
		super();
		
		this.title = title;
		this.pcontent = pcontent;
	}
	
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	@Override
	public String toString() {
		return "Posts [aid=" + aid + ", title=" + title + ", pcontent=" + pcontent + "]";
	}
	
	
}
