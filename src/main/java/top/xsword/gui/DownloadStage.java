package top.xsword.gui;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import top.xsword.controller.DownloadController;
import top.xsword.entity.Video;
import top.xsword.service.GetVideoInfoService;

import java.util.List;

public class DownloadStage extends Stage {

    private final GetVideoInfoService videoInfoService; //视频信息
    private BorderPane borderPane; //主布局
    private Text videoTitle; //视频名
    private ScrollPane scrollPane; //滚轮窗格
    private GridPane downloadPane; //下载窗格
    private BorderPane bottomPane; //底部布局
    private Button downloadBtn; //下载按钮
    private ToggleButton allBtn; //全选按钮
    private Button downloadListBtn; //下载列表按钮

    public DownloadStage(GetVideoInfoService videoInfoService) {
        this.videoInfoService = videoInfoService;
    }

    public void build() {
        getIcons().add(new Image(MainStage.class.getResourceAsStream("../../../img/icon.png")));
        setTitle("download");
        initModality(Modality.APPLICATION_MODAL);//设置为模态窗口

        borderPane = new BorderPane();
        videoTitle = new Text(videoInfoService.getVideos().get(0).getVideoName());
        downloadPane = new GridPane();
        scrollPane = new ScrollPane();
        bottomPane = new BorderPane();
        downloadBtn = new Button("download");
        allBtn = new ToggleButton("all");
        downloadListBtn = new Button("download list");

        BorderPane.setAlignment(videoTitle, Pos.CENTER);
        BorderPane.setMargin(videoTitle, new Insets(5, 0, 5, 0));
        BorderPane.setMargin(scrollPane, new Insets(0, 0, 10, 0));

        videoTitle.setFont(Font.font("宋体", 15));
        downloadPane.setMinSize(380, 350);

        borderPane.setTop(videoTitle);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(bottomPane);
        scrollPane.setContent(downloadPane);

        loadDownloadPane();
        loadBottomPane();

        Scene scene = new Scene(borderPane, 400, 400);
        scene.getStylesheets().add(getClass().
                getResource("../../../css/downloadStage.css").toExternalForm());
        scene.getStylesheets().add(getClass().
                getResource("../../../css/base.css").toExternalForm());
        setScene(scene);
        setResizable(false);
        show();
    }

    public void loadDownloadPane() {
        List<Video> videos = videoInfoService.getVideos();
        for (int i = 0, row = 0, col = 0; i < videos.size(); i++) {
            ToggleButton toggleButton = new ToggleButton(String.valueOf(i + 1));
            toggleButton.setMinSize(80, 40);
            toggleButton.getStyleClass().add("toggle_button");
            if (i % 4 == 0) {
                row++;
                col = 0;
            } else {
                col++;
            }
            toggleButton.setOnMouseClicked((mouseEvent) -> {
                if (!toggleButton.isSelected()) {
                    allBtn.setSelected(false);
                }
            });
            downloadPane.add(toggleButton, col, row);
            GridPane.setMargin(toggleButton, new Insets(5, 5, 5, 9));
        }
    }

    //https://www.bilibili.com/video/BV1fh411y7R8
    public void loadBottomPane() {
        allBtn.getStyleClass().addAll("button_base", "all_button");
        downloadBtn.getStyleClass().add("button_base");
        downloadListBtn.getStyleClass().add("button_base");

        allBtn.setMinSize(40, 20);
        downloadBtn.setMinSize(80, 20);

        allBtn.setOnMouseClicked((mouseEvent) -> downloadPane.getChildrenUnmodifiable().forEach((btn) -> {
            ToggleButton toggleButton = (ToggleButton) btn;
            toggleButton.setSelected(allBtn.isSelected());
        }));

        downloadBtn.setOnMouseClicked((mouseEvent) -> {
            downloadPane.getChildrenUnmodifiable().forEach((btn) -> {
                ToggleButton toggleButton = (ToggleButton) btn;
                List<Video> videos = videoInfoService.getVideos();
                if (toggleButton.isSelected()) {
                    DownloadController.downloadQueue.add(videos.get(Integer.parseInt(toggleButton.getText()) - 1));
                }
            });
            Thread thread = new Thread(new Task<>() {
                @Override
                protected Object call() {
                    new DownloadController().start();
                    return null;
                }
            });
            thread.setDaemon(true);
            thread.start();
        });

        downloadListBtn.setOnMouseClicked((mouseEvent) -> new DownloadList().build());

        BorderPane.setMargin(allBtn, new Insets(0, 0, 10, 10));
        BorderPane.setMargin(downloadBtn, new Insets(0, 0, 10, 25));
        BorderPane.setMargin(downloadListBtn, new Insets(0, 10, 10, 0));

        bottomPane.setLeft(allBtn);
        bottomPane.setCenter(downloadBtn);
        bottomPane.setRight(downloadListBtn);
    }
}
