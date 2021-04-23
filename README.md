# lil-Code

### Members:
* Anders
* Asgeir
* Kristian
* Nathaniel
* Ulrik

## README

### Roborally:
Roborally is a board game where every player computes a 5-step program their robot executes.
This repeats until a robot has visited all flags on the map in a specific order. 
The game is over when the first robot visits all the flags. During the game, the robots can have different interactions with eachother, and the map.


#### How to play the game:
* Clone the latest version
* Run Main.java (src/main/java/inf112/skeleton/app/).
* To play multiplayer, have other people do the same, and you will automatically connect to eachother.
* When everyone is in, the server-client presses enter to start the game for everyone (server-client has ID 1 in the console).
* To play a card write the corresponding number (1-9), the nonserver-clients need to do this first, then the server clients does it.
* The moves will be executed automatically.
* To start a new round, the server-client presses space
* Visit each flag in order to win.

#### Known bugs:
* Starting new rounds sometimes gives out multiple hands in multiplayer.
* Some print messages gets sent multiple times in multiplayer.
