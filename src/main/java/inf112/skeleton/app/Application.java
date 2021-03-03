package inf112.skeleton.app;

import Cards.MovementCard;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import objects.Flag;
import objects.Robot;
import Game.Game;

import java.util.ArrayList;

public class Application extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;

    private TiledMap map;
    private TmxMapLoader mapLoader;
    private Stage stage;

    private TiledMapTileLayer baseLayer;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private TiledMapTileLayer.Cell playerWonCell;

    private ArrayList<Robot> players = new ArrayList<>();
    private ArrayList<Flag> flags = new ArrayList<>();

    private Game game;
    public enum State
    {
        PAUSE,
        RUN,
        RESUME,
        STOPPED
    }
    private State state = State.RUN;

    /**
     * Loads in map and every object on it
     */
    @Override
    public void create() {
        batch = new SpriteBatch();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("assets/RoboRallyTile.tmx");
        camera = new OrthographicCamera();


        baseLayer = (TiledMapTileLayer) map.getLayers().get("BaseLayer");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("Flag");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");

        TextureRegion[][] playerTextures = TextureRegion.split(new Texture("assets/player.png"), 300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerDiedCell = new TiledMapTileLayer.Cell();
        playerWonCell = new TiledMapTileLayer.Cell();

        playerCell.setTile(new StaticTiledMapTile(playerTextures[0][0]));
        playerDiedCell.setTile(new StaticTiledMapTile(playerTextures[0][1]));
        playerWonCell.setTile(new StaticTiledMapTile(playerTextures[0][2]));

        camera.setToOrtho(false,11,11);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map,1/300f);
        renderer.setView(camera);

        Gdx.input.setInputProcessor(this);
        Robot player1 = new Robot(0,0);
        Flag flag1 = new Flag(3,3);
        Flag flag2 = new Flag(6,6);
        players.add(player1);
        flags.add(flag1);
        flags.add(flag2);

        game = new Game(players, flags, this);
        players.get(0).IntializeClient(game);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {

        switch(state){
            case RUN:
                update();
                break;
            case PAUSE:
                break;
            case STOPPED:
                System.out.println("The game is over");
                Gdx.app.exit();
        }
        draw();

    }

    @Override
    public boolean keyUp(int keycode){

        playerLayer.setCell(playerXPosition(game.getPlayers().get(0)),playerYPosition(game.getPlayers().get(0)),null);

        if(keycode == Input.Keys.UP){
            //game.getPlayers().get(0).setPosition(game.getPlayers().get(0).getX(), game.getPlayers().get(0).getY() + 1);
            game.getPlayers().get(0).chooseCard(new MovementCard(1, 0));
            game.getPlayers().get(0).setDirection("N");
            game.getPlayers().get(0).moveBasedOnNextCard();
            return true;
        }
        else if(keycode == Input.Keys.DOWN){
            game.getPlayers().get(0).chooseCard(new MovementCard(1, 0));
            game.getPlayers().get(0).setDirection("S");
            game.getPlayers().get(0).moveBasedOnNextCard();
            return true;
        }
        else if(keycode == Input.Keys.LEFT){
            game.getPlayers().get(0).chooseCard(new MovementCard(1, 0));
            game.getPlayers().get(0).setDirection("W");
            game.getPlayers().get(0).moveBasedOnNextCard();
            return true;
        }
        else if(keycode == Input.Keys.RIGHT){
            game.getPlayers().get(0).chooseCard(new MovementCard(1, 0));
            game.getPlayers().get(0).setDirection("E");
            game.getPlayers().get(0).moveBasedOnNextCard();
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
    }


    public void draw(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        renderer.render();
    }

    public void update() {
        for (int i = 0; i < game.getPlayers().size(); i++) {
            if (holeLayer.getCell(playerXPosition(game.getPlayers().get(i)), playerYPosition(game.getPlayers().get(i))) != null) {
                playerLayer.setCell(playerXPosition(game.getPlayers().get(i)), playerYPosition(game.getPlayers().get(i)), playerDiedCell);
            } else if(playerOnFlag()){
                if (game.checkIfWinner()) {
                    setGameState(State.STOPPED);
                }
            } else{
                playerLayer.setCell(playerXPosition(game.getPlayers().get(i)),playerYPosition(game.getPlayers().get(i)),playerCell);
            }
        }
        /*        else if(flagLayer.getCell(playerXPosition(players.get(0)),playerYPosition(players.get(0))) != null){*/
    }

    @Override
    public void pause() {
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        this.state = State.RESUME;
    }

    public void setGameState(State s){
        this.state = s;
    }

    public int playerXPosition(Robot player){
        return Math.round(player.getX());
    }
    public int playerYPosition(Robot player){
        return Math.round(player.getY());
    }

    public int flagXPosition(Flag flag){
        return Math.round(flag.getX());
    }
    public int flagYPosition(Flag flag){
        return Math.round(flag.getY());
    }
    public boolean playerOnFlag() {
        for (Flag flag : flags) {
            if ((playerXPosition(game.getPlayers().get(0)) == flagXPosition(flag)) && (playerYPosition(game.getPlayers().get(0)) == flagYPosition(flag))) {
                playerLayer.setCell(playerXPosition(game.getPlayers().get(0)), playerYPosition(game.getPlayers().get(0)), playerWonCell);
                game.getPlayers().get(0).registerFlag(flag);

                return true;
            }

        }
        return false;
    }

    public void AddPlayer(Robot robot) {
        game.getPlayers().get(0).add(robot);
    }

    public TiledMapTileLayer getPlayerLayer() {
        return playerLayer;
    }
}
