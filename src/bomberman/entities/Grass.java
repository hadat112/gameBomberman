package bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class Grass extends Entity {

    public Grass(int x, int y, Image img) {
        super(x, y, img);
        super.setBreakable(false);
        super.setPassable(true);
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {

    }
}
