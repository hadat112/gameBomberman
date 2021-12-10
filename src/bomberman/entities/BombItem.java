package bomberman.entities;

import javafx.scene.image.Image;

public class BombItem extends Item{
    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void eatItem(Bomber bomber) {
        bomber.increaseBomNum();
    }
}
