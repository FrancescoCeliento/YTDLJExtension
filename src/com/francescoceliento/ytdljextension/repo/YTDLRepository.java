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
		
		// Test utilizzo yt-dlp invece di youtube-dl
		//String cmd = "\"" + input.getYoutubeDLPath() + "\\youtube-dl\" --cookies \"" + input.getYoutubeDLPath() + "\\youtube.com_cookies.txt\" --playlist-reverse --flat-playlist --get-id " + input.getPlaylistUrl();
		String cmd = input.getYoutubeDLPath() + "/yt-dlp --compat-options no-youtube-unavailable-videos --cookies \"" + input.getYoutubeDLPath() + "/youtube.com_cookies.txt\" --playlist-reverse --flat-playlist --get-id " + input.getPlaylistUrl();
		String[] command = new String[] {
				input.getYoutubeDLPath() + "/yt-dlp",
                "--compat-options", "no-youtube-unavailable-videos",
                "--cookies", input.getYoutubeDLPath() + "/youtube.com_cookies.txt",
                "--playlist-reverse", "--flat-playlist",  
                "--get-id", input.getPlaylistUrl()
            };	
		
		Process process;
		try {
			process = Runtime.getRuntime()
					//.exec(cmd, null, new File("C:\\Users\\Francesco Celiento\\youtube-dl\\"));
					//.exec(cmd, null, null);
					.exec(command);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
//				if (line.startsWith("\"") && line.endsWith("\"")) {
//					line = line.substring(1, line.length() - 1);
//		        }
				itemLists.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return itemLists;
	}
	
	
	
	/*
	 * 
	 */
	public List<YTDLItem> getInfoVideo(List<String> requestList) {
		List<YTDLItem> resultList = new ArrayList<YTDLItem>();
		YTDLItem resultItem;
		
		//Preparo il command
		String cmd = input.getYoutubeDLPath() + "/yt-dlp --flat-playlist --cookies \"" + input.getYoutubeDLPath() + "/youtube.com_cookies.txt\" --get-filename -o \"%(id)s;%(upload_date)s;%(title)s\"";
		String[] command = new String[] {
				input.getYoutubeDLPath() + "/yt-dlp",
                "--flat-playlist",
                "--cookies", input.getYoutubeDLPath() + "/youtube.com_cookies.txt",
                "--get-filename", "-o", "%(id)s;%(upload_date)s;%(title)s"
            };		
		
		for (String requestItem : requestList) {
			cmd += " https://www.youtube.com/watch?v=" + requestItem;
			
			String[] nuovoCommand = new String[command.length + 1];
			for (int i = 0; i < command.length; i++) {
				nuovoCommand[i] = command[i];
			}
			nuovoCommand[command.length] = "https://www.youtube.com/watch?v=" + requestItem;
			command = nuovoCommand; 
		}
		
		//Eseguo il processo di recupero date
		Process process;
		try {
			process = Runtime.getRuntime()
					//.exec(cmd, null, null);
					.exec(command);
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			String[] split;
			while ((line = reader.readLine()) != null) {
//				if (line.startsWith("\"") && line.endsWith("\"")) {
//					line = line.substring(1, line.length() - 1);
//		        }
				split = line.split(";");
				resultItem = new YTDLItem();
				resultItem.setId(split[0]);
				resultItem.setUploadDate(split[1]);
				resultItem.setTitle(split[2]);
				
				resultList.add(resultItem);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	
		
	/*
	 * 
	 */
	public boolean download(YTDLItem item, boolean ifsplitting) {
	
		boolean result;
		String patternFile = ifsplitting ? item.getId() + ".%(ext)s" : "%(upload_date)s-%(title)s-%(id)s.%(ext)s";
		
		// Test utilizzo yt-dlp invece di youtube-dl
		String[] command = new String[] {
				input.getYoutubeDLPath() + "/yt-dlp",
                "--extract-audio",
                "--cookies", input.getYoutubeDLPath() + "/youtube.com_cookies.txt",
                "--ffmpeg-location", input.getYoutubeDLPath(),
                "--audio-format", "mp3",
                "-o", input.getDownloadDirectory() + "/" + patternFile,
                "https://www.youtube.com/watch?v=" + item.getId()
            };
		
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
						.exec(command);
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				String lastLine = "";
				
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("[download]")) {
						downloadCheck++;
						lastLine = line;
					}
				}
				reader.close();
				
				if (lastLine!="")
					System.out.println(lastLine);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		
		
		if (downloadCheck <1) {
			result = false;
		} else {
			result = true;
		}
		
		return result;
	}

}
