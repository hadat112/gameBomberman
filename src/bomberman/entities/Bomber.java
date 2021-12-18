package bomberman.entities;

import bomberman.GameMap.GameMap;
import bomberman.Sound.Sound;
import bomberman.graphics.Sprite;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class Bomber extends Entity {
    private int speed = 8;
    private int animate = 0;
    private int time = 8;
    private boolean isMoved = false;
    private int bomNum ;
    private int bomberWidth = 32;
    private int bomberHeight = 32;
    Sprite sprite;
    private Bomb bomb;

    private boolean die = false;

    private boolean win = false;

    private int bomPosX;
    private int bomPosY;

    public boolean isWin() {
        return win;
    }

    public boolean isDie() {
        return die;
    }

    public void setDie(boolean die) {
        this.die = die;
    }

    public void increaseBomNum() {
        bomNum++;
    }

    public void  increaseSpeed() {
        speed+=2;
    }

    private boolean canMove = true;

    public int getAnimate() {
        return animate;
    }

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        bomNum = 1;
    }

    @Override
    public void update(Scene scene, List<Entity> entities) {
        createListener(scene, entities);

        if(isDie()) {
            diedAnimation();
            if (getAnimate() > 25) {
                Sound.playLosingSound();
                Sound.stop();
                entities.remove(this);
            }
        }
        checkBomber(entities);

//        Kiểm tra xem có bomb đang được đặt không nếu có thì chạy animation trong 2s sau đó cho nổ
        for (int i = 0; i < entities.size(); i++) {
            Entity b = entities.get(i);
            if (b != null && b instanceof Bomb) {
                ((Bomb) b).runBombAnimation();

                if (System.currentTimeMillis() - ((Bomb) b).getPlantTime() > 2000) {
                    ((Bomb) b).isExploded = true;
                    if (((Bomb) b).canPlaySound) {
                        Sound.playExplosionSound();
                        ((Bomb) b).canPlaySound = false;
                    }
                    ((Bomb) b).explode();
                    ((Bomb) b).showExplosion();
                }
                if (System.currentTimeMillis() - ((Bomb)b).getPlantTime() >= 2500) {
                    GameMap.stillObjects[b.getY() / Sprite.SCALED_SIZE][b.getX() / Sprite.SCALED_SIZE].setPassable(true);
                    entities.remove(b);
                    bomNum++;
                }
            }
        }

    }

    public void diedAnimation() {
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2,
                    Sprite.player_dead3, animate++, time).getFxImage();
    }

//    Nhận vào từ bàn phím các lần bấm nút di chuyển và đặt bom
    public void createListener(Scene scene, List <Entity> entities) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!isDie()) {
                    animate++;
                    switch (event.getCode()) {
                        case UP:
                            moveUp();
                            break;
                        case DOWN:
                            moveDown();
                            break;
                        case LEFT:
                            moveLeft();
                            break;
                        case RIGHT:
                            moveRight();
                            break;
                        case SPACE:
                            plantBomb(entities);
                            break;
                    }
                }
//                if (event.getCode() == KeyCode.ESCAPE) {
//                    pauseView.showPauseScreen();
//                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                animate=0;
                switch (event.getCode()) {
                    case UP:
                        standStillUp();
                        break;
                    case DOWN:
                        standStillDown();
                        break;
                    case LEFT:
                        standStillLeft();
                        break;
                    case RIGHT:
                        standStillRight();
                        break;
                }
            }
        });
    }

    //Nếu có thể đi theo hướng nào sẽ cộng thêm vào hướng đó
    public void moveLeft() {
        if (Math.abs(y - y / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE) <= 6) y += Math.abs(y - y / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE);
        else if (Math.abs(y - y / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE) >= 26) y -= Sprite.SCALED_SIZE - Math.abs(y - y / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE);
        sprite = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, time);
        img = sprite.getFxImage();

        if (canMoveLeft()){
            move(-speed, 0);
        }
    }

    public void standStillLeft(){
        img = Sprite.player_left.getFxImage();
    }

    public void moveRight() {
        if (Math.abs(y - y / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE) <= 6) y += Math.abs(y - y / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE);
        else if (Math.abs(y - y / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE) >= 26) y -= Sprite.SCALED_SIZE - Math.abs(y - y / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE);
        sprite = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, time);
        super.img = sprite.getFxImage();

        if (canMoveRight()){
            move(speed, 0);
        }
    }

    public void standStillRight(){
        img = Sprite.player_right.getFxImage();
    }

    public void moveUp() {
        if (Math.abs(x - x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE) <= 6) x += Math.abs(x - x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE);
        else if (Math.abs(x - x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE) >= 26) x -= Sprite.SCALED_SIZE - Math.abs(x - x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE);
        sprite = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, time);
        img = sprite.getFxImage();

        if (canMoveUp()){
            move(0, -speed);
        }
    }

    public void standStillUp(){
        img = Sprite.player_up.getFxImage();
    }

    public void moveDown() {
        if (Math.abs(x - x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE) <= 6) x += Math.abs(x - x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE);
        else if (Math.abs(x - x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE) >= 26) x -= Sprite.SCALED_SIZE - Math.abs(x - x / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE - 32);
        sprite = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, time);
        img = sprite.getFxImage();

        if (canMoveDown()){
            move(0, speed);
        }
    }

    public void standStillDown(){
        img = Sprite.player_down.getFxImage();
    }

    private void move(int dx, int dy) {
        if (dx == 0 && dy == 0) return;
        if (canMove) {
            this.x = x + dx;
            this.y = y + dy;
        }
    }

//    Kiểm tra 4 hướng xem có đi được không
    private boolean canMoveLeft() {
        int posy = y;
        if (y > y / 32 * 32) posy = y + 32;
        if (checkPassable(GameMap.stillObjects[y / 32][(x - 1) / 32])
                || checkPassable(GameMap.stillObjects[posy / 32][(x - 1) / 32])) {
            return false;
        }
        return true;
    }

    private boolean canMoveRight() {
        int posy = y;
        if (y > y / 32 * 32) posy = y + 32;
        if (checkPassable(GameMap.stillObjects[y / 32][(x + 32) / 32])
                || checkPassable(GameMap.stillObjects[posy / 32][(x + 32) / 32])) {
            return false;
        }
        return true;
    }

    private boolean canMoveUp() {
        int posx = x;
        if (x > x / 32 * 32) posx = x + 32;
        if (checkPassable(GameMap.stillObjects[(y - 1) / 32][(x) / 32])
                || checkPassable(GameMap.stillObjects[(y - 1) / 32][posx / 32])) {
            return false;
        }
        return true;
    }

    private boolean canMoveDown() {
        int posx = x;
        if (x > x / 32 * 32) posx = x + 32;
        if (checkPassable(GameMap.stillObjects[(y + 32) / 32][(x) / 32])
                || checkPassable(GameMap.stillObjects[(y + 32) / 32][posx / 32])) {
            return false;
        }
        return true;
    }

    public boolean checkPassable(Entity obj) {
        if (obj != null) {
            if (obj.isPassable()) {
                return false;
            } else return checkCollisionStillObject(this, obj);
        }
        return false;
    }

//    Lấy vị trí hiện tại để đặt bom vào vị trí đó
    public void setBombPosition(int x, int y) {
        int ox = x + (int) Sprite.bomb.getFxImage().getWidth() / 2;
        int oy = y + (int) Sprite.bomb.getFxImage().getHeight() / 2;
        bomPosX = (ox / 32 * 32) / Sprite.SCALED_SIZE;
        bomPosY = (oy / 32 * 32) / Sprite.SCALED_SIZE;
    }

    //Đặt bom nếu bấm dấu cách
    private void plantBomb(List <Entity> entities){
        if (!isDie() && bomNum > 0) {
            bomNum--;
            setBombPosition(x, y);
            bomb = new Bomb(bomPosX, bomPosY, Sprite.bomb.getFxImage());
            bomb.addExplodeAnimation();
            bomb.setPlantTime(System.currentTimeMillis());
            bomb.canPlaySound = true;
            entities.add(bomb);
//            GameMap.entitiesMap[bomb.getY()/32][bomb.getX()/32].setPassable(false);
        }
    }

    // Kiểm tra va chạm của bomberman với item, balloom, portal, Oneal
    private void checkBomber(List<Entity> entities) {
        if (this != null) {
            for(int i = 0; i < entities.size(); i++) {
                Entity entity = entities.get(i);
                if ( entity instanceof Item) {
                    if(checkCollisionStillObject(this, entity)) {
                        ((Item) entity).eatItem(this);
                        entities.remove(entity);
                    }
                }else if (entity instanceof Portal) {
                    if (((Portal) entity).isShow() && checkCollision(this, entity)) {
                        win = true;
                    }
                } else if (entity instanceof Balloom) {
                    if (!((Balloom) entity).isDied() && checkCollision(this, entity)) {
                        this.setDie(true);
                        return;
                    }
                } else if (entity instanceof Oneal) {
                    if (entity != null && checkCollision(this, entity)) {
                        this.setDie(true);
                        return;
                    }
                }
            }
        }
    }
}
