package com.francescoceliento.ytdljextension.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.francescoceliento.ytdljextension.db.SQLLiteDB;
import com.francescoceliento.ytdljextension.model.YTDLInput;
import com.francescoceliento.ytdljextension.model.YTDLItem;
import com.francescoceliento.ytdljextension.repo.Logger;
import com.francescoceliento.ytdljextension.repo.YTDLRepository;

public class YTDLService {
	
	private YTDLInput input;
	private YTDLRepository repo;
	Logger logger;
	SQLLiteDB database;
	
	
	public YTDLService(YTDLInput input) {
		this.input = input;
		database = new SQLLiteDB(input.getYoutubeDLPath());
		repo = new YTDLRepository(input);
	}
	
	public List<YTDLItem> getIdList() {
					
		//Ricevo la lista degli ID
		System.out.println("Inizio procedura di ricezione di tutti gli item della playlist");
		System.out.println("La durata dell'operazione dipende dal numero di video contenuto nella playlist...");
		List<String> idList = repo.getIDLists();
		System.out.println("Finita la procedura di ricezione di tutti gli item della playlist");
		System.out.println("Sono stati letti " + idList.size() + " elementi");
		
		System.out.println("Ricavo le date degli elementi");
		List<YTDLItem> itemList = getInfoPlaylist(idList);
		System.out.println("Sono state ricavate le date di " + itemList.size() + " elementi");
			
		if (!input.isFiltered()) {
			System.out.println("Non è stata impostato un filtro sulle date, saranno scaricati tutti gli item rilevati");
			return itemList;
		}
		
		//Filtro i video per la data interessata
		System.out.println("Filtro gli elementi per le date interessate");
		List<YTDLItem> itemListToDownload = filterList(itemList);
		System.out.println("La nuova lista contiene " + itemListToDownload.size() + " elementi");
		
		return itemListToDownload;
	}
	
	private List<YTDLItem> getInfoPlaylist(List<String> idList) {
		
		List<YTDLItem> resultList = new ArrayList<YTDLItem>();
		List<String> searchList = new ArrayList<String>();
		int countItem;
		YTDLItem item;
		
		//Avvio il database
		database.checkDatabase();
		
		/* Eseguo prima la ricerca nel database locale
		 * se non trovo gli elementi inserisco l'id nell'array della request
		 * che sarà fatto dopo 
		 */
		System.out.println("Ricerco gli ID della playlist dal database locale");
		countItem = 0;
		for (String idVideo : idList) {
			item = database.read(idVideo);
			
			if (item!= null) {
				countItem++;
				resultList.add(item);
			} else {
				searchList.add(idVideo);
			}
		}
		System.out.println("Sono stati rilevati dal database " + countItem + " elementi di " + idList.size());
		
		
		/* Eseguo la procedura di richiesta info per tutti gli ID che non erano presenti
		 * nel database
		 */
		System.out.println("Rilevo le informazioni per gli ID sconosciuti");
		countItem = 0;
		int maxElement = 50;
		List<String> requestList = new ArrayList<String>();
		for (String searchItem : searchList) {
			countItem++;
			requestList.add(searchItem);
			
			// Ogni 50 elementi faccio una richiesta e salvo gli ID nel database
			if (countItem % maxElement == 0) {
				List<YTDLItem> resultSearchList = repo.getInfoVideo(requestList);
				for (YTDLItem resultSearchItem : resultSearchList) {
					database.insert(resultSearchItem);
					resultList.add(resultSearchItem);
				}
				System.out.println("Rilevati " + countItem + " nuovi elementi di " + searchList.size());
				requestList = new ArrayList<String>();
			}
		}
		
		// Ricerco i residui
		if (requestList.size() > 0) {
			List<YTDLItem> resultSearchList = repo.getInfoVideo(requestList);
			for (YTDLItem resultSearchItem : resultSearchList) {
				database.insert(resultSearchItem);
				resultList.add(resultSearchItem);
			}
		}
		System.out.println("Sono state aggiunte " + countItem + " nuove definizioni nel database");
		
		if (resultList.size() != idList.size()) {
			System.out.println("ATTENZIONE: Il numero di ID scaricati non equivale al numero di ID per la quale abbiamo ricevuto le informazioni. È possibile che tu abbia perso la connessione ad internet durante l'interrogazione. Premerere INVIO per continuare oppure CTRL+C per fermare la procedura e riavviarla.");
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		
		//Chiudo il database perché non mi serve più
		database.close();
		
		return resultList;
	}
	
	private List<YTDLItem> filterList(List<YTDLItem> completeList) {
		List<YTDLItem> newList = new ArrayList<YTDLItem>();
		
		for (YTDLItem ytdlItem : completeList) {
			if (input.isValid(ytdlItem.getUploadDate()))
				newList.add(ytdlItem);
		}
		
		return newList;
	}
	
	public void runDownload(List<YTDLItem> items) {
		
		Logger logger = new Logger(input.getDownloadDirectory(),"LogError.txt");
		
		int count = 0;
		
		for (YTDLItem ytdlItem : items) {
			count++;
			System.out.println("Download elemento " + count + " di " + items.size());
			if (checkExistingFile(input.getDownloadDirectory(),ytdlItem.getFileName())) {
				System.out.println("Il file " + ytdlItem.getFileName() + " esiste già");
			} else if (!repo.download(ytdlItem)) {
				System.out.println("Qualcosa è andato storto, inserisco il comando di download nel file di log");
				logger.log("Errore durante il download di " + ytdlItem.getTitle());
				logger.log("Esegui il seguente comando da terminale per scaricarlo manualmente");
				String cmdError = "\"" + input.getYoutubeDLPath() + "\\youtube-dl\" --extract-audio --audio-format mp3 -o \"" + input.getDownloadDirectory() + "\\%(upload_date)s-%(title)s-%(id)s.%(ext)s\" --cookie \"" + input.getYoutubeDLPath() + "\\youtube.com_cookies.txt\" https://www.youtube.com/watch?v=" + ytdlItem.getId();
				logger.log(cmdError);
			}
		}
		
		logger.close();
	}
	
	public boolean checkExistingFile (String path, String fileName) {
		
		File file = new File (path + "\\" + fileName);
		return file.exists();
	}

}
