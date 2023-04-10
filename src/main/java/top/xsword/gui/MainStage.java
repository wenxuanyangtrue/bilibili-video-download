package top.xsword.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import top.xsword.controller.SearchController;

/*
 *ClassName:MainPage
 *CreateTime:2022-01-23
 *author:三号男嘉宾
 *description:
 */
public class MainStage {
    private static final ThreadLocal<Stage> stageLocal = new ThreadLocal<>();
    private static final ThreadLocal<BorderPane> borderLocal = new ThreadLocal<>();

    public static void build(Stage primaryStage) {
        primaryStage.setTitle("bilibili-video-download");
        primaryStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("../../../img/icon.png")));

        BorderPane mainPane = new BorderPane();
        mainPane.setId("mainVBox");

        Scene scene = new Scene(mainPane, 500, 60);

        scene.getStylesheets().add(MainStage.class.
                getResource("../../../css/base.css").toExternalForm());

        stageLocal.set(primaryStage);
        borderLocal.set(mainPane);
        buildMenu();
        buildCenter();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private static void buildCenter() {
        BorderPane mainPane = borderLocal.get();
        HBox hBox = new HBox();
        Label text = new Label("video link");
        TextField searchBox = new TextField();
        Button searchBtn = new Button("search");

        searchBtn.getStyleClass().add("button_base");

        hBox.getChildren().addAll(text, searchBox, searchBtn);

        HBox.setMargin(text, new Insets(5, 0, 0, 5));
        HBox.setMargin(searchBox, new Insets(0, 0, 0, 10));
        HBox.setMargin(searchBtn, new Insets(2, 0, 0, 10));

        searchBox.setMinSize(360, 30);
        hBox.setMaxSize(500, 30);

        searchBtn.setOnMouseClicked((mouseEvent) -> {
            String url = searchBox.getText();
            new SearchController(url);
        });

        mainPane.setCenter(hBox);
    }

    private static void buildMenu() {
        BorderPane mainPane = borderLocal.get();

        MenuBar menuBar = new MenuBar();
        mainPane.setTop(menuBar);

        Menu settingMenu = new Menu("Setting");
        Menu viewMenu = new Menu("View");

        MenuItem cookieMenuItem = new MenuItem("Cookie");
        MenuItem downloadPathMenuItem = new MenuItem("Download Path");
        MenuItem downloadListMenuItem = new MenuItem("Download List");

        cookieMenuItem.setOnAction((actionEvent) -> {
            new CookieStage().build();
        });

        downloadPathMenuItem.setOnAction((actionEvent) -> {
            new DownloadPathStage().build();
        });

        downloadListMenuItem.setOnAction((actionEvent)->{
            new DownloadList().build();
        });

        settingMenu.getItems().addAll(cookieMenuItem, downloadPathMenuItem);
        viewMenu.getItems().addAll(downloadListMenuItem);
        menuBar.getMenus().addAll(settingMenu,viewMenu);
    }

}
