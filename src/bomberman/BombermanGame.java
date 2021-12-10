package bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import bomberman.graphics.Sprite;
import bomberman.GameMap.GameMap;
import bomberman.entities.*;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    private static final int OFFSET = 8;

    private GraphicsContext gc;
    private Canvas canvas;
    Scene scene;
    private List<Entity> entities = new ArrayList<>();
    Bomber bomberman;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        GameMap.init(entities);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update(scene, entities);
            }
        };
        timer.start();

        bomberman = new Bomber(GameMap.bomberX, GameMap.bomberY, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void update(Scene scene, List<Entity> entities) {
        entities.forEach(entity -> entity.update(scene, entities));
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                GameMap.stillObjects[i][j].render(gc);
            }
        }

        entities.forEach(g -> {
            if(g instanceof Bomb && ((Bomb) g).isExploded()) {
                ((Bomb) g).downFlames.forEach(f -> f.render(gc));
                ((Bomb) g).upFlames.forEach(f -> f.render(gc));
                ((Bomb) g).rightFlames.forEach(f -> f.render(gc));
                ((Bomb) g).leftFlames.forEach(f -> f.render(gc));
            }
            g.render(gc);
        });
    }
}
