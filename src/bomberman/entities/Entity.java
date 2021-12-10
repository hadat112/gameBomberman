package bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import bomberman.graphics.Sprite;

import java.util.List;

public abstract class Entity {
    private static final int OFFSET = 4;
    private boolean breakable;
    private boolean passable;
    private boolean eatable;

    public boolean isEatable() {
        return eatable;
    }

    public void setEatable(boolean eatable) {
        this.eatable = eatable;
    }

    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth(){
        return (int)img.getWidth();
    }

    public int getHeight(){
        return (int)img.getHeight();
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Entity() {

    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update(Scene scene, List<Entity> entities);

//    Kiểm tra va chạm với entities
    public static boolean checkCollision(Entity e1, Entity e2) {
        boolean collisionX = e1.getX() + e1.getWidth() >= e2.getX() + OFFSET && e2.getX() + e2.getWidth() >= e1.getX()+ OFFSET;
        boolean collisionY = e1.getY() + e1.getHeight() >= e2.getY() + OFFSET && e2.getY() + e2.getHeight() >= e1.getY()+ OFFSET;
        return collisionX && collisionY;
    }

//    Kiểm tra va chạm với stillObject
    public static boolean checkCollisionStillObject(Entity e1, Entity e2) {
        boolean collisionX = e1.getX() + e1.getWidth() >= e2.getX() && e2.getX() + e2.getWidth() >= e1.getX();
        boolean collisionY = e1.getY() + e1.getHeight() >= e2.getY() && e2.getY() + e2.getHeight() >= e1.getY();
        return collisionX && collisionY;
    }

}
