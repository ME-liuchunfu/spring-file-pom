package xin.spring.file.disk.sdk.storage.impl;

import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xin.spring.file.common.exception.StorageException;
import xin.spring.file.disk.sdk.conf.CloudStorageConfig;
import xin.spring.file.disk.sdk.storage.StorageService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.storage.impl
 * @ClassName AliyunCloudStorageService.java
 * @descript 阿里云存储
 * @date 2021/9/2 18:41
 */
public class AliyunCloudStorageService extends StorageService {

    private static final Logger logger = LoggerFactory.getLogger(AliyunCloudStorageService.class);

    private OSSClient client;

    public AliyunCloudStorageService(CloudStorageConfig config){
        super(config);

    }

    protected void init(){
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
        } catch (Exception e){
            logger.error("上传文件失败，请检查配置信息", e);
            throw new StorageException("上传文件失败，请检查配置信息", e);
        }
        String url = config.getAliyunDomain() + "/" + path;
        logger.info("上传成功路径${}", url);
        return url;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }
}
