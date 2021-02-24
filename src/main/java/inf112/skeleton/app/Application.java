package inf112.skeleton.app;

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
import com.badlogic.gdx.math.Vector2;
import objects.Flag;
import objects.Robot;

public class Application extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;

    private TiledMap map;
    private TmxMapLoader mapLoader;

    private TiledMapTileLayer baseLayer;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private Robot playerPosition;
    private Flag flagPosition;

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
        playerPosition = new Robot(0,0);
        flagPosition = new Flag(4,4);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);


        if(holeLayer.getCell(playerXPosition(),playerYPosition()) != null){
            playerLayer.setCell(playerXPosition(),playerYPosition(),playerDiedCell);

        }
        else if(flagLayer.getCell(playerXPosition(),playerYPosition()) != null){
            playerLayer.setCell(playerXPosition(),playerYPosition(),playerWonCell);
            registerflag();
            pause();
        }

        else{
            playerLayer.setCell(playerXPosition(),playerYPosition(),playerCell);
        }

        renderer.render();
    }

    @Override
    public boolean keyUp(int keycode){

        playerLayer.setCell(playerXPosition(),playerYPosition(),null);

        if(keycode == Input.Keys.UP){
            playerPosition.add(0,1);
            return true;
        }
        else if(keycode == Input.Keys.DOWN){
            playerPosition.add(0,-1);
            return true;
        }
        else if(keycode == Input.Keys.LEFT){
            playerPosition.add(-1,0);
            return true;
        }
        else if(keycode == Input.Keys.RIGHT){
            playerPosition.add(1,0);
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public void registerflag() {
        playerPosition.registerFlag(flagPosition);
    }

    public int playerXPosition(){
        return Math.round(playerPosition.getX());
    }
    public int playerYPosition(){
        return Math.round(playerPosition.getY());
    }

    public int flagXPosition(){
        return Math.round(flagPosition.getX());
    }
    public int flagYPosition(){
        return Math.round(flagPosition.getY());
    }
}
