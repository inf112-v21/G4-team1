package inf112.skeleton.app;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

import java.io.File;

public class FileLoadTest {
    private TiledMap map;
    private TmxMapLoader mapLoader;

    @BeforeEach
    public void setUp(){
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("assets/RoboRallyTile2.tmx");
    }

    @Test
    public void checkMapExists(){
        File f = new File("assets/RoboRallyTile2.tmx");
        assertTrue(f.exists());
    }
}
