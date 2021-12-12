package bomberman.View;

import bomberman.GameMap.GameMap;
import bomberman.Sound.Sound;
import bomberman.entities.Bomb;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import bomberman.entities.Portal;
import bomberman.graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameView {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private Stage menuStage;
    private Stage gameStage;

    private GraphicsContext gc;
    private Canvas canvas;
    private AnimationTimer timer;

    Scene scene;
    private List<Entity> entities = new ArrayList<>();
    Bomber bomberman;

    public void playGame() {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        scene = new Scene(root);

        gameStage = new Stage();
        gameStage.setScene(scene);

        Sound.init();
        Sound.playBackgroundMusic();
        GameMap.init(entities);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update(scene, entities);
                if (bomberman.isDie() && bomberman.getAnimate() > 25) {
                    endGame();
                }
            }
        };
        timer.start();

        bomberman = new Bomber(GameMap.bomberX, GameMap.bomberY, Sprite.player_down.getFxImage());
        entities.add(bomberman);
        gameStage.show();
    }

    private void endGame() {
        Bomb.setFlameLength(1);
        gameStage.close();
        menuStage.show();
        timer.stop();
        Sound.stop();
    }

    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();

    }

    public void update(Scene scene, List<Entity> entities) {
        for (int i = 0; i < entities.size(); i++)
            entities.get(i).update(scene, entities);
//            entities.forEach(entity -> entity.update(scene, entities));
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                GameMap.stillObjects[i][j].render(gc);
            }
        }

        entities.forEach(g -> {
            if (g instanceof Portal) {
                if (((Portal) g).isShow())
                    g.render(gc);
            } else {
                g.render(gc);
            }
            if(g instanceof Bomb && ((Bomb) g).isExploded()) {
                ((Bomb) g).downFlames.forEach(f -> f.render(gc));
                ((Bomb) g).upFlames.forEach(f -> f.render(gc));
                ((Bomb) g).rightFlames.forEach(f -> f.render(gc));
                ((Bomb) g).leftFlames.forEach(f -> f.render(gc));
            }
        });
    }
}
