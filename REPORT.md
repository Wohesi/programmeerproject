# Beschrijving
Deze app kan gebruikt worden om bordspellen en de bijbehorende informatie te vinden. Zodra deze gevonden is kan de gebruiker een evenement aanmaken om een spel te spelen. 
![sc1](https://github.com/Wohesi/programmeerproject/blob/master/doc/final%20screenshots/searchfragments.png)

-------

# Technisch ontwerp

### SearchFragment.java
![sc1](https://github.com/Wohesi/programmeerproject/blob/master/doc/final%20screenshots/searchfragments.png)
* Het **SearchFragment.java** laad de data in vanuit de Boardgamegeek API. Deze API heeft een zoekfunctie waarmee naar bepaalde spellen gezocht kan worden. Dit fragment toont de resultaten van deze zoekquery.
* **SearchTile.java:** Class voor een searchtile. Hierin worden naam, jaar van uitgave en ID gezet en opgehaald. 
* **SearchAdapter.java:** Adapter classe voor de recyclerview zodat recyclerview gevult wordt met cards.
* **BlankFragment.java** Een lege fragment die gewisseld wordt zodra er gewisseld wordt tussen de LargeBgFragment en SearchView om te voorkomen dat er per ongeluk op een tile in de achtergrond wordt geklikt. 

### LargeBgFragment.java
![sc2](https://github.com/Wohesi/programmeerproject/blob/master/doc/final%20screenshots/largebgfragment.png)
* Maakt gebruik van de boardgame geek API doormiddel van een uniek ID van een spel. Dit ID is opgehaald in de SearchFragment.

### EventsFragment
![sc3](https://github.com/Wohesi/programmeerproject/blob/master/doc/final%20screenshots/eventsfragment.png)
* In het EventsFragment worden evenementen vanuit de firebase database opgehaald. Deze wordt net zoals de SearchFragment gevuld door middel van een recyclerview.  
* **Event.java:** Class voor het zetten en en ophalen van de gegevens van een evenement. Het gaat om de naam, datum en tijd van een evenement. 
* **EventAdapter.java:** Adapter class om de recycler view op de juiste manier te vullen met eventcards. 

### NewEventFragment.java
![sc4]()
*
*
*

## LoginFragment.java
![sc5](https://github.com/Wohesi/programmeerproject/blob/master/doc/final%20screenshots/userfragments.png)
* In het loginfragment kan worden ingelogd. Dit fragment wordt geupdate zodra een gebruiker is ingelogd, of als een gebruiker zich uitlogd. 
* In het registerfragment kan een gebruiker zich registreren. 

-------

# Uitdagingen
Gedurende het project ben ik tegen een aantal obstakels heen gelopen. Allereerst vond ik parsen van de XML data via de XmlPullParser een stuk ingewikkelder dan via een JSON string request. Ook het combineren en uitvogelen van hoe een XML bestand verwerkt wordt en hoe je het juiste element verkrijgt en opslaat was een uitdaging. Het heeft enige tijd gekost om de juiste elementen te verkrijgen. Daarnaast was de API van boardgamegeek anders ingedeeld dan ik had verwacht, en moest data op verschillende manieren verkregen worden vanuit de API. Zo waren bijvoorbeeld plaatjes of andere informatie via andere queries te verkrijgen. 

Daarnaast was het een uitdaging om connectie te maken via de google calendar. Hiervoor was een OAuth2 verificatie vereist. Dit koste mij veel moeite om te voor elkaar te krijgen. Aangezien de API waar ik hoofdzakelijk gebruik van maakte offline was en slecht werkte voor enige tijd vanwege een DDoS aanval (1), had ik veel tijd verspild aan het testen van mijn code, aangezien ik het vermoeden had dat ik fouten maakte, waardoor er vaak een timeout error verscheen.

 Gezien de tijd leek het mij de beste optie om af te stappen van de google calendar API en gebruik te maken van Firebase. Op deze manier kunnen mensen een account aanmaken op de app en via deze manier evenementen maken, in plaats van via de google calendar app.  
De omschakeling naar Firebase ging over het algemeen goed, hoewel ik moeite had met de juiste data uit de database halen, is dit uiteindelijk gelukt. Ook was het een uitdaging om te kijken hoe er geen dubbele evenementen gemaakt konden worden met dezelfde attributen. Dit is uiteindelijk opgelost met een uniek ID voor elk evenement. 
Echter, dankzij deze verandering is het niet gelukt om een interactie tussen de gebruikers te implementeren. 
1[https://boardgamegeek.com/thread/1924416/bgg-being-targeted-distributed-denial-service-ddos]

-------

# Keuzes en veranderingen
Ik heb gekozen om af te stappen van google calendar omdat ik al veel tijd heb verloren met de DDoS aanval op de boardgamegeek API. Aangezien er twee á drie dagen weinig bekend was over de oorzaak van de traagheid van de API dacht ik dat het mijn onervarenheid in programmeren was. Daarbovenop kwam dat ik het lastig vindt om de authenticatie van google calendar te implementeren. Gezien de tijd leek firebase een goede oplossing om alsnog evenementen aan te maken en op te slaan per gebruiker. 

De zoekfunctie laat nu eigenlijk maar twee stukjes informatie zien, namelijk de naam en het jaar van uitgave. De thumbnail en andere informatie is te verkrijgen via een andere query naar de API. Als er meer tijd zijn en ik vaardiger was met de XmlPullParser zou ik op een andere manier connectie hebben gemaakt met de API waardoor meer informatie te zien is in het directie zoekscherm. 

Als ik meer tijd zou hebben zou ik ook meer interactie willen toepassen, bijvoorbeeld dat gebruikers elkaar kunnen uitnodigen voor een bepaald evenement. Dit zou optimaal kunnen doormiddel van google calendar, waarbij het evenement automatisch gesynchroniseerd wordt met je google account. 

In het fragment waarop meer informatie getoont wordt is gekozen om de rating van een spel weg te halen. Dit omdat de API de rating als een poll heeft gemaakt. Dit heeft het ene bordspel wel, en het andere niet. Daarnaast is er per rating geteld hoeveel stemmen er zijn. Bijvoorbeeld vier personen hebben een drie gegeven en twee personen een 5. Dit zou dan nog omgerekend moeten worden naar een gemiddelde. Aangezien ik al moeite had met het uitlezen van een XML API en het ontbreken van deze informatie bij sommige bordspellen, heb ik ervoor gekozen om dit weg te laten en de informatie te laten zien die altijd aanwezig is. Nu aan het einde van dit project heb ik de XML parser redelijk onder de knie gekregen en zou het leuk zijn als ik in een utopische wereld alle tijd zou hebben, dit zou kunnen implementeren. 

Het uiteindelijke resultaat is een goed begin naar wat de app qua functionaliteit had moeten zijn, namelijk dat er een spel gezocht kan worden, en hiervoor samen met andere mensen een evenement voor gepland kon worden. Er is een goed begin qua functionaliteit en UI / UX gemaakt, alleen de interactie mist nog. Dit had gelukt met meer tijd en minder tegenslagen..

