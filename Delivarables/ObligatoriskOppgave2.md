# Obligatorisk oppgave 2

### Rollene

**Teamleder:** Kristian 

**Tester:** Anders

**Kundekontakt:** Nathaniel

**Nettverksansvarlig:** Asgeir

**Møtereferat:** Ulrik

**Programmerere:** Ulrik, Kristian, Nathaniel, Anders, Asgeir

Rollene i teamet fungerer greit. Men det har blitt gjort endringer siden oblig1. På grunn av at vi alle har forskjellig erfaring slik at noen er bedre på visse ting og dermed fullføre oppgaven mer effektivt. Rollene sier noe om hvilke oppgaver man blir tildelt. F.eks nettverksansvarlig får hovedansvar for det som har med multiplayeren å gjøre osv.
Det var vanskelig å tildele roller i starten men det lønner seg når man skal organisere arbeidet og at det er litt struktur på hvem som holder på med hva istedenfor at det blir kaos. 
Asgeri som kom inn i prosjektet har overtatt rollen som nettverksansvarlig. Dette er fordi han har en del erfaring med dette, og det er han sin server spillet kjører på.
Ulrik har også fått rollen som referats-ansvarlig.

Gruppedynamikken er god og vi har flytende kommunikasjon under møtene. Vi har ikke opplevd store problemer med prosjektet selv om vi alle har forskjellig erfaring. Hvis det er problemer med noe blir det tatt opp med en gang slik at det ikke bygger seg opp.
I hvert møte går vi tydelig gjennom hva hvert enkelt medlem holder på med, og hvor langt vi har kommet, og hva de skal jobbe videre med. Dette gjør slik at alle vet nøyaktig hve de skal jobbe med til en hver tid.

### Prosjektstruktur
Til nå har vi greid å oppfylle alle Mvp kravene samt begynt å jobbe litt med mer utfyllende spill logikk. 
Vi bruker project board til å se hva som holdes på med og hva som har blitt gjort. 
Hvis det kommer noen spørsmål har vi laget en egen discord slik at kommukasjonen ikke stopper opp mellom møtene.
I tilleg brukes codeWithMe hvis flere programmerer på samme ting for å unngå kluss.

### Retrospektiv

Det er noen ting vi har hatt fokus på å prøve å forbedre siden forrige innlevering, som vi gikk gjennom på møte 24. februar. 
Dette er mer oppfulgte møtetider. 
Vi skal også ha mer fokus på tester, og ha flere manuelle visuelle tester.
Vi skal også forbedre commits, det innebærer tydeligere og mer beskrivende commit meldinger og bedre fordeling av commits, noe som er vanskelig siden det er forskjellig programeringskompetanse og når vi bruker codeWithMe, er det en person som commiter alt som blir gjort.


##### ProjectBoard

![img.png](img.png)
Utklipp av project board under utviklingen. Oppgaver som ikke er selvinlysende er forklart med komentar som dukker opp når du klikker på den.

### Brukerhistorier med akseptansekriterier
#### Mvp krav 6:
Som spiller vil jeg kunne spille sammen med andre, slik at det blir konkurranse om å vinne.

**Akseptansekriterier:**

Gitt at

spiller(1)  starter applikasjonen

spiller(2) starter applikasjonen

når spiller(1) beveger seg fra felt (0,0) til (0,1)

så vises det samme for spiller(2)

**Tester:**

For å teste flerspiller-kravet, brukte vi en enkel manuell test. To eller flere spillere kobler seg på serveren (kjører main). Her kan en fort se at at flere spillere har koblet til ettersom spillerkarakteren blir nummerert. Da er det lett å skille mellom de ulike spillerene og en kan se at flere spillere er på samtidig.

**Arbeidsoppgaver:**
- Få til å koble flere spillere sammen enten ved hjelp av p2p, eller server-client system
- La spillere se hverandre på brettet
- Lage logikk i programmet som registrerer vinneren


#### Mvp krav 7:
Som spiller vil jeg få utdelt kort, slik at jeg har mulighet til å spille de.

**Akseptansekriterier:**

Gitt at

man har startet et spill

når programmeringsfasen begynner

så skal spiller bli delt ut 9 kort

**Arbeidsoppgaver**
- Lage en overklasse for kort
- Lage en underklasse klasse for bevegelse- og snukort
- Lage en klasse for dekket som inneholder alle kortene
- Lage logikk som deler ut 9 tilfeldige kort hver programmeringsrunde, og som viser kortene til spiller

**Tester:**

Til dette kravet er det brukt manuelle tester.

For denne iterasjonen av programmet blir terminalen brukt for å printe ut kortene på String-format.

For å teste at alle 9 kortene viser, kjører en først main. Da vil spill-vinduet komme opp. Når viduet er lastet inn bruker en Enter-knappen på tastaturet. Da vil det bli printet ut 9 kort til terminalen.

Kortene er formatert for lesbarhet, noe som gjør det lett å se at alle 9 kortene blir printet ut slik at spilleren kan velge mellom de.

#### Mvp krav 8:
Som spiller vil jeg ha muligheten til å velge 5 kort, slik at jeg kan planlegge runden min.

**Akseptansekriterier:**

Gitt at

Spillet er i programmeringsfasen

spiller har blitt tildelt 9 kort å velge mellom

spiller har valgt 4 kort

når spiller velger kort 5

så kan ikke spiller velge flere kort før neste programmeringsfase

**Arbeidsoppgaver**
- Vise kort til brukeren
- Lage en funskjon som lagrer valgte kort
- Lage logikk slik at man ikke man velge det samme kortet flere ganger
- Lage logikk slik at man ikke kan velge mer enn 5 kort

**Tester:**

For å velge kort i denne iterasjonen bruker en 1-9 på tastaturet. 

Etter at kortene er blitt delt ut kommer det en forklarende tekst for hvordan man velger kortene.

Først ble det testet om en kom videre til neste kortvalg etter at en har valgt et kort. Etter et kort ble valgt, kom det opp en ny "velgkort"-linje så da kunne vi se at dette funket.

Neste test gikk ut på å sjekke om en ble hindret i å velge samme kortet to ganger (eks. trykke 1 på to ulike "velgkort"-linjer).
Når samme kortet ble valgt to ganger, kom en melding om at kortet var allerede valgt, og en måtte velge på nytt, testen passerer.

For neste test ville vi se at en bruker fikk valgt fem kort og ikke færre. Dette ble gjort ved at spilleren valgte fem ulike kort, og ikke ble stoppet før det.
Dette funket, testen passerer.

For siste test ville vi se at en bare fikk valgt fem kort og ikke flere. Dette ble testet ved å velge fem ulike kort.
Etter det femte kortet har blitt valgt burde ikke brukeren få opp en ny kortvelger-linje. Det skjer ikke, testen passerer.

#### Mvp krav 9:

Som robot vil jeg ha muligheten til å bevege meg i henhold til valgte kort, slik at jeg kan vinne spillet.

**Akseptansekriterier:**

Gitt at

Spiller er i programmeringsfasen

spiller har valgt kort 1,2,3,4,5 i kronologisk rekkefølge

kort 1 er to fram

kort 2 er snu venstre

når programmeringsfasen er over

så skal robot til spiller først bevege seg to fram og så snu til venstre.

**Arbeidsoppgaver**
- Lage logikk som leser de valgte kortene i rekkefølge
- Lage funksjon som flytter roboten etter valgt kort

**Tester:**

Ettersom denne iterasjonen av programmet ikke roterer spill-karakteren er det ikke alt som kan testet på i dette mvp-kravet.
Kortene som omhandler rotering av spilleren vil ikke være mulig å teste rent visuellt (slik som blir gjort med resten av dette mvp kravet).

For å kunne teste rotasjonskortene blir det skrevet tekst som beskriver bevegelsen til roboten (printer til terminal).
Her er det blitt testet om kort som feks. "Turn Left" sammsvarer med "Turned to the left...". Dette virker bra, så testen passerer. 

For move-kortene er det lettere å teste rent visuellt. For å teste disse kortene velger vi bare move-kort så det er lett å telle om alle kortene blir tatt i bruk.
Si at en har fem move1-kort etter hverandre, så vil roboten flytte fem brikker. 
Vi valgte fem move-kort og telte opp på forhånd hvor mange ruter den burde bevege seg. Så sammenligent vi det med hvor mange ruter roboten faktisk flyttet seg.
Disse sammsvarte, så testen passerer. 
### Vår prioritering:
Vi har ikke endret eller tilpasset MVP kravene fra første innlevering, da vi syntes de passet bra og var gjennomførbare.
Vi har hatt stor fokus på å få til multiplayer tidlig i denne innleveringen, fordi vi anntok at dette kom til å ta lengst tid.
På tiden vi leverer følger ikke spillet alle spillereglene, men alle originale MVP kravene er innfridd.