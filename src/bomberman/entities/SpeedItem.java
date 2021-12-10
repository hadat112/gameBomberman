package bomberman.entities;

import javafx.scene.image.Image;

public class SpeedItem extends Item{

    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void eatItem(Bomber bomber) {
        bomber.increaseSpeed();
    }
}
