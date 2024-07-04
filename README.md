# YTDLJExtension
Version 0.1.4-beta
  
Libreria che estende la gestione del download in mp3 di intere playlist utilizzando yt-dlp

## Note fondamentali
La libreria è stata creata in pochissime ore di sviluppo, quindi allo stato attuale conterrà sicuramente delle anomalie o degli errori ortografici nei messaggi di output. Inoltre questa guida è scritta senza porre particolari attenzioni. Chiaramente, seguiranno aggiornamenti. Riassumendo brevemente, è un progetto cotto e mangiato.

## Qual è il problema con yt-dlp (youtube-dl)?
Mentre lavoro al computer, mi concentro ascoltando musica di diversi generi, come rap, rock e musica elettronica (trance). Trovo molti contenuti validi su YouTube, ma ho riscontrato che youtube-dl, che uso per scaricare gli mp3 da canali con molti contenuti, ha problemi di gestione della cache quando si tratta di download massivi. Funziona bene per scaricare una lista limitata di brani, ma se cerco di scaricare lunghe liste di contenuti (magari filtrati per data), si verificano problemi che vanno risolti.

Seguo canali musicali con molti video e scarico solo i brani mese per mese impostando il range di date. Tuttavia, youtube-dl lavora sempre sull'intera lista dei video di un canale inclusi quelli fuori il range di date interessate e controlla la data di caricamento solo al momento del download, causando una perdita di tempo. Per risolvere questo problema, ho sviluppato una libreria aggiuntiva che ottimizza i download e salva informazioni utili in un database locale per scaricare solo i contenuti relativi alle date desiderate. Questo approccio ottimizza gli errori e ci consente di scaricare lunghe liste di contenuti in modo efficiente.

## Requisiti
  - Java 1.8+
  - L'eseguibile yt-dlp (la versione per linux yt-dlp_linux va rinominata in 'yt-dlp')
  - ffmpeg
  - ffplay
  - ffprobe
  - youtube.com_cookies.txt (file dei cookie di youtube estratto con il plugin cookies.txt disponibile per Firefox e Chrome)

## Come si usa YTDLJExtension
In una cartella caricare

  - YTDLJExtension-version.jar
  - L'eseguibile yt-dlp
  - ffmpeg
  - ffplay
  - ffprobe
  - youtube.com_cookies.txt

Aprire una shell e posizionarsi nella cartella che abbiamo creato, e digiare

    java -jar YTDLJExtension-version.jar

Seguire le richieste dello script, i file saranno scaricati nella sotto cartella 'download'

Dal primo utilizzo verà creato il database **YTDLJExtension.db** che non dovrà mai essere cancellato.

## Split mp3
La libreria si occupa anche di splittare i lunghi mix. Se, invece di inserire il link di un canale o di una playlist, viene inserito il link di un singolo video, lo script ci chiederà gli eventuali tempi per splittare un lungo remix. Gli offset vanno inseriti in questo formato

    00:00:00;00:02:30;00:05:47;ecc...

## Da fare
  - Migliorare il README.MD
  - Inserire check su eventuale nuova versione disponibile
  - Cancellare 10 tra gli id più vecchi (memorizzati nel database) di oltre un anno di anzianità ad ogni esecuzione, per liberare memoria da contenuti obsoleti. Eventualmente, se ancora presenti, il tool li riscaricherà con gli eventuali aggiornamenti
  - Riconoscimento degli ID dei contenuti live o programmati
  - Configurare dei lanciatori .bat e .sh per avviare lo script senza dover utilizzare ogni volta il comando **java -jar YTDLJExtension-version.jar**
  - Gestire selezione download audio (mp3), video (mp4) o sottotitoli
  - Aggiungere calcolo percentuale sulla lista dei download
  - Incorporare tutte le librerie necessarie nello stesso jar
  - Gestire la posizione del database nella stessa posizione del jar
  - Abilitare download video
  - Abilitare download sottotitoli generati automaticamente
  - Download playlist smart temporizzata: Inserita la playlist o il canale, ed inserito un minutaggio, il tool deve scaricare in una cartella una playlist smart casuale della durata esatta del minutaggio scelto
  - Gestire gli  ID3 tag in caso di download degli mp3
  - Leggere i cookie direttamente dal browser

## Ti gusta il progetto?
Mi fa piacere, e se ti va mi puoi offrire tipo [un caffè](https://ko-fi.com/francescoceliento)
