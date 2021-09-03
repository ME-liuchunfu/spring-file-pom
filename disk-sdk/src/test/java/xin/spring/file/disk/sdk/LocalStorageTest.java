package xin.spring.file.disk.sdk;

import org.junit.Before;
import org.junit.Test;
import xin.spring.file.common.enums.StorageEnum;
import xin.spring.file.common.util.FileUtils;
import xin.spring.file.disk.sdk.conf.CloudStorageConfig;
import xin.spring.file.disk.sdk.factory.StorageFactory;
import xin.spring.file.disk.sdk.storage.StorageService;

import java.io.*;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk
 * @ClassName LocalStorageTest.java
 * @descript
 * @date 2021/9/3 14:22
 */
public class LocalStorageTest {

    CloudStorageConfig config;
    StorageService storageService;

    @Before
    public void before() {
        config = new CloudStorageConfig();
        config.setType(StorageEnum.LOCAL.getType());
        config.setDirRoot("D:\\storage-disk");
        config.setFolderFormat("yyyy-MM-dd");
        config.setLocalDomain("http://10.10.6.85");
        storageService = StorageFactory.build(config);
    }

    @Test
    public void upload() throws FileNotFoundException {
        String url = storageService.upload(new FileInputStream(new File("D:\\工作文件\\slot材料\\#21 Chinese Lantern中国灯笼\\#21_校验指标.xlsx")), "1#21_校验指标.xlsx");
        System.out.println(url);
    }

    @Test
    public void delete() {
        String url = "http://10.10.6.85/2021-09-03/1#21_校验指标.xlsx";
        storageService.delete(url);
    }

    @Test
    public void download() {
        String url = "http://10.10.6.85/2021-09-03/1#21_校验指标.xlsx";
        storageService.download(url, inputStream -> {
            File file = new File("xxxx.xlsx");
            try {
                FileUtils.copyInputStreamToFile(inputStream, file);
                System.out.println("下载完成：" + file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        System.out.println(1);
    }
}
