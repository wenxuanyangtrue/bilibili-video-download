package top.xsword.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DownloadPathStage extends Stage {

    private static final Properties conf = new Properties();
    private static final String confPath = "../../../conf.properties";

    static {
        try {
            conf.load(DownloadPathStage.class.getResourceAsStream(confPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void build() {
        getIcons().add(new Image(MainStage.class.getResourceAsStream("../../../img/icon.png")));
        setTitle("Download Path");
        initModality(Modality.APPLICATION_MODAL);//设置为模态窗口

        HBox hBox = new HBox();
        Label text = new Label("download path");
        TextField filePathField = new TextField();
        Button browseBtn = new Button("browse..");

        hBox.getChildren().addAll(text, filePathField, browseBtn);

        HBox.setMargin(text, new Insets(10, 0, 0, 5));
        HBox.setMargin(filePathField, new Insets(2, 0, 0, 15));
        HBox.setMargin(browseBtn, new Insets(7, 0, 0, 15));

        filePathField.setEditable(false);
        filePathField.setText(conf.getProperty("videoTarget"));
        filePathField.setMinSize(360, 30);
        hBox.setMaxSize(500, 30);

        browseBtn.setOnMouseClicked((mouseEvent) -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("select your directory");
            File file = directoryChooser.showDialog(this);
            if (file != null) {
                String absolutePath = file.getAbsolutePath() + '\\';
                filePathField.setText(absolutePath);
                conf.setProperty("videoTarget", absolutePath);
                String confPathStr = getClass().getResource(confPath).
                        toExternalForm().replace("file:/", "");
                try (FileOutputStream fos = new FileOutputStream(confPathStr)) {
                    conf.store(fos, "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });


        Scene scene = new Scene(hBox, 550, 35);
        scene.getStylesheets().add(getClass().
                getResource("../../../css/base.css").toExternalForm());

        browseBtn.getStyleClass().add("button_base");

        setScene(scene);
        setResizable(false);
        show();
    }

}
