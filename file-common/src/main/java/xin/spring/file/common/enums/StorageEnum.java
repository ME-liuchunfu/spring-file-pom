package xin.spring.file.common.enums;

/**
 * @author spring
 * @Package xin.spring.file.common.enums
 * @ClassName StorageEnum.java
 * @descript 存储类型枚举
 * @date 2021/9/3 9:39
 */
public enum StorageEnum {

    LOCAL(0, "本地存储", "xin.spring.file.disk.sdk.storage.impl.LocalStorageService"),
    QINIU_CLOUD(1, "七牛云存储", "xin.spring.file.disk.sdk.storage.impl.QiniuCloudStorageService"),
    ALI_CLOUD(2, "阿里云存储", "xin.spring.file.disk.sdk.storage.impl.AliyunCloudStorageService"),
    Q_CLOUD(3, "腾讯云存储", "xin.spring.file.disk.sdk.storage.impl.QcloudCloudStorageService");

    private int type;

    private String name;

    private String classType;

    StorageEnum(int type, String name, String classType) {
        this.type = type;
        this.name = name;
        this.classType = classType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

}
