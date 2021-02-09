package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

public class FileLoadTest {
    @Test
    public void checkMapExists(){
        File f = new File("assets/RoboRallyTile.tmx");
        assertTrue(f.exists());
    }

    /**
    @Test
    public void checkBaselayerExists(){

    }

    @Test
    public void checkHoleExists(){

    }

    @Test
    public void checkFlagExists(){

    }

    @Test
    public void checkPlayerExists(){

    }
    **/
}
