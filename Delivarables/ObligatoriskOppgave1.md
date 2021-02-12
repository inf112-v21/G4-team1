# INF112 Maven template 
##Deloppgave 1

###Kompetanse

**Kristian Oterholt** :**
Har erfaring med Java fra inf101 of inf102. Går IKT 4. semester

**Ulrik Seibt :**
Har erfaring i python (INF100).

**Nathaniel Maaskant :**
Erfaring med Java fra inf101 og inf102, litt python fra inf100, Går IKT 4. Semester

**Anders Refvik Torsvik :**
Erfaring med Java fra inf101 og inf102, litt python fra inf100, Går IKT 4. Semester

###Roller

**Teamleder:** Kristian

**Tester:** Anders

**Kundekontakt:** Nathaniel

**Nettverksansvarlig:** Ulrik

**Programmerere:** Ulrik, Kristian, Nathaniel, Anders

Kristian ble valgt som teamleder på grunn av tidligere erfaring med håndtering av større prosjekter. Anders har rollen tester fordi han viste interesse for det under første møte. Nathaniel er kundekontakt fordi han er målbevisst og har god kontroll på hva som skal prioriteres. Ulrik ble nettverksansvarlig siden han har litt erfaring med å sette opp nettverk fra før. Alle har rollen som programmer for at jobben ikke skal bli for krevende for enkeltpersoner. Disse rollene er i bruk hovedsakelig til første oblig, og vi er bevisste på at det kan endre seg i løpet av prosjektet.

## Deloppgave 2

**Prosjektmetodikk:** Kanban

Her er et skjermdump av vårt project board. Nye issues blir lagt i "backlog", de viktigste blir flyttet til "prioritert".
Alle gruppemedlemmene har sin egen kolonne hvor de kan dra issues de jobber på inn. I teams med flere medlemmer enn oss hadde det ikke funket med så mange kolonner,
men siden vi bare er 4, funker dette bra. Når en issue lukkes blir den lagt i "Ferdig"
![img.png](img.png)

**Møter:** Møtes fysisk mandag ettermiddag, gruppetimer onsdag,  fredag om det trengs mer arbeid på prosjektet.

**Kommunikasjon:** Vi bruker Discord og Facebook messenger til å kommunisere mellom møtene.

**Arbeidsfordeling:** Hver person tar en ting fra Project board som man jobber med omgangen. Lett å holde styr på hva som trengs. Slipper også at det blir problemer med at personer holder på med den samme tingen.

**Oppfølging:** Alle deler hva de driver med, og hvor langt de har kommet på onsdagsmøte. I tillegg kommuniserer vi gjennom discord hvor vi ligger an slik at ingen sitter fast/ligger bak.

**Deling av felles kode:** vi bruker repositorium på git og google disk mappe

## Deloppgave 3

Denne applikasjonen skal inneholde muligheten til å spille brettspillet RoboRally digitalt. Spillet vil inneholde funksjonalitet basert på brettspill-utgaven, men spesifisert av oppgaveteksten gitt av UiB. Målet er at spillet skal ha en grei spillopplevelse med mulighet for multiplayer.

####Brukerhistorie


Som spiller ønsker jeg å kunne se brettet jeg spiller på og de forskjellige brikkene på brettet for å kunne bevege meg.

####Arbeidsoppgaver:

* Lage brettet i programmet 'tiled'
* Vise brettet i aplikasjonen
* Vise spiller på aplikasjonen
* Spiller skal kunne klytte på brettet

####Akseptansekriterier

Gitt at

spiller ser brettet og brikkene på det


når Spiller trykker på ->

så beveger spiller seg på brettet i retning ->.

####Brukerhistorie:


Som spiller ønsker jeg å kunne besøke flagg i spillet, slik at det er mulig å vinne.

####Arbeidsoppgaver:

* Flagg skal vises på brettet
* Spiller skal registrere når den går over flagg

####Akseptansekriterier



Gitt at

spiller ser brettet og kan bevege seg.

Siste flagg er på felt(6,6)

spiller er på felt(6,5)

når spiller går fra felt (6,5) til felt(6,6)

så vinner spilleren

##Testing
For testingen lagde vi bare en JUnit test til dette programmet, FileLoadTest.
Denne testen, tester på om det finnes en fil som heter RoboRallyTile, altså tmx filen for kartet.

####Visuelle tester
Vi foretok noen visuelle tester for spillbrettet og for beveging av karaktern.
For spillbrettet sjekket vi at alle de ulike lagene viste (baselayer, holelayer,flaglayer og playerlayer).
Når alle de ulike lagene kunne sees, var testen passert.

Neste testingen ble gjort på spillerbrikken. Først plasserte vi brikken ved noen negative koordinater for å sjekke at dette ikke gikk.
Her kunne vi jo (åpenbart) ikke se brikken, så vi endret kordinatene til 0,0.
Da ble brikken satt i nedre vestre hjørnet, så testen sto.

Flytting av brikker var den neste visuelle testen. Her sjekket vi at brikken bevegde en brikke av gangen og i den retningen den trykkede piltastet viste.
Alle brikkene bevegde seg bare etter piltastene og ikke noen andre taster. Karakteren bevegde seg som den skal.

Neste, og siste visuelle test var en test på om karakteren endret ikon (skin) om den traff på et hull eller et flag.
På spillebrettet var det to hull og et flag. Hullet og flagget skulle gi to ulike skins, noe som det gjorde.
Det funket som det skulle, og derfor har alle de visuelle testen stått. 
