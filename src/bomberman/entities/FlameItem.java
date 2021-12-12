package bomberman.entities;

import javafx.scene.image.Image;

public class FlameItem extends Item {
    public FlameItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void eatItem(Bomber bomber) {
        Bomb.increaseFlameLength();
        System.out.println(Bomb.getFlameLength());
    }
}
