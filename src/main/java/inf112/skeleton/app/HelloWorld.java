package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class HelloWorld implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    private TiledMap map;
    private TmxMapLoader mapLoader;

    private TiledMapTileLayer baseLayer;
    private TiledMapTileLayer hole;
    private TiledMapTileLayer flag;
    private TiledMapTileLayer player;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer rederer;

    @Override
    public void create() {
        batch = new SpriteBatch();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("RoboRallyTile.tmx");

        camera = new OrthographicCamera();
        rederer = new OrthogonalTiledMapRenderer(map,1/300);
        rederer.setView(camera);

        baseLayer = (TiledMapTileLayer) map.getLayers().get("BaseLayer");
        hole = (TiledMapTileLayer) map.getLayers().get("Hole");
        flag = (TiledMapTileLayer) map.getLayers().get("Flag");
        player = (TiledMapTileLayer) map.getLayers().get("Player");

        camera.setToOrtho(false,11,11);
        camera.update();

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

        rederer.render();
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
