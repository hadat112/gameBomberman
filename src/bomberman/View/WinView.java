package bomberman.View;

import bomberman.Sound.Sound;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class WinView {
    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private AnchorPane winPane;
    private Stage winStage;
    private Scene winScene;
    private Button continueBtn;

    private GameView gameView;
    private Stage menuStage;

    public WinView(GameView gameView, Stage menuStage) {
        this.gameView = gameView;
        this.menuStage = menuStage;
        winPane = new AnchorPane();
        winScene = new Scene(winPane, WIDTH, HEIGHT);
        winStage = new Stage();
        winStage.setScene(winScene);
        addBackGround();
        createExitBtn();
    }

    public void show() {
        winStage.show();
        gameView.getTimer().stop();
        Sound.mute();
    }

    private void addBackGround(){
        Image backgroundImg = new Image(getClass().getResource("/textures/youWin.jpg").toExternalForm(),
                WIDTH, HEIGHT, false, true);
        BackgroundImage background= new BackgroundImage(backgroundImg, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null );
        winPane.setBackground(new Background(background));
    }

    private void createExitBtn(){
        Button exitBtn = new Button("Return to menu");
        exitBtn.setPrefHeight(40);
        exitBtn.setPrefWidth(200);
        exitBtn.setLayoutY(180);
        exitBtn.setLayoutX(100);
        exitBtn.setStyle("-fx-background-color: transparent;-fx-font-size: 20px;-fx-font-weight: bold;-fx-text-fill: red;");

        exitBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitBtn.setEffect(new DropShadow());
            }
        });

        exitBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitBtn.setEffect(null);
            }
        });

        exitBtn.setOnMouseClicked(e -> {
            gameView.getGameStage().close();
            menuStage.show();
            winStage.hide();
        });

        winPane.getChildren().add(exitBtn);
    }

}

