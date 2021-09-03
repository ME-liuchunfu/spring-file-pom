package xin.spring.file.disk.sdk.conf;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import xin.spring.file.disk.sdk.validator.group.AliyunGroup;
import xin.spring.file.disk.sdk.validator.group.LocalGroup;
import xin.spring.file.disk.sdk.validator.group.QcloudGroup;
import xin.spring.file.disk.sdk.validator.group.QiniuGroup;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.conf
 * @ClassName CloudStorageConfig.java
 * @descript
 * @date 2021/9/2 17:18
 */
@Data
public class CloudStorageConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型 1：七牛  2：阿里云  3：腾讯云 0：本地存储
     */
    @Range(min=1, max=3, message = "类型错误")
    private int type = 0;

    @NotBlank(message="七牛绑定的域名不能为空", groups = QiniuGroup.class)
    @URL(message = "七牛绑定的域名格式不正确", groups = QiniuGroup.class)
    private String qiniuDomain;
    private String qiniuPrefix;
    @NotBlank(message="七牛AccessKey不能为空", groups = QiniuGroup.class)
    private String qiniuAccessKey;
    @NotBlank(message="七牛SecretKey不能为空", groups = QiniuGroup.class)
    private String qiniuSecretKey;
    @NotBlank(message="七牛空间名不能为空", groups = QiniuGroup.class)
    private String qiniuBucketName;

    @NotBlank(message="阿里云绑定的域名不能为空", groups = AliyunGroup.class)
    @URL(message = "阿里云绑定的域名格式不正确", groups = AliyunGroup.class)
    private String aliyunDomain;
    private String aliyunPrefix;
    @NotBlank(message="阿里云EndPoint不能为空", groups = AliyunGroup.class)
    private String aliyunEndPoint;
    @NotBlank(message="阿里云AccessKeyId不能为空", groups = AliyunGroup.class)
    private String aliyunAccessKeyId;
    @NotBlank(message="阿里云AccessKeySecret不能为空", groups = AliyunGroup.class)
    private String aliyunAccessKeySecret;
    @NotBlank(message="阿里云BucketName不能为空", groups = AliyunGroup.class)
    private String aliyunBucketName;

    @NotBlank(message="腾讯云绑定的域名不能为空", groups = QcloudGroup.class)
    @URL(message = "腾讯云绑定的域名格式不正确", groups = QcloudGroup.class)
    private String qcloudDomain;
    private String qcloudPrefix;
    @NotNull(message="腾讯云AppId不能为空", groups = QcloudGroup.class)
    private Integer qcloudAppId;
    @NotBlank(message="腾讯云SecretId不能为空", groups = QcloudGroup.class)
    private String qcloudSecretId;
    @NotBlank(message="腾讯云SecretKey不能为空", groups = QcloudGroup.class)
    private String qcloudSecretKey;
    @NotBlank(message="腾讯云BucketName不能为空", groups = QcloudGroup.class)
    private String qcloudBucketName;
    @NotBlank(message="所属地区不能为空", groups = QcloudGroup.class)
    private String qcloudRegion;

    /**
     * 本地存儲路劲
     */
    @NotBlank(message="本地根目录不能为空", groups = LocalGroup.class)
    private String dirRoot;

    /**
     * 节点 默认为 dirRoot + folderFormat
     *  如果节点不位空： dirRoot + node + folderFormat
     */
    private String node;

    /**
     * 文件夾格式化： 如： YYYY-MM-dd
     */
    @NotBlank(message="本地分类目录不能为空", groups = LocalGroup.class)
    private String folderFormat;

    /**
     * 配置本地存储需要配置nginx域名， 不配置默认返回文件相对路径
     */
    private String localDomain;

}
