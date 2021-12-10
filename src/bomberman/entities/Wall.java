package bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class Wall extends Entity {

    public Wall(int x, int y, Image img) {
        super(x, y, img);
        super.setBreakable(false);
        super.setPassable(false);
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {

    }
}
