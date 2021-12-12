
package bomberman.View;

import bomberman.Sound.Sound;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class MenuView {
    private AnchorPane mainPane;
    private Stage mainStage;
    private Scene mainScene;
    private boolean muteNN = false;
    private boolean muteNG = false;

    public MenuView() {
        mainPane = new AnchorPane();
        mainScene  = new Scene(mainPane, 800, 600);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle("Bomberman");
        addBackgroung();
        addButton();
        mainScene.getStylesheets().addAll(this.getClass().getResource("/textures/style.css").toExternalForm());
    }

    public Stage getMainStage(){
        return mainStage;
    }

    private void addButton() {
        createNhacgameButton();
        createPlayButton();
        createExitButton();
    }


    private void createNhacgameButton(){
        Button button = new Button("");
        mainPane.getChildren().add(button);
        button.setLayoutY(450);
        button.setLayoutX(375);
        button.setPrefWidth(50);
        button.setPrefHeight(50);
        button.setId("unmute");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!muteNG) {
                    Sound.setIsMute(true);
                    button.setId("mute");
                    muteNG = true;
                } else {
                    Sound.setIsMute(false);
                    button.setId("unmute");
                    muteNG = false;
                }
            }
        });

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(new DropShadow());
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(null);
            }
        });
    }

    private void createPlayButton() {
        Button button = new Button("PLAY");
        button.setId("button");
        button.setPrefHeight(61);
        button.setPrefWidth(171);
        mainPane.getChildren().add(button);
        button.setLayoutY(300);
        button.setLayoutX(320);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(new DropShadow());
            }
        });

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameView gameView = new GameView();
                gameView.createNewGame(mainStage);
                gameView.playGame();
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(null);
            }
        });
    }

    private void createExitButton() {
        Button button = new Button("EXIT");
        button.setId("button");
        button.setPrefHeight(61);
        button.setPrefWidth(171);
        mainPane.getChildren().add(button);
        button.setLayoutY(375);
        button.setLayoutX(320);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(new DropShadow());
            }
        });

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainStage.close();
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(null);
            }
        });
    }

    private void addBackgroung() {
        Image backgroundImg = new Image(getClass().getResource("/textures/bombermanBackGround.png").toExternalForm(),
                800, 600, false, true);
        BackgroundImage background= new BackgroundImage(backgroundImg, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null );
        mainPane.setBackground(new Background(background));
    }

}
