package bomberman.entities;

import bomberman.GameMap.GameMap;
import bomberman.graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.List;

public class Flame extends Entity{
    private int animate = 0;
    private int time = 25;

    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean active = false;
    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }



    @Override
    public void update(Scene scene, List<Entity> entities) {
//        Nếu bom nổ ktra va trạm
        if (active) {
            checkFlameCollision(entities);
        }
    }

    //animation nổ ngang
    public void explodeHorizon() {
        active = true;
        img = Sprite.movingSprite(Sprite.explosion_horizontal,
                Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animate++, time).getFxImage();
    }

    //animation nổ dọc
    public void explodeVertical() {
        active = true;
        img = Sprite.movingSprite(Sprite.explosion_vertical,
                Sprite.explosion_vertical1, Sprite.explosion_vertical2, animate++, time).getFxImage();
    }


//    Kiểm tra nếu lửa chạm vào các entities
    private void checkFlameCollision(List<Entity> entities) {
        Entity obj = GameMap.stillObjects[this.getY() / Sprite.SCALED_SIZE][this.getX() / Sprite.SCALED_SIZE];

//        Nếu là gạch
        if (obj.isBreakable()) {
            Brick b = (Brick) obj;
            if (!b.isBreak()) {
                b.setBreakingTime(System.currentTimeMillis());
                b.setBreak(true);
            }
            if (System.currentTimeMillis() - b.getBreakingTime() < 300) {
                b.explodedAni();
                return;
            } else {
                GameMap.stillObjects[this.getY() / Sprite.SCALED_SIZE][this.getX() / Sprite.SCALED_SIZE] =
                        new Grass(this.getX() / Sprite.SCALED_SIZE, this.getY() / Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                return;
            }
        }

        for (Entity entity : entities) {
//            Nếu là enermy
            if (entity instanceof Balloom) {
                if (Bomber.checkCollision(entity, this) && !((Balloom) entity).isDied()) {
                    ((Balloom) entity).setDied();
                    return;
                }
//                Nếu là bomberman
            } else if (entity instanceof Bomber) {
                if (!((Bomber) entity).isDie() &&  Bomber.checkCollision(entity, this)) {
                    ((Bomber) entity).setDie(true);
                    return;
                }
//            Nếu là enermy
            } else if (entity instanceof Oneal) {
                if (Bomber.checkCollision(entity, this)) {
                ((Oneal) entity).setDied(true);
                ((Oneal) entity).setDiedTime(System.currentTimeMillis());
                }
            }
        }
    }
}
