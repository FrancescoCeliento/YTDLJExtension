package com.francescoceliento.ytdljextension.repo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
	
	private String path;
	private String fileName;
	
	FileWriter fw;
    BufferedWriter bw;
    PrintWriter pw;
	
	public Logger(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
		
		try {

			fw = new FileWriter(path + "\\" + fileName, true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void log(String string) {
		pw.println(string);
		pw.flush();
	}
	
	public void close() {
        try {
        	pw.close();
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
