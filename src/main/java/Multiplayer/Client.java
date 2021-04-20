package Multiplayer;

import Cards.ICards;
import Cards.MovementCard;
import Cards.TurningCard;
import Game.Game;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.Application;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import objects.Robot;
import org.lwjgl.system.CallbackI;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    private String id = "";
    URI uri;
    Socket socket;
    IO.Options options;
    Game game_;
    Application application;
    Robot robot;

    public Client(Game game, Robot robot, Application application) {
        this.game_ = game;
        this.robot = robot;
        this.application = application;

        uri = URI.create("http://ec2-3-140-185-175.us-east-2.compute.amazonaws.com/");
        options = IO.Options.builder().build();
        socket = IO.socket(uri, options);
        socket.connect();

        socket.on("initialize", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Object[] objectList = Arrays.stream(args).toArray();
                String[] result = (objectList[0]+"").split(",");
                int j = 0;
                for (int i = 0; i < result.length; i++) {
                    if (i == 0) {
                        id = result[i];
                        System.out.println("Du er spiller nr: " + id);
                        robot.setId(result[i]);
                    } else {
                        Robot robot = new Robot((int)Float.parseFloat(result[i+1]),(int)Float.parseFloat(result[i+2]), game);
                        robot.setId(result[i]);
                        game_.AddPlayer(robot);
                        i += 2;
                    }
                }
                // Tell the server our location
                //UpdateClientPosition(new Vector2(robot.getX(), robot.getY()));
                Vector2 pos = FindClearPosition();
                robot.setPosition(pos.x, pos.y);
            }
        });

        socket.on("initializeNewClient", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Object[] objectList = Arrays.stream(objects).toArray();
                id = objectList[0]+"";
                Robot robot = new Robot(0,0, game_);
                robot.setId(id);
                game_.AddPlayer(robot);
                /*Robot robot = new Robot(0, 0);
                robot.setId(objectList[0]+"");
                game.addPlayer(robot);*/
            }
        });

        socket.on("moveClientBasedOnCard", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Object[] objectList = Arrays.stream(objects).toArray();
                String[] result = (objectList[0]+"").split(",");
                for (Robot robot : game.getPlayers()) {
                    if (Integer.parseInt(robot.getId()) == Integer.parseInt(result[0])) {
                        robot.moveBasedOnCard(new MovementCard(Integer.parseInt(result[1]), 999), result[2], false);
                    }
                }
            }
        });

        socket.on("updateClientPosition", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Object[] objectList = Arrays.stream(objects).toArray();
                String[] result = (objectList[0]+"").split(",");
                for (Robot robot : game.getPlayers()) {
                    if (Integer.parseInt(robot.getId()) == Integer.parseInt(result[0])) {
                        robot.setPosition(Float.parseFloat(result[1]), Float.parseFloat(result[2]));
                    }
                }
            }
        });

        socket.on("disconnected", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Object[] objectList = Arrays.stream(objects).toArray();
                String result = objectList[0]+"";
                for (int i = game.getPlayers().size() - 1; i >= 0; i--) {
                    if (game.getPlayers().get(i).getId().equals(result)) {
                        // Visually removes player
                        game.getApplication().getPlayerLayer().setCell(Math.round(game.getPlayers().get(i).getX()), Math.round(game.getPlayers().get(i).getY()), null);
                        game.getPlayers().remove(i);
                    }
                }
            }
        });

        socket.on("emitCards", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Object[] objectList = Arrays.stream(objects).toArray();
                String[] result = (objectList[0]+"").split(",");
                ArrayList<String> simpleCardNames = new ArrayList<String>();
                int robotIterator = 0;
                for (int i = 0; i < result.length; i++) {
                    if (result[i].equals(game.getPlayers().get(robotIterator).getId())) {
                        simpleCardNames = new ArrayList<>();
                        // Get the 9 next elements of the result array, which is the 9 cards
                        for (int j = 1; j < 10; j++) {
                            simpleCardNames.add(result[(i+j)]);
                        }
                        game.getPlayers().get(robotIterator).setHand(simpleCardNamesToICards(simpleCardNames));
                        robotIterator++;
                        if (robotIterator >= game.getPlayers().size()); {
                            i = result.length;
                        }
                    }
                }


                game.getPlayers().get(0).printCardsToTerminal();
            }
        });

        socket.on("emitChosenCards", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Object[] objectList = Arrays.stream(objects).toArray();
                String[] result = (objectList[0]+"").split(",");
                System.out.println("RECIEVED CHOSEN CARDS");
                ArrayList<String> simpleCardNames = new ArrayList<String>();
                int robotIterator = 0;
                int robotID = Integer.parseInt(result[0]) - 1;
                for (int i = 0; i < result.length; i++) {
                    if (result[i].equals(game.getPlayers().get(robotID).getId())) {
                        System.out.println("RESULT[i] (ID) " + result[i]);
                        simpleCardNames = new ArrayList<>();
                        // Get the 5 next elements of the result array, which is the 9 cards
                        for (int j = 1; j < 6; j++) {
                            simpleCardNames.add(result[(i+j)]);
                        }
                        game.getPlayers().get(robotID).setChosenCardFromHand(simpleCardNamesToICards(simpleCardNames));
                        System.out.println("First card priority: " + game.getPlayers().get(robotID).getChosenCardsFromHand().get(0).getPrio());
                        robotIterator++;
                        if (robotIterator >= game.getPlayers().size()); {
                            i = result.length;
                        }
                    }
                }
            }
        });

        socket.on("startRound", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Object[] objectList = Arrays.stream(objects).toArray();
                String[] result = (objectList[0]+"").split(",");
                System.out.println("START THE ROUND");
                game.playTurn();
            }
        });
    }

    public ArrayList<ICards> simpleCardNamesToICards(ArrayList<String> cards) {
        ArrayList<ICards> iCardsArrayList = new ArrayList<ICards>();
        for (String s : cards) {
            switch (s.charAt(0)) {
                case 'M':
                    if (s.charAt(1) == '-') {
                        iCardsArrayList.add(new MovementCard(Character.getNumericValue(s.charAt(2)), Integer.parseInt(s.substring(3, s.length()))));
                    } else {
                        iCardsArrayList.add(new MovementCard(Character.getNumericValue(s.charAt(1)), Integer.parseInt(s.substring(2, s.length()))));
                    }
                    break;
                case 'R':
                    iCardsArrayList.add(new TurningCard(true, false, Integer.parseInt(s.substring(1, s.length()))));
                    break;
                case 'L':
                    iCardsArrayList.add(new TurningCard(false, false, Integer.parseInt(s.substring(1, s.length()))));
                    break;
                case 'U':
                    iCardsArrayList.add(new TurningCard(true, true, Integer.parseInt(s.substring(1, s.length()))));
                    break;
            }
        }

        // DEBUG CARDS
        return iCardsArrayList;
    }

    public Vector2 FindClearPosition() {
        for (int i = 0; i < 10; i++) {
            Vector2 pos = new Vector2(i, 0);
            boolean positionClear = true;
            for (Robot rob : game_.getPlayers()) {
                System.out.println((rob.getX() == pos.x) +" | " + (rob.getY() == pos.y) + " | " + !rob.getId().equals(id));
                if (rob.getX() == pos.x && rob.getY() == pos.y) {
                    if (!rob.getId().equals(id)) { positionClear = false; }
                }
            }
            if (positionClear) { return pos; }
        }
        return new Vector2(0,0);
    }

    public void MoveClientBasedOnCard(String id, int tiles, String dir) {
        socket.emit("moveClientBasedOnCard", id + "," + tiles + "," + dir);
    }

    public void UpdateClientPosition(Vector2 position, String id) {
        //System.out.println("UPDATING POSITION: " + id + "," + position.x + "," + position.y);
        socket.emit("updateClientPosition", id + "," + position.x + "," + position.y);
    }

    public void emitCards(String cards) {
        socket.emit("emitCards", cards);
    }

    public void emitChosenCards(String cards) {
        System.out.println("EMITTING CHOSEN CARDS");
        socket.emit("emitChosenCards", cards);
    }

    public void emitRoundOverFlag() {
        socket.emit("emitRoundOverFlag", "");
    }

    public String getId() {
        return id;
    }

    public Socket getSocket() { return socket; }

    public void setId(String id) {
        this.id = id;
    }

    public Application getApplication(){
        return application;
    }
}
