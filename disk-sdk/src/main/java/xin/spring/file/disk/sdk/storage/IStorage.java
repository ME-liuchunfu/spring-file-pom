package xin.spring.file.disk.sdk.storage;

import java.io.InputStream;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.storage
 * @ClassName IStorage.java
 * @descript
 * @date 2021/9/3 14:11
 */
public interface IStorage {

    /**
     * 文件上传
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名
     * @return        返回http地址
     */
    public String upload(byte[] data, String path);

    /**
     * 文件上传
     * @param data     文件字节数组
     * @param suffix   后缀
     * @return         返回http地址
     */
    public String uploadSuffix(byte[] data, String suffix);

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    public String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     * @param inputStream  字节流
     * @param suffix       后缀
     * @return             返回http地址
     */
    public String uploadSuffix(InputStream inputStream, String suffix);

    /**
     * 删除
     * @param path
     */
    public void delete(String path);

    /**
     * 下载
     * @param path
     * @param downLoadHandler
     */
    public void download(String path, DownLoadHandler downLoadHandler);

}
