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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import objects.Flag;
import objects.Robot;
import Game.Game;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

public class Application extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;

    private TiledMap map;
    private TmxMapLoader mapLoader;

    private TiledMapTileLayer baseLayer;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer wallsLayer;
    private TiledMapTileLayer startPositionsLayer;
    private TiledMapTileLayer conveyorBeltLayer;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private ArrayList<TiledMapTileLayer.Cell> playerCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private TiledMapTileLayer.Cell playerWonCell;

    private ArrayList<Robot> players = new ArrayList<>();
    private ArrayList<Flag> flags = new ArrayList<>();

    private boolean arrowKeysEnabled = true;

    private ArrayList<String> playerSkinPaths = new ArrayList<>(Arrays.asList("assets/player1.png", "assets/player2.png", "assets/player3.png", "assets/player4.png", "assets/player5.png", "assets/player6.png"));

    private int mapWidth;
    private int mapHeight;

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

        mapWidth = 16;
        mapHeight = 12;

        Robot player1 = new Robot(0,0);
        players.add(player1);


        game = new Game(players, this);
        players.get(0).InitializeClient(game, this);

        batch = new SpriteBatch();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("assets/RoboRallyTile2.tmx");
        camera = new OrthographicCamera();

        baseLayer = (TiledMapTileLayer) map.getLayers().get("BaseLayer");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("HoleLayer");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("FlagLayer");
        wallsLayer = (TiledMapTileLayer) map.getLayers().get("WallsLayer");
        startPositionsLayer = (TiledMapTileLayer) map.getLayers().get("StartPositionsLayer");
        conveyorBeltLayer = (TiledMapTileLayer) map.getLayers().get("ConveyorBeltLayer");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("PlayerLayer");

        SetPlayerSkin();



        camera.setToOrtho(false,mapWidth,mapHeight);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map,1/300f);
        renderer.setView(camera);
        Gdx.input.setInputProcessor(this);

    }

    public void SetPlayerSkin() {
        playerCell = new ArrayList<>();
        playerDiedCell = new TiledMapTileLayer.Cell();
        playerWonCell = new TiledMapTileLayer.Cell();

        for (int i = 0; i < playerSkinPaths.size(); i++) {
            TextureRegion[][] playerTextures = TextureRegion.split(new Texture(playerSkinPaths.get(i)), 300, 300);

            playerCell.add(new TiledMapTileLayer.Cell());
            playerCell.get(i).setTile(new StaticTiledMapTile(playerTextures[0][0]));
            playerDiedCell.setTile(new StaticTiledMapTile(playerTextures[0][1]));
            playerWonCell.setTile(new StaticTiledMapTile(playerTextures[0][2]));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();

        Gdx.app.exit();
        System.exit(0);
    }


    /**
     * Runs the game, checks if its running, paused or finished,
     * Updates the board if the game is running. Calls draw function which calls the render
     * function again.
     */
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

    /**
     *  Updates the position of a player, with keys or cards
     */
    @Override
    public boolean keyUp(int keycode){

        if (arrowKeysEnabled) {
            if (keycode == Input.Keys.UP) {
                //game.getPlayers().get(0).setPosition(game.getPlayers().get(0).getX(), game.getPlayers().get(0).getY() + 1);
                game.getPlayers().get(0).chooseCardFromHand(new MovementCard(1, 0));
                game.getPlayers().get(0).setDirection("N");
                game.getPlayers().get(0).moveBasedOnNextCard(false, false);
                return true;
            } else if (keycode == Input.Keys.DOWN) {
                game.getPlayers().get(0).chooseCardFromHand(new MovementCard(1, 0));
                game.getPlayers().get(0).setDirection("S");
                game.getPlayers().get(0).moveBasedOnNextCard(false, false);
                return true;
            } else if (keycode == Input.Keys.LEFT) {
                game.getPlayers().get(0).chooseCardFromHand(new MovementCard(1, 0));
                game.getPlayers().get(0).setDirection("W");
                game.getPlayers().get(0).moveBasedOnNextCard(false, false);
                return true;
            } else if (keycode == Input.Keys.RIGHT) {
                game.getPlayers().get(0).chooseCardFromHand(new MovementCard(1, 0));
                game.getPlayers().get(0).setDirection("E");
                game.getPlayers().get(0).moveBasedOnNextCard(false, false);
                return true;
            } else if (keycode == Input.Keys.SPACE) {
                if(!game.getPlayers().get(0).getChosenCardsFromHand().isEmpty()) {
                    game.getPlayers().get(0).moveBasedOnNextCard(false, false);
                }
                else {
                    game.discardStep();
                    game.drawStep();
                    players.get(0).robotOnBelt();
                }
                return true;
            }

        }

        if(keycode == Input.Keys.ENTER){
            if(!game.isPlaying()) {
                game.startGame();
                flags = game.getFlags();

            }
        }
        return false;
    }

    public void toggleArrowKeys(){
        arrowKeysEnabled = !arrowKeysEnabled;
    }

    @Override
    public void resize(int width, int height) {
    }


    public void draw(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        renderer.render();
    }

    /**
    Updates the game board, checks if any players are on flag or hole tiles.
    If a players stands on a flag tiles, it calls checkIfWinner in game to see if the game is done
    Sets gameState to stopped if checkIfWinner is true.
     */
    public void update() {
        for (Robot player : game.getPlayers()) {
            if(player.getLifeTokens() == 0){
                player.setPosition(-5, -5);
                continue;
            }

            if (playerInPit(player)) {
                playerLayer.setCell(playerXPosition(player), playerYPosition(player), playerDiedCell);
                player.loseLife();

                if(player.getLifeTokens() == 0){
                    System.out.println("You are dead");
                    player.setPosition(-5, -5);
                }
                player.setPosition(player.getStartPositionX(), player.getStartPositionY());
            } else if(playerOnFlag(player)){
                if (game.checkIfWinner()) {
                    setGameState(State.STOPPED);
                }
            }

            else{
                if (player.getId() != null) {
                    playerLayer.setCell(playerXPosition(player),playerYPosition(player),playerCell.get(Integer.parseInt(player.getId()) - 1));
                }
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

    /**
    Checks if a player is on a flag tile, registers flag for player if it is on the right order
    for flags, and player has all previous flags, or if the flag is the first flag.
     */
    public boolean playerOnFlag(Robot player) {
        for (Flag flag : flags) {
                if(player.getVisitedFlags().contains(flag)) continue;
                if(flags.get(0).equals(flag) || player.getVisitedFlags().contains(flags.get(flags.indexOf(flag)-1))){
                    if ((playerXPosition(player) == flagXPosition(flag)) && (playerYPosition(player) == flagYPosition(flag))) {
                        playerLayer.setCell(playerXPosition(player), playerYPosition(player), playerWonCell);
                        player.registerFlag(flag);
                        return true;
                    }
                }
            }
            return false;
        }

    public boolean playerInPit(Robot player) {
        return (holeLayer.getCell(playerXPosition(player), playerYPosition(player)) != null);
    }


    public ArrayList<Vector2> getEntities(TiledMapTileLayer layer){
        ArrayList<Vector2> entities = new ArrayList<>();
        for(int x = 0; x < mapWidth; x++){
            for(int y = 0; y < mapHeight;y++){
                if(layer.getCell(x,y) != null){
                    Vector2 pos = new Vector2(x,y);
                    entities.add(pos);
                }
            }
        }
        return entities;
    }

    public TiledMapTileLayer getPlayerLayer() {
        return playerLayer;
    }

    public TiledMapTileLayer getStartPositionLayer() {
        return startPositionsLayer;
    }

    public TiledMapTileLayer getFlagLayer() {
        return flagLayer;
    }

    public TiledMapTileLayer getWallsLayer() { return wallsLayer; }

    public TiledMapTileLayer getConveyorBeltLayer() { return conveyorBeltLayer;}
}
