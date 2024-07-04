package com.francescoceliento.ytdljextension.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class SplitMP3Utils {
	
	
	public boolean split(String path, String fileName, String splitText) {
		
		int exitCode = 0;

		try {
			// Ottengo la durata totale dell'mp3
			String filePath = path + "/download/" + fileName + ".mp3";
			
			Mp3File mp3File = new Mp3File(filePath);
			long durataTotaleSecondi = mp3File.getLengthInSeconds();
			// Calcola le ore, i minuti e i secondi
	        long ore = durataTotaleSecondi / 3600;
	        long minuti = (durataTotaleSecondi % 3600) / 60;
	        long secondi = durataTotaleSecondi % 60;
	        String durataTotale = String.format("%02d:%02d:%02d", ore, minuti, secondi);
	        
			// Verifica se la stringa non inizia con "0:00" o "00:00"
	        if (!splitText.startsWith("00:00:00")) {
	            // Aggiungi "00:00;" all'inizio della stringa
	        	splitText = "00:00:00;" + splitText;
	        }
	        
	       if (!splitText.endsWith(durataTotale)) {
	        	splitText = splitText + ";" + durataTotale; 
	        }
	        
	        //elimino eventuali ; doppi
	        splitText = splitText.replace(";;", ";");
		
	        
	        String[] splitTime = splitText.split(";");
			
			for (int i = 0; i < splitTime.length; i++) {
				final String time = splitTime[i];

				final int nextSong = i + 1;
				if (nextSong < splitTime.length) {
					final String nextTime = splitTime[nextSong]; //This gets the starting time of the next song in the main MP3
																			
					final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					final Date currentSongTime = sdf.parse(time);
					final Date nextSongTime = sdf.parse(nextTime);

					final long duration = nextSongTime.getTime() - currentSongTime.getTime(); // calculate how long  the current song  is

					final long diffInSeconds = TimeUnit.MILLISECONDS
							.toSeconds(duration); // ffmpeg uses the total
													// seconds you want to pass
													// before cutting the file

					String command = path + "/ffmpeg -i %s -ss %s -t %s -acodec copy %s.mp3";
					
					
					command = String.format(command, filePath, time, 
							diffInSeconds, path + "/download/" + fileName + "-" + nextSong); // generate
																	// command

					final Runtime rt = Runtime.getRuntime();
					final Process proc = rt.exec(command);
					int exitProcCode = proc.waitFor();
					if (exitProcCode !=0)
						exitCode = exitProcCode; 
					
					proc.destroy();
				}
			}
			
		} catch (final IOException | ParseException e) {
			e.printStackTrace();
		} catch (UnsupportedTagException e) {
			e.printStackTrace();
		} catch (InvalidDataException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return exitCode == 0;
	}
}
