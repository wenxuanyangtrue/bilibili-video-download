package top.xsword.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/*
 *ClassName:CookieStage
 *CreateTime:2022-01-23
 *author:三号男嘉宾
 *description:
 */
public class CookieStage extends Stage {
    private static final Properties REQUEST_HEADER = new Properties();
    private static final String conf = "../../../request-header.properties";

    static {
        try {
            REQUEST_HEADER.load(CookieStage.class.getResourceAsStream(conf));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CookieStage() {

    }

    public void build() {
        getIcons().add(new Image(MainStage.class.getResourceAsStream("../../../img/icon.png")));
        setTitle("Cookie");
        initModality(Modality.APPLICATION_MODAL);//设置为模态窗口
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        TextArea textArea = new TextArea();
        textArea.setPrefSize(400, 200);
        textArea.setWrapText(true);
        Button submitBtn = new Button("submit");
        Button resetBtn = new Button("reset");

        textArea.setEditable(false);
        submitBtn.setDisable(true);
        textArea.setText(REQUEST_HEADER.getProperty("cookie"));

        gridPane.addRow(0, textArea);
        gridPane.addRow(1, submitBtn, resetBtn);
        GridPane.setColumnSpan(textArea, 2);
        GridPane.setHalignment(resetBtn, HPos.RIGHT);

        submitBtn.setOnMouseClicked((mouseEvent) -> {
            REQUEST_HEADER.setProperty("cookie", textArea.getText());
            textArea.setEditable(false);
            submitBtn.setDisable(true);
        });
        resetBtn.setOnMouseClicked((mouseEvent) -> {
            textArea.clear();
            textArea.setEditable(true);
            submitBtn.setDisable(false);
        });
        setOnCloseRequest((windowEvent) -> {
            String confPath = getClass().getResource(conf).toExternalForm().replace("file:/", "");
            try (FileOutputStream fos = new FileOutputStream(confPath)) {
                REQUEST_HEADER.store(fos, "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        Scene scene = new Scene(gridPane, 500, 300);
        scene.getStylesheets().add(getClass().
                getResource("../../../css/base.css").toExternalForm());

        submitBtn.getStyleClass().addAll("button_base");
        resetBtn.getStyleClass().addAll("button_base");

        setScene(scene);
        setResizable(false);
        show();
    }
}
