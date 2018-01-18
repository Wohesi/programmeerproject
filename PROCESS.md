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

# day 8 - 17-1-2017
Aan de hand van de volgende tutorial is het filteren van de zoekfunctie werkend: 
https://www.learn2crack.com/2017/03/searchview-with-recyclerview.html

~~De adapter plaatst nu alleen de verkeerde elementen op de verkeerde positie.~~
Positie can de textview is is nu correct. Momenteel is de truc om de juiste ID binnen een XML tag te verkrijgen van de XML pullparser

# day 9 - 18-1-2017
De API van boardgamegeek heeft onderhoud, vandaar dat ik niet kan werken aan de boardgame tabs, of testen hoe de juiste XML tag te verkrijgen. 
zie: https://boardgamegeek.com/thread/1921913/xmlapi-outages-planned
In plaats hiervan ben ik begonnen aan de fragments voor de calender pagina. 
Voor de largeboardgamefragment heb ik de fundatie gelegd. De loadData() functie, zoals deze ook in de searchfragment is geimplementeerd.
Een diagonale achtergrond toegevoegd aan de hand van de volgende tutorial: 
https://medium.com/@adinugroho/create-diagonal-cut-view-in-android-5a376eca6a1c
