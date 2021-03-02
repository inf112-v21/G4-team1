package Multiplayer;

import Game.Game;
import com.badlogic.gdx.math.Vector2;
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

    public Client(Game game, Robot robot) {
        this.game_ = game;

        uri = URI.create("http://localhost:3001");
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
                        System.out.println("OUR ID IS: " + id);
                        robot.setId(result[i]);
                    } else {
                        Robot robot = new Robot(Integer.parseInt(result[i+1]),Integer.parseInt(result[i+2]), game);
                        robot.setId(result[i]);
                        game_.AddPlayer(robot);
                        System.out.println("A robot already existed at " + result[i+1] + "," + result[i+2] + " with id " + result[i]);
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
                Robot robot = new Robot(0,0, game_);
                robot.setId(id);
                game_.AddPlayer(robot);
                /*Robot robot = new Robot(0, 0);
                robot.setId(objectList[0]+"");
                game.addPlayer(robot);*/
            }
        });

        socket.on("updateClientPosition", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Object[] objectList = Arrays.stream(objects).toArray();
                System.out.println("POSITION CHANGE RECEIVED FOR: " + objectList[0]+"");
                String[] result = (objectList[0]+"").split(",");
                for (Robot robot : game.getPlayers()) {
                    if (Integer.parseInt(robot.getId()) == Integer.parseInt(result[0])) {
                        robot.setPosition(Float.parseFloat(result[1]), Float.parseFloat(result[2]));
                        System.out.println("ROBOT FOUND, POSITION HAS BEEN UPDATED FOR " + robot.getId());
                    }
                }
            }
        });
    }

    public void UpdateClientPosition(Vector2 position, String id) {
        System.out.println("POSITION BROADCAST: " + id + "," + position.x + "," + position.y);
        socket.emit("updateClientPosition", id + "," + position.x + "," + position.y);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
