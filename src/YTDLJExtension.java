import java.util.List;
import java.util.Scanner;

import com.francescoceliento.ytdljextension.model.YTDLInput;
import com.francescoceliento.ytdljextension.model.YTDLItem;
import com.francescoceliento.ytdljextension.service.YTDLService;

//https://github.com/sapher/youtubedl-java/releases
//https://search.maven.org/search?q=g:org.json%20AND%20a:json



public class YTDLJExtension {
	
	public static void main(String[] args) {
		System.out.println("Inizio procedura di importazione playlist");
		
		try (Scanner console = new Scanner(System.in)) {
			
			YTDLInput input = new YTDLInput();
			String inputKey;
			
			System.out.println("Inserisci la posizione di youtube-dl con tutte le librerie (OBBLIGATORIO)");
			inputKey = console.nextLine();
			if (!inputKey.isBlank()) {
				input.setYoutubeDLPath(inputKey);
			}
			
			System.out.println("Inserisci il link della playlist da scaricare (OBBLIGATORIO)");
			inputKey = console.nextLine();
			if (!inputKey.isBlank()) {
				input.setPlaylistUrl(inputKey);
			}
			
			System.out.println("Inserisci la cartella dove verranno salvati gli mp3 scaricati (OBBLIGATORIO)");
			inputKey = console.nextLine();
			if (!inputKey.isBlank()) {
				input.setDownloadDirectory(inputKey);
			}
			
			System.out.println("Inserisci la data di interesse di inizio (YYYYMMDD) ");
			inputKey = console.nextLine();
			if (!inputKey.isBlank()) {
				input.setDateBegin(inputKey);
			}
			
			System.out.println("Inserisci la data di interesse di fine (YYYYMMDD)");
			inputKey = console.nextLine();
			if (!inputKey.isBlank()) {
				input.setDateEnd(inputKey);
			}
			
			YTDLService service = new YTDLService(input);
			
			System.out.println("Estraggo la lista completa degli ID della playlist...");
			List<YTDLItem> itemList = service.getIdList();
			System.out.println("Estrazione della lista completa terminata");			
			
			System.out.println("Avvio il download");
			service.runDownload(itemList);
		}
			
		System.out.println("Fine procedura di importazione playlist");
		
	}

}
