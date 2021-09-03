package xin.spring.file.disk.sdk.storage;

import java.io.InputStream;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.storage
 * @ClassName DownLoadHandler.java
 * @descript
 * @date 2021/9/3 14:14
 */
@FunctionalInterface
public interface DownLoadHandler {

    void press(InputStream inputStream);

}
