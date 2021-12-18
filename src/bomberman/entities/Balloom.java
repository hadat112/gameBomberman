package bomberman.entities;

import bomberman.GameMap.GameMap;
import bomberman.graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Balloom extends Enermy{

    private boolean right;
    private boolean left;

    private int balloomWidth = 32;
    private int balloomHeight = 32;
    private final byte SPEED = 1;
    private int animate = 0;
    private int time = 40;

    private boolean isDied = false;
    private long diedTime;
    private int dir;
    private List<Integer> possibleDirection = new ArrayList<>();
    private int numOfPosDir;

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        dir = 6;
        moveLeft();
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {
        moveRandDir();
        animate++;

//        Nếu chết thì hiện animation trong 0.5s và xoá khỏi entities
        if(isDied){
            if(System.currentTimeMillis() - diedTime < 500) {
                disappearAnimation();
            } else {
                entities.remove(this);
                GameMap.enermyNum--;
            }
        }
    }

    //Di chuyển sang trái, nếu bị cản thì đổi hướng
    private void moveLeft() {
        if (!checkCollisionAround(GameMap.stillObjects[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1])) {
            x -= SPEED;
            left = true;
        } else {
            changeDir();
        }
    }

    //Di chuyển sang phải, nếu bị cản thì đổi hướng
    private void moveRight() {
        if (!checkCollisionAround(GameMap.stillObjects[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 1])) {
            x += SPEED;
            left = false;
        } else {
            changeDir();
        }
    }

    //Di chuyển lên trên, nếu bị cản thì đổi hướng
    private void moveUp() {
        if (!checkCollisionAround(GameMap.stillObjects[(y - Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE])) {
            y -= SPEED;
        } else {
            changeDir();
        }
    }

    //Di chuyển xuống dưới, nếu bị cản thì đổi hướng
    private void moveDown() {
        if (!checkCollisionAround(GameMap.stillObjects[(y + Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE])) {
            y += SPEED;
        } else {
            changeDir();
        }
    }

    //Dung lai
    public void stop() {
        x += 0;
        y += 0;
        dir = 0;
    }

    //hoạt ảnh trái
    private void animationLeft() {
        this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2,
                Sprite.balloom_left3, animate, time).getFxImage();
    }

    //hoạt ảnh phải
    private void animationRight() {
        this.img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2,
                Sprite.balloom_right3, animate, time).getFxImage();
    }

    //Chọn 1 hướng ngẫu nhiên
    private int getRandomPossibleDir() {
        numOfPosDir = possibleDirection.size();
        int rand = (int) Math.floor(Math.random() * numOfPosDir);
        dir = possibleDirection.get(rand);
        return dir;
    }

    /*public void find3PossibleDir(){
        checkAround();
        if (possibleDirection.size()>=3){
            numOfPosDir = possibleDirection.size();
            int rand = (int) Math.floor(Math.random() * numOfPosDir);
            dir = possibleDirection.get(rand);
        }
    }*/

    //Kiểm tra 4 hướng xung quanh
    private void checkAround() {
        possibleDirection.clear();
        if (!checkCollisionAround(GameMap.stillObjects[y / Sprite.SCALED_SIZE][(x + balloomWidth) / Sprite.SCALED_SIZE])) {
            possibleDirection.add(6);
        }
        if (!checkCollisionAround(GameMap.stillObjects[y / Sprite.SCALED_SIZE][(x - balloomWidth) / Sprite.SCALED_SIZE])) {
            possibleDirection.add(4);
        }
        if (!checkCollisionAround(GameMap.stillObjects[(y + balloomHeight) / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE])) {
            possibleDirection.add(2);
        }
        if (!checkCollisionAround(GameMap.stillObjects[(y - balloomHeight) / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE])) {
            possibleDirection.add(8);
        }
    }

    //Đổi hướng di chuyển
    private void changeDir() {
        checkAround();
        getRandomPossibleDir();
    }

    //Di chuyển theo hướng
    public void moveRandDir() {
        switch (dir) {
            case 2:
                moveDown();
                loadAnimation();
                break;
            case 4:
                moveLeft();
                animationLeft();
                break;
            case 6:
                moveRight();
                animationRight();
                break;
            case 8:
                moveUp();
                loadAnimation();
                break;
            case 0:
                stop();
                break;
        }
    }

    //Kiểm tra va chạm
    public boolean checkCollisionAround(Entity obj) {
        if (obj != null) {
            if (obj.isPassable()) {
                return false;
            } else {
                boolean collisionX = x + Sprite.SCALED_SIZE >= obj.x && obj.x + obj.getWidth() >= x;
                boolean collisionY = y + Sprite.SCALED_SIZE >= obj.y && obj.y + obj.getHeight() >= y;
                return collisionX && collisionY;
            }
        } else return false;
    }

    //Chọn hoạt ảnh phù hợp
    private void loadAnimation() {
        if (left) {
            animationLeft();
        } else {
            animationRight();
        }
    }

    public void disappearAnimation() {
        img = Sprite.balloom_dead.getFxImage();
    }

    public void setDied(){
        isDied = true;
        setDiedTime(System.currentTimeMillis());
        stop();
    }

    public boolean isDied() {
        return isDied;
    }

    public long getDiedTime() {
        return diedTime;
    }

    public void setDiedTime(long dieTime) {
        this.diedTime = dieTime;
    }
}
