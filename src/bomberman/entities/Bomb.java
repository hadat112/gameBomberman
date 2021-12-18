package bomberman.entities;

import bomberman.GameMap.GameMap;
import bomberman.graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity{
    public List<Flame> leftFlames = new ArrayList<Flame>();
    public List<Flame> rightFlames = new ArrayList<Flame>();
    public List<Flame> upFlames = new ArrayList<Flame>();
    public List<Flame> downFlames = new ArrayList<Flame>();
    public Flame centerFlame;
    private static int flameLength = 1;

    boolean isExploded = false;
    boolean canPlaySound = false;

    private long plantTime = 0;

    public static int getFlameLength() {
        return flameLength;
    }

    private int animate = 0;
    private int time = 25;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void setExploded(boolean exploded) {
        isExploded = exploded;
    }

    public long getPlantTime() {
        return plantTime;
    }

    public static void increaseFlameLength() {
        flameLength++;
    }

    public void setPlantTime(long plantTime) {
        this.plantTime = plantTime;
    }

    public Bomb(int x, int y) {
        super(x, y);
    }

    public Bomb() {
        super();
    }

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setPassable(false);
        setBreakable(true);
        plantTime = System.currentTimeMillis();
    }

    public void runBombAnimation() {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate++, time).getFxImage();
    }

    public void explode() {
        img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                Sprite.bomb_exploded2, animate, time).getFxImage();
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {
        centerFlame.update(scene, entities);
        downFlames.forEach(f -> f.update(scene, entities));
        upFlames.forEach(f -> f.update(scene, entities));
        rightFlames.forEach(f -> f.update(scene, entities));
        leftFlames.forEach(f -> f.update(scene, entities));
    }

    public static void setFlameLength(int flameLength) {
        Bomb.flameLength = flameLength;
    }

    public void showExplosion() {
        centerFlame.setActive(true);
        upFlames.forEach(flame -> flame.explodeVertical());
        downFlames.forEach(flame -> flame.explodeVertical());
        leftFlames.forEach(flame -> flame.explodeHorizon());
        rightFlames.forEach(flame -> flame.explodeHorizon());
    }

//    Thêm lửa vào bốn phía
    public void addExplodeAnimation() {
        int posX = x / Sprite.SCALED_SIZE;
        int posY = y / Sprite.SCALED_SIZE;
        centerFlame = new Flame(posX, posY, this.img);
        addLeftFlame(posX, posY);
        addRightFlame(posX, posY);
        addUpFlame(posX, posY);
        addDownFlame(posX, posY);
    }

    //    Kiểm tra bên trái có bị chặn lửa không
    private void addLeftFlame(int x, int y) {
        for (int i = 0; i < flameLength; i++) {
            if (x - 1 - i >= 0 && GameMap.stillObjects[y][x - 1 - i].isPassable()) {
                leftFlames.add(new Flame(x - 1 - i, y, Sprite.explosion_horizontal.getFxImage()));
            } else {
                if (GameMap.stillObjects[y][x - 1 - i].isBreakable()) {
                    leftFlames.add(new Flame(x - 1 - i, y, Sprite.explosion_horizontal.getFxImage()));
                    break;
                } else if (!GameMap.stillObjects[y][x - 1 - i].isBreakable()) {
                    break;
                }
            }

        }
    }

    //    Kiểm tra bên phải có bị chặn lửa không
    private void addRightFlame(int x, int y) {
        for (int i = 0; i < flameLength; i++) {
            if (x + 1 + i < GameMap.mapWidth && GameMap.stillObjects[y][x + 1 + i].isPassable()) {
                rightFlames.add(new Flame(x + 1 + i, y, Sprite.explosion_horizontal.getFxImage()));
            } else {
                if (GameMap.stillObjects[y][x + 1 + i].isBreakable()) {
                    rightFlames.add(new Flame(x + 1 + i, y, Sprite.explosion_horizontal.getFxImage()));
                    break;
                } else if (!GameMap.stillObjects[y][x + 1 + i].isBreakable()) {
                    break;
                }
            }

        }
    }

    //    Kiểm tra bên trên có bị chặn lửa không
    private void addUpFlame(int x, int y) {
        for (int i = 0; i < flameLength; i++) {
            if (y - 1 - i >= 0 && GameMap.stillObjects[y - 1 - i][x].isPassable()) {
                upFlames.add(new Flame(x, y - 1 - i, Sprite.explosion_vertical.getFxImage()));
            } else {
                if (GameMap.stillObjects[y - 1 - i][x].isBreakable()) {
                    upFlames.add(new Flame(x, y - 1 - i, Sprite.explosion_vertical.getFxImage()));
                    break;
                } else if (!GameMap.stillObjects[y - 1 - i][x].isBreakable()) {
                    break;
                }
            }
        }
    }

//    Kiểm tra bên dưới có bị chặn lửa không
    private void addDownFlame(int x, int y) {
        for (int i = 0; i < flameLength; i++) {
            if (y + 1 + i <= GameMap.mapHeight && GameMap.stillObjects[y + 1 + i][x].isPassable()) {
                downFlames.add(new Flame(x, y + 1 + i, Sprite.explosion_vertical.getFxImage()));
            } else {
                if (GameMap.stillObjects[y + 1 + i][x].isBreakable()) {
                    downFlames.add(new Flame(x, y + 1 + i, Sprite.explosion_vertical.getFxImage()));
                    break;
                } else if (!GameMap.stillObjects[y + 1 + i][x].isBreakable()) {
                    break;
                }
            }

        }
    }
}
