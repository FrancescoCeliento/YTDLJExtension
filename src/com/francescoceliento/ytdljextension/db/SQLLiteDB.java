package com.francescoceliento.ytdljextension.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.francescoceliento.ytdljextension.model.YTDLItem;

public class SQLLiteDB {
	
	private String path;
	private static String name = "YTDLJExtension.db";
	
	Connection conn;
	
	
	public SQLLiteDB(String path) {
		this.path = path;
	}
	public String getPatch() {
		return path;
	}
	public void setPatch(String patch) {
		this.path = patch;
	}
		
	private String getDBUrl() {
		return "jdbc:sqlite:"+path+"\\"+name;
	}
	
	private String getDBPosition() {
		return path+"//"+name;
	}
	
	public void checkDatabase() {
		File dbFile = new File(getDBPosition());
		String url = getDBUrl();  
		
		if (!dbFile.exists()) {
			   
	        try {  
	            conn = DriverManager.getConnection(url);  
	            if (conn != null) {  
	                DatabaseMetaData meta = conn.getMetaData();  
	                System.out.println("The driver name is " + meta.getDriverName());  
	                System.out.println("A new database has been created.");
	                initializeTable();
	            }  
	   
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
		} else {
			System.out.println("Abbiamo trovato un file database già esistente");
		}
		
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void initializeTable() {  
          
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS items (\n"  
                + " id text PRIMARY KEY,\n"  
                + " upload_date text,\n"  
                + " title text,\n"
                + " dt_insert text\n"
                + ");";  
          
        try{  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
	
	public void insert(YTDLItem item) {
		// SQL statement for creating a new table  
        String sql = "INSERT INTO items(id, upload_date, title, dt_insert) VALUES(?, ?, ?, ?);";  
          
        try{  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, item.getId());  
            pstmt.setString(2, item.getUploadDateToString());
            pstmt.setString(3, item.getTitle());
            pstmt.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            pstmt.executeUpdate();    
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
	}
	
	public YTDLItem read(String id) {
		YTDLItem item = null;
		
        String sql = "SELECT * FROM items WHERE id = ?";  
        
        try{
        	
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs    = pstmt.executeQuery();
            
            while (rs.next()) {
            	item = new YTDLItem();
            	item.setId(rs.getString("id"));
            	item.setUploadDate(rs.getString("upload_date"));
            	item.setTitle(rs.getString("title"));
           }
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
        
		return item;
	}
	
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
