package com.francescoceliento.ytdljextension.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YTDLInput {
	
	private String playlistUrl;
	private String downloadDirectory;
	private String youtubeDLPath;
	private Date dateBegin;
	private Date dateEnd;
	private String splitTime;
	
	private boolean dateFilter;
	
	public YTDLInput() {
		setDateBegin("10000101");
		setDateEnd("99991231");
		dateFilter = false;
	}
	public String getPlaylistUrl() {
		return playlistUrl;
	}
	public void setPlaylistUrl(String playlistUrl) {
		this.playlistUrl = playlistUrl;
	}
	public String getDownloadDirectory() {
		return downloadDirectory;
	}
	public void setDownloadDirectory(String downloadDirectory) {
		this.downloadDirectory = downloadDirectory;
	}
	public String getYoutubeDLPath() {
		return youtubeDLPath;
	}
	public void setYoutubeDLPath(String youtubeDLPath) {
		this.youtubeDLPath = youtubeDLPath;
	}
	
	public Date getDateBegin() {
		return dateBegin;
	}
	public String getDateBeginToString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateToStr = dateFormat.format(dateBegin);
				
		return dateToStr;
	}
	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
		dateFilter = true;
	}
	public void setDateBegin(String dateBeginString) {
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
        try {
        	this.dateBegin = new Date(sdf.parse(dateBeginString).getTime());
        	dateFilter = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public Date getDateEnd() {
		return dateEnd;
	}
	public String getDateEndToString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateToStr = dateFormat.format(dateEnd);
				
		return dateToStr;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
		dateFilter = true;
	}
	public void setDateEnd(String dateEndString) {
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
        try {
        	this.dateEnd = new Date(sdf.parse(dateEndString).getTime());
        	dateFilter = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean isValid(Date date) {
		return date.compareTo(dateBegin) >=0 && date.compareTo(dateEnd)<=0;
	}
	
	public boolean isFiltered() {
		return dateFilter;
	}
	public String getSplitTime() {
		return splitTime;
	}
	public void setSplitTime(String splitTime) {
		this.splitTime = splitTime;
	}
	public boolean isSplitting() {
		return this.splitTime != null; 
	}

}
