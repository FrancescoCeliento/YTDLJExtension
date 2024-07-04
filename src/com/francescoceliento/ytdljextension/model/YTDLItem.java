package com.francescoceliento.ytdljextension.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YTDLItem {
	
	private String id;
	private Date uploadDate;
	private String title;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getUploadDate() {
		return uploadDate;
	}
	
	public String getUploadDateToString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateToStr = dateFormat.format(uploadDate);
		return dateToStr;
	}
	
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	public void setUploadDate(String uploadDateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
        try {
        	this.uploadDate = new Date(sdf.parse(uploadDateString).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getFileName() {
		return getUploadDateToString() + "-" + title + "-" + id + ".mp3";
	}
	
}
