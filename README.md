# SofaScoreAcademy
Ovaj repozitorij korišten je za rješavanje i objavljivanje rješenja zadaća sa SofaScore android akademije 2021.
Kao finalni rezultat zadaća uz predavanje nastala je jednostavna aplikacija vremenske prognoze. U aplikaciji su pokrivena sva područja akademije
od Networkinga (Retrofit), baze podataka (Room), Courutina...
U budućnosti ću pokušati još više poboljšati ovu aplikaciju i dodati/popraviti određene dijelove.

### 1.DZ
Hello World projekt
  - Instalirati Android Studio i Kotlin plugin
  - Kreirati Github account
  - Kreirati novi Android projekt i povezati ga s Github repozitorijem
  - Napraviti inicijalni commit s readme datotekom
  - Dodati gitignore
  - Napraviti Hello World Activity
    - mora imati Button i TextView
    - na pritisak Buttona se TextView prikazuje/skriva, te se mijenja tekst Buttona
    
### 2.DZ
- MVVM Activity s dva povezana Fragmenta
  - Napraviti Activity s dva Fragmenta
  - Napraviti custom klasu s bar 5 parametara 
  - Napraviti ViewModel koji sprema listu objekata vlastite klase
  - Povezati fragmente na način da jedan služi za dodavanje
    u listu, a drugi prikazuje objekte iz liste
    
### 3.DZ
  - Ako niste, ostvarite ponašanje prethodne zadaće pomoću Bottom Navigation Activityja
    - kreirajte prema predlošku
    - dodajte view model
    - promijenite nazive i ikone (svg) u izborniku
  - Unaprijediti layoute koristeći:
    - ConstraintLayout
    - Novi font
    - Barem jedan drawable resurs za pozadinu
  - Dodajte nove parametre svojim model klasama
    - barem jedan mora biti custom enum koji je napravljen kao spinner
    - barem jedan mora biti izveden pomoću radio buttona
  - Unaprijedite kod i logiku dodavanja objekata
    - ViewBinding
    - Svaki parametar mora imati vlastitu provjeru ispravnosti
    podataka (neprazan string, Int vrijednost u ispravnom rasponu, ...)
  - Koristiti ArrayAdapter za listu svojih objekata
  
### 4.DZ
  - Napraviti custom app bar
    - Kreirati i koristiti vlastitu temu
  - Napraviti custom status bar
  - Dodati treći fragment na main activity
    - Neka se zove settings
    - U njemu napraviti lokalizaciju aplikacije - language chooser (podrška za hr i en)
  - Unaprijediti listu itema
    - Dodati polje za sliku
    - Implementirati RecyclerView
    - Prikazivati osnovne podatke o objektu
    - Koristiti coil/Picasso za dohvaćanje slike
  - Dodati CollapsibleToolbarActivity za prikaz specifičnog objekta na klik u recycleru
    - U njemu napraviti detaljan pregled objekta i svih njegovih parametara
  - Dodati Toast i Snackbar u neki dio flowa

## WeatherApp

### 5.DZ
  - Implementirati networking
    - Dodati retrofit i gson
    - Kreirati sve modele za traženi api
    - Implementirati sve endpointove
  - Koristiti Kotlin Coroutines za network pozive
    - Dodati podršku hvatanje error-a
    - Implementirati dizajn bottom navigation layouta
  - Implementirati dizajn i funkcionalnost Search Fragmenta
    - Location Search endpoint
  - Implementirati dizajn i funkcionalnost City Activityja
    - Location endpoint

### 6.DZ
  - Implementirati Room bazu podataka i koristiti ju za spremanje
    - Pretraga gradova
    - Favorita
  - Kreirati repozitorij koji upravlja spremanjem podataka 
      dohvaćenih s endpointa u bazu
  - Implementirati dizajn i funkcionalnost My Cities fragmenta
  - Mogućnost uklanjanja i reorder-anja gradova

### 7.DZ
  - Dovršiti Weather app
    - popuniti rupe iz prethodne dvije zadaće
    - ispeglati dizajn
    - implementirati Settings fragment
  - Opcionalno
    - dodati GoogleMaps API
    - kreirati vlastiti widget
