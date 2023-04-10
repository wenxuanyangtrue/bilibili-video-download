package top.xsword.utils;
/*
 *ClassName:FileUtil
 *UserName:三号男嘉宾
 *CreateTime:2021-10-26
 *description:
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    /**
     * 创建目录
     *
     * @param paths 需要创建的目录
     */
    public static void mkdirs(String... paths) {
        for (String path : paths) {
            try {
                Files.createDirectories(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
