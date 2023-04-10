package top.xsword.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import top.xsword.utils.Function;

public class ModalStage extends Stage {
    public void build(String message, Function yesFunc,Function NoFunc) {
        setTitle("warning");
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 100, 200);
        Text text = new Text(message);
        Button yes = new Button("是");
        Button no = new Button("否");
        yes.setOnMouseClicked(event -> yesFunc.invoke());
        no.setOnMouseClicked(event -> NoFunc.invoke());

        gridPane.addRow(0, text);
        gridPane.addRow(1, yes, no);
        setScene(scene);
        show();
    }
}
