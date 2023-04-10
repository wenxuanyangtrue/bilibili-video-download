package top.xsword.gui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import top.xsword.controller.DownloadController;
import top.xsword.entity.Video;

public class DownloadList extends Stage {

    private BorderPane borderPane; // 主窗口布局
    private ScrollPane scrollPane; //滚轮窗格
    private VBox vBox; // 下载列表的列内容布局
    public static Thread listUpdate;

    public void build() {
        getIcons().add(new Image(MainStage.class.getResourceAsStream("../../../img/icon.png")));
        setTitle("download list");

        borderPane = new BorderPane();
        scrollPane = new ScrollPane();
        vBox = new VBox();

        borderPane.getStyleClass().add("main_box");
        scrollPane.getStyleClass().add("download_list");
        vBox.getStyleClass().add("box_col");

        scrollPane.setContent(vBox);
        borderPane.setTop(scrollPane);

        loadList();
        listUpdate = new Thread(new Task<>() {
            @Override
            protected Object call() {
                while (true) {
                    if (!DownloadController.isFinish) {
                        synchronized (DownloadController.class) {
                            Platform.runLater(() -> {
                                vBox.getChildren().clear();
                                loadList();
                            });
                            DownloadController.class.notify();
                        }
                    }
                }
            }
        });
        listUpdate.setDaemon(true);
        listUpdate.start();

        Scene scene = new Scene(borderPane, 400, 400);
        scene.getStylesheets().add(getClass().
                getResource("../../../css/downloadList.css").toExternalForm());
        setScene(scene);
        setResizable(false);
        show();
    }

    private void loadList() {
        DownloadController.downloadQueue.forEach(this::loadListRow);
    }

    private void loadListRow(Video video) {
        HBox hBox = new HBox(); // 下载列表的一行布局
        Label label = new Label(video.getVideoName() + " " + video.getpVideoName());

        hBox.getStyleClass().add("box_row");
        label.getStyleClass().add("row_labelText");

        HBox.setMargin(label, new Insets(0, 0, 0, 5));
        VBox.setMargin(hBox, new Insets(5, 5, 5, 5));

        hBox.getChildren().addAll(label);
        vBox.getChildren().add(hBox);
    }

}
