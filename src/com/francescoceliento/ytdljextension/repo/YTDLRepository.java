package com.francescoceliento.ytdljextension.repo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.francescoceliento.ytdljextension.model.YTDLInput;
import com.francescoceliento.ytdljextension.model.YTDLItem;

public class YTDLRepository {
	
	YTDLInput input;
	
	public YTDLRepository(YTDLInput input) {
		super();
		this.input = input;
	}
	
	
	/*
	 * 
	 */
	public List<String> getIDLists() {
		
		List<String> itemLists = new ArrayList<String>();
		
		String cmd = "\"" + input.getYoutubeDLPath() + "\\youtube-dl\" --cookie \"" + input.getYoutubeDLPath() + "\\youtube.com_cookies.txt\" --playlist-reverse --flat-playlist --get-id " + input.getPlaylistUrl();
		
		Process process;
		try {
			process = Runtime.getRuntime()
					//.exec(cmd, null, new File("C:\\Users\\Francesco Celiento\\youtube-dl\\"));
					.exec(cmd, null, null);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				itemLists.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		      //.exec("sh -c ls", null, new File("Pathname")); for non-Windows users
		
		return itemLists;
	}
	
	
	
	/*
	 * 
	 */
	public List<YTDLItem> getInfoVideo(List<String> requestList) {
		List<YTDLItem> resultList = new ArrayList<YTDLItem>();
		YTDLItem resultItem;
		
		//Preparo il command
		String cmd = "\"" + input.getYoutubeDLPath() + "\\youtube-dl\" --cookie \"" + input.getYoutubeDLPath() + "\\youtube.com_cookies.txt\" --flat-playlist --get-filename -o \"%(id)s;%(upload_date)s;%(title)s\"";
		
		for (String requestItem : requestList) {
			cmd += " https://www.youtube.com/watch?v=" + requestItem;
		}
		
		//Eseguo il processo di recupero date
		Process process;
		try {
			process = Runtime.getRuntime()
					//.exec(cmd, null, new File("C:\\Users\\Francesco Celiento\\youtube-dl\\"));
					.exec(cmd, null, null);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			String[] split;
			while ((line = reader.readLine()) != null) {
				split = line.split(";");
				resultItem = new YTDLItem();
				resultItem.setId(split[0]);
				resultItem.setUploadDate(split[1]);
				resultItem.setTitle(split[2]);
				
				resultList.add(resultItem);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		      //.exec("sh -c ls", null, new File("Pathname")); for non-Windows users
		return resultList;
	}
	
		
	/*
	 * 
	 */
	public boolean download(YTDLItem item) {
	
		boolean result;
			
		String cmd = "\"" + input.getYoutubeDLPath() + "\\youtube-dl\" --extract-audio --audio-format mp3 -o \"" + input.getDownloadDirectory() + "\\%(upload_date)s-%(title)s-%(id)s.%(ext)s\" --cookie \"" + input.getYoutubeDLPath() + "\\youtube.com_cookies.txt\" https://www.youtube.com/watch?v=" + item.getId();
		
		int downloadCheck = 0;
		int attemps = 0;
		
		System.out.println("Eseguo il download di " + item.getTitle());
		while (downloadCheck < 1 && attemps <10) {
			attemps++;
			downloadCheck = 0;
			
			if (downloadCheck <1 && attemps>1) {
				System.out.println("Download non riuscito, tentativo " + attemps + " di 10");
			}
			
			Process process;
			try {
				process = Runtime.getRuntime()
						//.exec(cmd, null, new File("C:\\Users\\Francesco Celiento\\youtube-dl\\"));
						.exec(cmd, null, null);
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = "";
				String lastLine = "";
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("[download]")) {
						downloadCheck++;
						lastLine = line;
						
					}
				}
				
				if (!lastLine.isBlank())
					System.out.println(lastLine);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		
		if (downloadCheck <1) {
			result = false;
		} else {
			result = true;
		}
		
		
		      //.exec("sh -c ls", null, new File("Pathname")); for non-Windows users
		return result;
	}

}
