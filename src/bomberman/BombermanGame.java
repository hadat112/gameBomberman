package bomberman;

import bomberman.View.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;


public class BombermanGame extends Application {


    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        MenuView menuView = new MenuView();
        stage = menuView.getMainStage();
        stage.show();
    }

}