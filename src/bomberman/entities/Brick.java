package bomberman.entities;

import bomberman.graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class Brick extends Entity{
    private int animate = 0;
    private int time =10;
    private long breakingTime;
    private boolean collisionDetected = false;
    private boolean isBreak = false;


    public Brick(int x, int y, Image img) {
        super(x, y, img);
        setPassable(false);
        setBreakable(true);
    }


    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    public void setCollisionDetected(boolean collisionDetected) {
        this.collisionDetected = collisionDetected;
    }

    public void setBreakingTime(long time) {
        breakingTime = time;
    }

    public long getBreakingTime() {
        return breakingTime;
    }

    public void explodedAni(){
        img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate++, time).getFxImage();
    }

    public boolean isBreak() {
        return isBreak;
    }

    public void setBreak(boolean aBreak) {
        isBreak = aBreak;
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {

    }
}
