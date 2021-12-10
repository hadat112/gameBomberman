package bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public abstract class Item extends Entity {
    private long buffTime;

    public Item(int x, int y, Image img) {
        super(x, y, img);
        setPassable(true);
        setBreakable(true);
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {

    }

    public abstract void eatItem(Bomber bomber);
}
