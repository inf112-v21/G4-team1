package Multiplayer;

import Cards.MovementCard;
import Game.Game;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.Application;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import objects.Robot;
import org.lwjgl.system.CallbackI;

import java.net.URI;
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

            }
        });

        socket.on("initializeNewClient", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Object[] objectList = Arrays.stream(objects).toArray();
                id = objectList[0]+"";
                System.out.println("KVA STÅR HERE: " + id);
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
                        robot.moveBasedOnCard(new MovementCard(Integer.parseInt(result[1]), 999), result[2]);
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
                System.out.println("DISCONNECT EVENT FROM " + result);
                for (int i = game.getPlayers().size() - 1; i >= 0; i--) {
                    if (game.getPlayers().get(i).getId().equals(result)) {
                        // Visually removes player
                        game.getApplication().getPlayerLayer().setCell(Math.round(game.getPlayers().get(i).getX()), Math.round(game.getPlayers().get(i).getY()), null);
                        game.getPlayers().remove(i);
                    }
                }
            }
        });
    }

    public void MoveClientBasedOnCard(String id, int tiles, String dir) {
        System.out.println("MOVE: " + id + "," + tiles + "," + dir);
        socket.emit("moveClientBasedOnCard", id + "," + tiles + "," + dir);
    }

    public void UpdateClientPosition(Vector2 position, String id) {
        socket.emit("updateClientPosition", id + "," + position.x + "," + position.y);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
