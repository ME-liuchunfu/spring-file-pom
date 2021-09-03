package xin.spring.file.disk.sdk.factory;

import xin.spring.file.common.enums.StorageEnum;
import xin.spring.file.common.exception.StorageException;
import xin.spring.file.disk.sdk.conf.CloudStorageConfig;
import xin.spring.file.disk.sdk.storage.StorageService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.factory
 * @ClassName StorageFactory.java
 * @descript
 * @date 2021/9/3 9:45
 */
public class StorageFactory {

    public static StorageService build(CloudStorageConfig config, StorageEnum...enums) {
        Objects.requireNonNull(config);
        StorageEnum[] confEnums = StorageEnum.values();
        StorageEnum[] allEnums = null;
        if (enums != null && enums.length > 0) {
            allEnums = new StorageEnum[confEnums.length + enums.length];
            int cur = 0;
            for (StorageEnum item : confEnums) {
                allEnums[cur++] = item;
            }
            for (StorageEnum item : enums) {
                allEnums[cur++] = item;
            }
        } else {
            allEnums = confEnums;
        }
        StorageService storageService = null;
        for (StorageEnum item : allEnums) {
            if (item.getType() == config.getType()) {
                try {
                    Class<?> classType = Class.forName(item.getClassType());
                    Constructor<?> constructor = classType.getConstructor(config.getClass());
                    storageService = (StorageService) constructor.newInstance(config);
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    throw new StorageException("存储实现类未找到", e);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    throw new StorageException("存储实现类构造失败", e);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    throw new StorageException("存储实现类构造失败", e);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new StorageException("存储实现类构造失败", e);
                }
            }
        }
        return storageService;
    }

}
