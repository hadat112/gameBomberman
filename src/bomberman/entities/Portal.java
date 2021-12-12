package bomberman.entities;

import bomberman.GameMap.GameMap;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class Portal extends Entity{
    private boolean isShow = false;

    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setBreakable(false);
        setPassable(true);
    }

    public boolean isShow() {
        return isShow;
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {
        if (GameMap.enermyNum == 0) {
            isShow = true;
        }
    }
}
