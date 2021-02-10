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
import objects.Flag;
import objects.Robot;

public class HelloWorld extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;

    private TiledMap map;
    private TmxMapLoader mapLoader;

    private TiledMapTileLayer baseLayerLayer;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer playerLayer;

    private Texture playerTexture;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer rederer;

    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private Robot robot;

    Flag flag;

    public HelloWorld() {
        robot = new Robot(0,0);
        flag = new Flag(4,4);
    }


    @Override
    public void create() {
        batch = new SpriteBatch();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("RoboRallyTile.tmx");  
        camera = new OrthographicCamera();

        baseLayerLayer = (TiledMapTileLayer) map.getLayers().get("BaseLayer");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("Flag");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");

        TextureRegion[][] playerTextures = TextureRegion.split(new Texture("player.png"), 300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerDiedCell = new TiledMapTileLayer.Cell();
        playerWonCell = new TiledMapTileLayer.Cell();

        playerCell.setTile(new StaticTiledMapTile(playerTextures[0][0]));
        playerDiedCell.setTile(new StaticTiledMapTile(playerTextures[0][1]));
        playerWonCell.setTile(new StaticTiledMapTile(playerTextures[0][2]));

        camera.setToOrtho(false,11,11);
        camera.update();

        rederer = new OrthogonalTiledMapRenderer(map,1/300f);
        rederer.setView(camera);

        Gdx.input.setInputProcessor(this);
        robot.set(0,0);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        playerLayer.setCell(Math.round(robot.x),Math.round(robot.y),playerCell);
        rederer.render();
    }

    @Override
    public boolean keyUp(int keycode){
        playerLayer.setCell(Math.round(robot.x),Math.round(robot.y),null);

        if(keycode == Input.Keys.UP){
            robot.add(0,1);
            robot.setYPosition(robot.getY()+1);
        }
        else if(keycode == Input.Keys.DOWN){
            robot.add(0,-1);
            robot.setYPosition(robot.getY()-1);
        }
        else if(keycode == Input.Keys.LEFT){
            robot.add(-1,0);
            robot.setXPosition(robot.getX()-1);
        }
        else if(keycode == Input.Keys.RIGHT){
            robot.add(1,0);
            robot.setXPosition(robot.getX()+1);
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
