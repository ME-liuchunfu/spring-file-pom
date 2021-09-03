package xin.spring.file.disk.sdk.storage.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import com.qiniu.util.IOUtils;
import net.sf.json.JSONObject;
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
 * @ClassName QcloudCloudStorageService.java
 * @descript 腾讯云存储
 * @date 2021/9/3 9:33
 */
public class QcloudCloudStorageService extends StorageService {

    private static final Logger logger = LoggerFactory.getLogger(QcloudCloudStorageService.class);

    private COSClient client;

    public QcloudCloudStorageService(CloudStorageConfig config){
        super(config);

    }

    protected void init(){
        Credentials credentials = new Credentials(config.getQcloudAppId(), config.getQcloudSecretId(),
                config.getQcloudSecretKey());

        //初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        clientConfig.setRegion(config.getQcloudRegion());

        client = new COSClient(clientConfig, credentials);
    }

    @Override
    public String upload(byte[] data, String path) {
        //腾讯云必需要以"/"开头
        if(!path.startsWith("/")) {
            path = "/" + path;
        }

        //上传到腾讯云
        UploadFileRequest request = new UploadFileRequest(config.getQcloudBucketName(), path, data);
        String response = client.uploadFile(request);

        JSONObject jsonObject = JSONObject.fromObject(response);
        if(jsonObject.getInt("code") != 0) {
            logger.error("文件上传失败，" + jsonObject.getString("message"));
            throw new StorageException("文件上传失败，" + jsonObject.getString("message"));
        }
        String url = config.getQcloudDomain() + path;
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
        return upload(data, getPath(config.getQcloudPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getQcloudPrefix(), suffix));
    }
}
