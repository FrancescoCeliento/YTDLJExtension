# YTDLJExtension
Version 0.1.2-beta
  
Libreria che estende la gestione del download in mp3 di intere playlist utilizzando youtube-dl

## Note fondamentali
La libreria è stata creata in 2 ore, quindi allo stato attuale conterrà sicuramente delle anomalie o degli errori ortografici nei messaggi di output. Inoltre questa guida è scritta senza porre particolari attenzioni. Chiaramente, seguiranno aggiornamenti.

## Qual è il problema con youtube-dl?
Sono una persona a caccia di canali musicali di ogni genere, dalla musica rap, a quella rock, a quella elettronica (trance), ed i canali di YouTube al riguardo sono carichi di contenuti validi, ma ahimè sono anche dispersivi. Ho sempre utilizzato youtube-dl per il download massivo di tutti gli mp3 da canali con più di 10 mila contenuti, il problema però è che youtube-dl ha una cattiva gestione della cache e dei download massivi che in qualche modo va ottimizzata, in quanto rischiamo di perderci il singolo brano o tutta la coda nel caso si verifichi anche un solo errore (perdita di connessione, video non disponibile, ecc...).

Solitamente, siccome si tratta di canali molto corposi, scarico solo i brani mese per mese impostando il range di date, ma youtube-dl lavora sempre su tutti gli id della playlist e controlla la data di caricamento solo al momento del download causando un irrimediabile perdita di tempo. Si è quindi visto necessaria una libreria aggiuntiva a youtube-dl, in grado di ottimizzare i download, e di salvare in un database in locale informazioni utili per scaricare esclusivamente contenuti relativi alle date interessate, e ottimizzando gli errori permettendoci di scaricare anche lunghe liste di contenuti.

## Come si usa YTDLJExtension
È necessario creare una cartella contenente

  - la libreria **youtube-dl**
  - la libreria **ffmpeg**
  - la libreria **ffplay**
  - la libreria **ffprobe**
  - il file dei cookie di youtube nominato accuratamente come **youtube.com\_cookies.txt**
  - la libreria **YTDLJExtension-version.jar** nell'ultima versione rilasciata

Ho scritto questa guida rapidamente, seguiranno aggiornamenti che prevederanno maggiore precisione

Per l'esecuzione posizionarsi con il command nella cartella dove abbiamo scaricato la libreria **YTDLJExtension-version.jar** ed eseguire il comando

    java -jar YTDLJExtension-version.jar

lo script ci chiederà di inserire i seguenti dati

  - Percorso della cartella che contiene le librerie necessarie (youtube-dl, ecc...)
  - Link della playlist da scaricare
  - Percorso di destinazione dei file mp3
  - Eventuale data d'inizio scritta in formato YYYYMMDD
  - Eventuale data di fine scritta in formato YYYYMMDD

Già dal primo utilizzo, nella cartella dove si trovano le librerie necessarie (youtube-dl, ecc...) vi ritroverete un nuovo file, chiamato **YTDLJExtension.db**. Non cancellare mai questo file. Si tratta di un file database SQLite che contiene le informazioni scaricate riguardo i contenuti delle playlist che scarichiamo. Serve per evitare di riscaricare nuovamente le informazioni di ogni video quando cerchiamo aggiornamenti su una playlist che controlliamo più volte. Per esempio, le playlist che scarico contengono oltre 10 mila brani ciascuna, con questo file database io evito di scaricare ogni volta le informazioni dei 10 mila brani, ma scarico solamente quelle dei brani recentemente inseriti.

## Da fare
  - Migliorare il README.MD
  - Inserire check su eventuale nuova versione disponibile
  - Cancellare 10 tra gli id più vecchi (memorizzati nel database) di oltre un anno di anzianità ad ogni esecuzione, per liberare memoria da contenuti obsoleti. Eventualmente, se ancora presenti, il tool li riscaricherà con gli eventuali aggiornamenti
  - Riconoscimento degli ID dei contenuti live o programmati
  - Configurare dei lanciatori .bat e .sh per avviare lo script senza dover utilizzare ogni volta il comando **java -jar YTDLJExtension-version.jar**
  - Gestire selezione download audio (mp3) o video (mp4)
  - Aggiungere calcolo percentuale sulla lista dei download
  - Incorporare tutte le librerie necessarie nello stesso jar
  - Gestire la posizione del database nella stessa posizione del jar
  - Abilitare download video
  - Abilitare download sottotitoli generati automaticamente
  - Download playlist smart temporizzata: Inserita la playlist o il canale, ed inserito un minutaggio, il tool deve scaricare in una cartella una playlist smart casuale della durata esatta del minutaggio scelto
  - Gestire gli  ID3 tag in caso di download degli mp3

## Ti gusta il progetto?
Mi fa piacere, e se ti va mi puoi offrire tipo [un caffè](https://ko-fi.com/francescoceliento)
