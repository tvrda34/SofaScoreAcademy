# SofaScoreAcademy
Repozitorij za rješavanje i objavljivanje rješenja zadaća SofaScore android akademije 2021.

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
