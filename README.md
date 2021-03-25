# lil-Code

###Members:
* Anders
* Asgeir
* Kristian
* Nathaniel
* Ulrik

##README

###Roborally:
Roborally is a board game where every player computes a 5-step program their robot executes.
This repeats until a robot has visited all flags on the map in a specific order. 
The game is over when the first robot visits all the flags. During the game, the robots can have different interactions with eachother, and the map.


####How to play the game:
* Clone the latest version 
* Open it in your editor
* Run Main.java
* To play multiplayer, have other people do the same, and you will automatically connect to eachother
* When everyone is in, press Enter to start (currently not working)
* To play a card write the corresponding number (1-9)
* when every player has played 5 cards, they will be executed

####Known bugs:
* pushRobot will sometimes give an error, but works most times (Important: must give error to work)
* Pushing player(2) into wall will render them invisible and player(1) replaces their position. Player(2) becomes visible if they move again.
* If multiple players press enter to start the first programming phase game movement becomes buggy and will eventually crash.
* List of flags is not sorted correctly, so the order you have to visit them is not accurate