# Obligatorisk oppgave 3

###Rollene

**Teamleder:** Kristian

**Tester:** Anders

**Kundekontakt:** Nathaniel

**Nettverksansvarlig:** Asgeir

**Møtereferat:** Ulrik

**Programmerere:** Ulrik, Kristian, Nathaniel, Anders, Asgeir

Rollene i teamet fungerer bra. Vi hadde i starten av prosjektet en god diskusjon om hvordan vi skulle fordele rollene. Utenom et par endringer har mesteparten av de første utdelte rollene ikke endret seg. Vi har god teamdynamikk og det har egentlig ikke vært noen grunn til å gjøre noen endringer på rollene.

###Erfaringer:

En erfaring vi har gjort oss er at det kan bli kaotisk med bare en branch. Vi har derfor begynt å lage nye branches for større endringer til prosjektet slik at hvis vi får problemer der slipper det å påvirke master branchen. Spesielt når det skal arbeides på større klasser som f.eks Game, eller det skal gjøres endringer på multiplayeren.

###Retroperspektiv:

Til nå har vi klart å lage et fungerende RoboRalley spill med multiplayer. Vi har implementert alle Mvp-kravene samt et par metoder som forbedrer spillopplevelsen. Vi har hatt mye fokus på god teamdynamikk og som et resultat av dette er det aldri dårlig stemning eller humør under noen av møtene. Det er felles forståelse om hvordan prosjektet ligger an, slik at det er enighet om hva som skal prioriteres videre.

En ting vi har jobbet med til denne innleveringen er mer beskrivende oppgaver i project board, noe som gjør det mer oversiktlig og lettere å forstå.  Dette er noe vi planlegger å opprettholde gjennom resten av prosjektet. I tillegg ser vi at det kan mangle litt kode-kommentarer i ny og ne. Spesielt nå som prosjektet begynner å bli ganske stort er det greit å holde det organisert. Et forbedringspunkt vi har er nok derfor å bruke mer tid på kommentarer når vi skriver koden slik at det ikke må legges til i ettertid.

###Gruppedynamikk:

Vi har god gruppedynamikk. 
Ekstramøte vi har satt opp på mandag har bra oppmøte og vi opprettholder fortsatt god kommunikasjon med hverandre.
I møter hender det ofte at vi hjelper hverandre med forskjellige problemer vi har.
I mellom møter er det ikke uvanlig at en eller to i gruppen møter i discord for å teste noe. 
Arbeidet fordeles forholdsvis jevnt og alle behandles med like stor grad av respekt og er villig til å gi litt av seg selv.


**Brukerhistorie 1:**
Som spiller vil jeg kunne gå i hull, slik at jeg kan miste liv.

**Akseptansekriterier:**

Gitt at

spiller er på posisjon (0,0)

Hull er på posisjon (0,1)

når spiller beveger seg fra (0,0) til (0,1)

så skal spiller miste 1 liv.

**Arbeidsoppgaver:**
- Lage funksjon som lagrer posisjon på hull
- Lage logikk som fjerner 1 liv fra spiller som går på hull

**Brukerhistorie 2:**
Som spiller vil jeg kunne dø av å gå i hull, slik at jeg kan tape.

**Akseptansekriterier:**

Gitt at

spiller har 3 liv

det er et hull på posisjon (x,y)

når spiller går i hullet (x,y) 3 ganger

så skal spiller fjernes fra brettet

**Arbeidsoppgaver:**
- Lagre funksjon som oppdaterer spiller sine liv.
- Lage logikk som fjerner spiller fra brettet når han har 0 liv.

**Brukerhistorie 3:**
Som spiller vil jeg kunne ha en meny når jeg starter programmet, slik at jeg kan velge når jeg vil starte spillet.

**Akseptansekriterier:**

Gitt at spiller starter programmet

så skal en meny vises.

når spiller velger "start spill"

så skal spillet starte.

**Arbeidsoppgaver:**
- Lage en meny til RoboRalley.
- Lage metode som registrerer valg og gjennomfører det.

