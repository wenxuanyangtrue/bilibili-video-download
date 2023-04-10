package top.xsword.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;


/*
 *ClassName:JavaFXMain
 *CreateTime:2022-01-23
 *author:三号男嘉宾
 *description:
 */

public class JavaFXMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainStage.build(primaryStage);
    }
}
