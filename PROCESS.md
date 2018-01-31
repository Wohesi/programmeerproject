# day 1 - 9-1-2018
UI design aangepast en design document begonnen

# day 2 - 10-1-2018
Design document af gemaakt en gevraagd om feedback aan mede studenten

# day 3 - 11-1-2018
Main activity gemaakt met sliders en desbetreffende xml layout bestanden

# day 4 - 12-1-2018
Begin gemaakt aan de calendar / planner screen en zoekfunctie / xml parser d.m.v. 
https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview https://developer.android.com/training/basics/network-ops/xml.html

# day 5 - 15-1-2018
Gewerkt aan de searchview functionaliteit / XML parser om de jusite data te krijgen van de API - XML parser aan de searchview geprobeerd te koppelen, zodat de juiste URL binnengehaald wordt. Dit lukt nog niet 22:27 update: XML parsen werkend gekregen, voor 1 maal: nu Volley timeout error

# day 6 - 15-1-2018
De volleyrequest werkt nog niet naar behoren. Nogsteeds een timeouterror. Ook de corresponderende id uit de xml tag moet nog eruit gehaald worden.
Een begin gemaakt aan de adaptor voor de recycler view voor de searchfragment. Echter, dankzij de TimeOutError is dit moeilijk te testen.

# day 7 - 16-1-2018
De adapter en bijbehorende classe gemaakt om ervoor te zorgen dat de searchview een goede lijst van items laat zien. De TimeoutErrors van de vorige dagen kwamen door mankementen bij de server van de API, waar ik geen invloed op had. 

# day 8 - 17-1-2018
Aan de hand van de volgende tutorial is het filteren van de zoekfunctie werkend: 
https://www.learn2crack.com/2017/03/searchview-with-recyclerview.html

~~De adapter plaatst nu alleen de verkeerde elementen op de verkeerde positie.~~
Positie can de textview is is nu correct. Momenteel is de truc om de juiste ID binnen een XML tag te verkrijgen van de XML pullparser

# day 9 - 18-1-2018
De API van boardgamegeek heeft onderhoud, vandaar dat ik niet kan werken aan de boardgame tabs, of testen hoe de juiste XML tag te verkrijgen. 
zie: https://boardgamegeek.com/thread/1921913/xmlapi-outages-planned
In plaats hiervan ben ik begonnen aan de fragments voor de calender pagina. 
Voor de largeboardgamefragment heb ik de fundatie gelegd. De loadData() functie, zoals deze ook in de searchfragment is geimplementeerd.
Een diagonale achtergrond toegevoegd aan de hand van de volgende tutorial: 
https://medium.com/@adinugroho/create-diagonal-cut-view-in-android-5a376eca6a1c

# day 10 - 19-1-2018
De loadData functie haalt nu ook de juiste ID op van een bordspel, nu kan ik met dit ID de volgende query uitvoeren om meer data te krijgen over een specifiek bordspel. 

# day 11 - 20-1-2018
WEEKEND

# day 12 - 21-1-2018
WEEKEND - Ondanks het weekend, heb ik alsnog de XML voor een specifiek boardgame fragment werkend gekregen. 

# day 13 - 22-2-2018
Google calendar API zorgt voor veel problemen met authenticatie etc. Ik heb hierdoor gekozen om google calendar api te laten vervallen en gebruik te maken van firebase om zo gebruikers evenementen te maken.

De registratie / login gemaakt. Ook zijn de dialogfragments om een tijd, datum en titel te kiezen gemaakt. Deze zullen in een firebase database gestopt worden, en de user kan deze eruit halen op het events fragment.

De html / xml markup is uit de beschrijving van de spellen gehaald. 

# day 14 - 23-1-2018
De dialog views geven nu de juiste data terug. Er kan een naam, tijd en datum geselecteerd worden voor een bepaald evenement en deze worden op geslagen. 
Dit is gedaan aan de hand van de volgende stackoverflow hulp: 
https://stackoverflow.com/questions/18579590/how-to-send-data-from-dialogfragment-to-a-fragment
https://stackoverflow.com/questions/43866472/android-custom-dialog-view-change-button-color-before-showing-for-first-time

het loginscherm aangepast zodat er niet een aparte activity gemaakt hoeft te worden. 
Ook een hoop anonymous listeners verwijderd, onnodige imports verwijderd en comments toegevoegd zodat de code er netter uit ziet. 

# day 15 - 23-1-2018
De data van een evenement wordt nu op de juiste manier in firebase gezet. Dit is gedaan aan de hand van het volgende voorbeeld: 
https://stackoverflow.com/questions/37688031/class-java-util-map-has-generic-type-parameters-please-use-generictypeindicator

De eventfragment heeft nu een aparte adapter en de data wordt opgehaald en in een adapter gezet, dit werkt nog niet helemaal naar behoren. 

# day 16 - 24-1-2018
Begin gemaakt om de data goed uit firebase te halen.

# day 17 - 25-1-2018
d.m.v. overleg met mijn daily standup groep heb ik op de juistemanier de elementen uit firebase kunnen verkijgen in inladen in de adapterview. Nu moet er gekeken worden naar een auto incement voor voorfirebase zodat meerdere unieke evenementen kunnen worden toegevoegd aan firebase zonder dat er duplicaten zijn. 

Zodra de searchview een lijst van resultaten terug krijgt pakt hij altijd of het eerste of het laatstegenoteerde ID, er moet een oplossing voor komen dat de jusite ID bij de juiste searchview komt. 

~~Voor het vertonen van de evenementen moet een auto increment tussen, zodat de jusite elementen worden laten zien. ~~
auto increment werkt nu voor evenementen per user,alleen reset de auto increment. moet hiervoor naar een oplossing zoeken.

Ook voor het toevoegen van de evenemtnen vanuit firebase moet op een betere manier gedaan worden. 

# day 18 - 26-1-2018
De auto increment was een count, maar deze is vervangen voor een UUID: 
https://stackoverflow.com/questions/1389736/how-do-i-create-a-unique-id-in-java
ook de eventsfragment waar de events worden gegenereerd is aangepast zodat de het duidelijk erin zit. Echter, de juist elementen worden nog niet geladen.

# day 19 - 27-1-2018
WEEKEND

# day 20 - 28-1-2018
WEEKEND

# day 21 - 29-1-2018
De ID's worden nu doorgestuurd naar en het juiste spel wordt geladen. Dit maakt de zoekfunctie handiger. Ook worden de evenementen goed geladen bij de juiste blokken
Gewerkt aan de UI met betrekking tot placeholder images en app icon aangepast

# day 22 - 30-1-2018
Aan de hand van de code review zijn er anonymous listeners weggehaald en is code opgedeeld in meer methods om het kleiner en overzichtelijker te maken. Ook zijn er nog enkele comments toegevoegd om het leesbaarder te maken. Ook worden de evenementen goed ingeladen zodra een gebruiker in of uitlogt. Dit is gedaan samen met een student in mijn groepje die mij hierbij heeft geholpen. 
~~De titel van een spel wordt nogsteeds niet goed geladen, de XML pullparser werkt nog niet. Dit is de laatste bug.~~
XML pull parser haalt nu de juiste titel op en zet deze op de correcte plek

# day 23 - 31-1-2018
Ik heb meer mensen gevraag om hun mening met betrekking tot de zoekresultaten. De keuze was of er specifiek één resultaat getoont moest worden, of meerdere. Het gevolg want het laatstenoemde is dat de kans aanwezig is dat er ontzettend veel resultaten komen. Echter, de feedback gaf aan dat dit meer gewenst is dan een kleine hoeveelheid opties. 

ook is de button om een evenement weggehaald zodat als eenuser niet is ingelogd er ook geen evenement aangemaakt kan worden.
