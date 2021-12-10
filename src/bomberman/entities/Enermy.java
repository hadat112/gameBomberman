package bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class Enermy extends Entity {

    public Enermy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setBreakable(true);
        setPassable(false);
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {

    }
}
