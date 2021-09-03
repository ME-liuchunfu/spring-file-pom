package xin.spring.file.disk.sdk.storage;

import xin.spring.file.common.exception.StorageException;
import xin.spring.file.common.util.DateUtils;
import xin.spring.file.common.util.StringUtils;
import xin.spring.file.disk.sdk.conf.CloudStorageConfig;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.storage
 * @ClassName StorageService.java
 * @descript
 * @date 2021/9/2 18:35
 */
public abstract class StorageService implements IStorage {

    /** 云存储配置信息 */
    protected CloudStorageConfig config;

    public StorageService(CloudStorageConfig config) {
        this.config = config;

        init();
    }

    protected abstract void init();

    /**
     * 文件路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = DateUtils.format(new Date(), "yyyyMMdd") + "/" + uuid;

        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }

        return path + suffix;
    }

    /**
     * 文件上传
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名
     * @return        返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     * @param data     文件字节数组
     * @param suffix   后缀
     * @return         返回http地址
     */
    public abstract String uploadSuffix(byte[] data, String suffix);

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     * @param inputStream  字节流
     * @param suffix       后缀
     * @return             返回http地址
     */
    public abstract String uploadSuffix(InputStream inputStream, String suffix);

    @Override
    public void delete(String path) {
        throw new StorageException(getClass() + "未实现删除方法");
    }

    @Override
    public void download(String path, DownLoadHandler downLoadHandler) {
        throw new StorageException(getClass() + "未实现下载方法");
    }

}
