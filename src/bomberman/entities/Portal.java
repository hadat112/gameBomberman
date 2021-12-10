package bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class Portal extends Entity{

    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setBreakable(false);
        setPassable(true);
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {

    }
}
