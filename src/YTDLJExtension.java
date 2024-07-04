import java.io.File;
import java.net.URISyntaxException;
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
		
		String path;
		
		try (Scanner console = new Scanner(System.in)) {
			
			YTDLInput input = new YTDLInput();
			String inputKey;
			
			String jarPath = new File(YTDLJExtension.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
			path = new File(jarPath).getParent();
			//path = "/home/francesco/YTDLJExtension";
			
			input.setYoutubeDLPath(path);
			
			System.out.println("Inserisci il link della playlist o devo video da scaricare (OBBLIGATORIO)");
			inputKey = console.nextLine();
			if (inputKey!="") {
				input.setPlaylistUrl(inputKey);
			}
			
			if (input.getPlaylistUrl().toLowerCase().contains("/watch?v=")) {
				System.out.println("Hai inserito un download singolo, inserisci l'eventuale preset per lo splitting del file mp3 se troppo lungo separati da ;");
				inputKey = console.nextLine();
				if (inputKey!="" && inputKey.length()>0) {
					input.setSplitTime(inputKey);
				}
			}
			
			//TODO: Testiamo salvataggio nella cartella output della libreria
			/*System.out.println("Inserisci la cartella dove verranno salvati gli mp3 scaricati (OBBLIGATORIO)");
			inputKey = console.nextLine();
			if (inputKey!="") {
				input.setDownloadDirectory(inputKey);
			}*/
			input.setDownloadDirectory(path+"/download");
			
			if (!input.isSplitting()) {
				System.out.println("Inserisci la data di interesse di inizio (YYYYMMDD) ");
				inputKey = console.nextLine();
				if (inputKey!="" && inputKey.length()>0) {
					input.setDateBegin(inputKey);
				}
				
				System.out.println("Inserisci la data di interesse di fine (YYYYMMDD)");
				inputKey = console.nextLine();
				if (inputKey!="" && inputKey.length()>0) {
					input.setDateEnd(inputKey);
				}				
			}
			
			YTDLService service = new YTDLService(input);
			
			System.out.println("Estraggo la lista completa degli ID della playlist...");
			List<YTDLItem> itemList = service.getIdList();
			System.out.println("Estrazione della lista completa terminata");			
			
			System.out.println("Avvio il download");
			service.runDownload(itemList, input.isSplitting(), input);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
			
		System.out.println("Fine procedura di importazione playlist");
		
	}

}
