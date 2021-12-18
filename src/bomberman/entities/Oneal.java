package bomberman.entities;

import bomberman.GameMap.GameMap;
import bomberman.graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Oneal extends Enermy{
    private int speed = 1;
    private List<Integer> possibleDir;
    private List<Integer> moveDir;
    private List<Integer> safeDir;
    private int currentDir;
    private int oldDir;

    private boolean right;
    private int animate = 0;
    private int time = 25;


    private boolean isDied = false;
    private long diedTime;

    private Bomber bomber;
    private Bomb bomb;
    private int bomNum = 1;

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        possibleDir = new ArrayList<>();
        moveDir = new ArrayList<>();
        safeDir = new ArrayList<>();
        currentDir = 4;
    }

    public void hate(Bomber bomber){
        this.bomber = bomber;
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {
        if (animate >= 6000) animate = 0;
        animate++;

        if (moveDir.size() > 4) {
            moveDir.remove(0);
        }

        if (isDied){
            if (System.currentTimeMillis() - getDiedTime() < 100) {
                diedAni();
            }else {
                entities.remove(this);
                GameMap.enermyNum--;
            }
            currentDir = 0;
        }
        entities.forEach(entity -> {
            if (entity instanceof Bomber) {
                hate((Bomber) entity);
            }
        });
        followDir();
    }

    public void findWays() {
        possibleDir.clear();
        if (x < bomber.x && !checkCollision(GameMap.stillObjects[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 1])) {
            possibleDir.add(6);
        }
        if (x > bomber.x && !checkCollision(GameMap.stillObjects[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1])) {
            possibleDir.add(4);
        }
        if (y > bomber.y && !checkCollision(GameMap.stillObjects[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE])) {
            possibleDir.add(8);
        }
        if (y < bomber.y && !checkCollision(GameMap.stillObjects[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE])) {
            possibleDir.add(2);
        }
    }

    private void chooseAWay(){
        findWays();
        oldDir = currentDir;
        if (!possibleDir.isEmpty()){
            for (Integer i : possibleDir){
                if (currentDir!= i){
                    currentDir = i;
                }
            }
        }
    }

    private void move(int x, int y) {
        if (x == 0 && y == 0) return;
        this.x += x;
        this.y += y;
    }

    private void followDir() {
        switch (currentDir) {
            case 2:
                moveDown();
                if (right) {
                    aimationRight();
                } else {
                    animationLeft();
                }
                break;
            case 4:
                moveLeft();
                animationLeft();
                right = false;
                break;
            case 6:
                moveRight();
                aimationRight();
                right = true;
                break;
            case 8:
                moveUp();
                if (right) {
                    aimationRight();
                } else {
                    animationLeft();
                }
                break;
            case 0:
                standStill();
                break;
        }
    }

    private void standStillLeft() {
        img = Sprite.oneal_left1.getFxImage();
    }

    private void standStillRight() {
        img = Sprite.oneal_right1.getFxImage();
    }

    private void animationLeft() {
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, time).getFxImage();
    }

    private void aimationRight() {
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, time).getFxImage();
    }

    private void printPosibleDir(){
        for (Integer i : possibleDir){
            System.out.println(i);
        }
    }

    private void moveLeft() {
        if (!checkCollision(GameMap.stillObjects[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1])) {
            move(-speed, 0);
        } else {
            chooseAWay();
        }
    }

    private void moveRight() {
        if (!checkCollision(GameMap.stillObjects[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 1])) {
            move(speed, 0);
        } else {
            chooseAWay();
        }

    }

    private void moveUp() {
        if (!checkCollision(GameMap.stillObjects[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE]))
            move(0, -speed);
        else {
            chooseAWay();
        }
    }

    private void moveDown() {
        if (!checkCollision(GameMap.stillObjects[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE]))
            move(0, speed);
        else {
            chooseAWay();
        }
    }

    public boolean checkCollision(Entity obj) {
        if (obj != null) {
            if (obj.isPassable()) {
                return false;
            } else {
                return Bomber.checkCollisionStillObject(this, obj);
            }
        } else return false;
    }

    private void standStill() {
        move(0, 0);
        if (right) {
            standStillRight();
        } else {
            standStillLeft();
        }
    }

    public boolean isDied() {
        return isDied;
    }

    public void setDied(boolean died) {
        isDied = died;
    }

    public void setDiedTime(long time){
        diedTime = time;
    }

    public long getDiedTime() {
        return diedTime;
    }

    public void diedAni(){
        img = Sprite.oneal_dead.getFxImage();
    }
}
