package xin.spring.file.disk.sdk.storage.impl;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xin.spring.file.common.exception.StorageException;
import xin.spring.file.disk.sdk.conf.CloudStorageConfig;
import xin.spring.file.disk.sdk.storage.StorageService;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.storage.impl
 * @ClassName QiniuCloudStorageService.java
 * @descript 七牛云存储
 * @date 2021/9/3 9:34
 */
public class QiniuCloudStorageService extends StorageService {

    private static final Logger logger = LoggerFactory.getLogger(QiniuCloudStorageService.class);

    private UploadManager uploadManager;
    private String token;

    public QiniuCloudStorageService(CloudStorageConfig config){
        super(config);

    }

    protected void init(){
        uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
        token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey()).
                uploadToken(config.getQiniuBucketName());
    }

    @Override
    public String upload(byte[] data, String path) {
        try {
            Response res = uploadManager.put(data, path, token);
            if (!res.isOK()) {
                logger.error("上传七牛出错：" + res.toString());
                throw new StorageException("上传七牛出错：" + res.toString());
            }
        } catch (Exception e) {
            logger.error("上传文件失败，请核对七牛配置信息", e);
            throw new StorageException("上传文件失败，请核对七牛配置信息", e);
        }
        String url = config.getQiniuDomain() + "/" + path;
        logger.info("文件上传成功${}", url);
        return url;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            logger.error("上传文件失败", e);
            throw new StorageException("上传文件失败", e);
        }
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getQiniuPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getQiniuPrefix(), suffix));
    }

}
