package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;

public class HelloWorld extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;

    private TiledMap map;
    private TmxMapLoader mapLoader;

    private TiledMapTileLayer baseLayer;
    private TiledMapTileLayer hole;
    private TiledMapTileLayer flag;
    private TiledMapTileLayer player;

    private Texture playerTexture;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer rederer;

    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private Vector2 playerPosition;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("RoboRallyTile.tmx");  
        camera = new OrthographicCamera();

        baseLayer = (TiledMapTileLayer) map.getLayers().get("BaseLayer");
        hole = (TiledMapTileLayer) map.getLayers().get("Hole");
        flag = (TiledMapTileLayer) map.getLayers().get("Flag");
        player = (TiledMapTileLayer) map.getLayers().get("Player");

        TextureRegion[][] playerTextures = TextureRegion.split(new Texture("player.png"), 300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerDiedCell = new TiledMapTileLayer.Cell();
        playerWonCell = new TiledMapTileLayer.Cell();

        playerCell.setTile(new StaticTiledMapTile(playerTextures[0][0]));
        playerDiedCell.setTile(new StaticTiledMapTile(playerTextures[0][1]));
        playerWonCell.setTile(new StaticTiledMapTile(playerTextures[0][2]));

        playerPosition = new Vector2();

        camera.setToOrtho(false,11,11);
        camera.update();

        rederer = new OrthogonalTiledMapRenderer(map,1/300f);
        rederer.setView(camera);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        player.setCell(0,0,playerCell);
        rederer.render();
    }

    @Override
    public boolean keyUp(int keycode){
        if(keycode == Input.Keys.UP){
            playerPosition.add(0,1);
        }
        else if(keycode == Input.Keys.DOWN){
            playerPosition.add(0,-1);
        }
        else if(keycode == Input.Keys.LEFT){
            playerPosition.add(-1,1);
        }
        else if(keycode == Input.Keys.RIGHT){
            playerPosition.add(1,1);
        }
        return true;
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
}
