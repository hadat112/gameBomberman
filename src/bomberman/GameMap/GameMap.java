package bomberman.GameMap;

import bomberman.entities.*;
import bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private static final String LEVEL1 = "./res/levels/Level1.txt";

    private static int level = 1;

    public static int mapWidth;
    public static int mapHeight;
    private static BufferedReader br;
    public static Entity[][] stillObjects;

    public static int bomberX;
    public static int bomberY;

//    public static List<Balloom> ballooms = new ArrayList<>();
//    public static List<Buff> buffs = new ArrayList<>();
//    public static Portal portal;
//    public static AIEnemy oneal;
    public static int enemyNums;
    private GameMap(){}

    public static void init(List<Entity> entities){
        loadMapFromFile(LEVEL1, entities);
    }

    private static void loadMapFromFile(String filePath, List<Entity> entities) {
        char ch;
        try {
            br = new BufferedReader(new FileReader(filePath));
            level = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            mapWidth = Integer.parseInt(br.readLine());
            stillObjects = new Entity[mapHeight][mapWidth];
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    ch = (char)br.read();
                    if (ch == 'p'){
                        bomberX = i;
                        bomberY = j;
                    }
                    if (ch == '1'){
                        entities.add(new Balloom(j, i, Sprite.balloom_right1.getFxImage()));
                    }
                    if (ch == 's'){
                        Item speedItem =  new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        entities.add(speedItem);
                    }
                    if (ch == 'b'){
                        Item bombItem = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        entities.add(bombItem);
                    }
                    if (ch == 'f'){
                        Item flameItem = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        entities.add(flameItem);
                    }
                    if (ch == 'x'){
                        entities.add(new Portal(j, i, Sprite.portal.getFxImage()));
                    }
                    if (ch == 'o'){
                        entities.add(new Oneal(j, i , Sprite.oneal_left1.getFxImage()));
                    }
                    if (ch == '#') {
                        stillObjects[i][j] = new Wall(j, i, Sprite.wall.getFxImage());
                    } else if (ch == '*'){
                        stillObjects[i][j] = new Brick(j, i, Sprite.brick.getFxImage());
                    } else {
                        stillObjects[i][j] = new Grass(j, i, Sprite.grass.getFxImage());
                    }
                }
                br.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Entity[][] getMap() {
        return stillObjects;
    }

    public static int getEnemyNums(){
        return enemyNums;
    }
}
