package xin.spring.file.disk.sdk.storage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xin.spring.file.common.exception.StorageException;
import xin.spring.file.common.util.DateUtils;
import xin.spring.file.common.util.FileUtils;
import xin.spring.file.common.util.IOUtils;
import xin.spring.file.common.util.StringUtils;
import xin.spring.file.disk.sdk.conf.CloudStorageConfig;
import xin.spring.file.disk.sdk.storage.DownLoadHandler;
import xin.spring.file.disk.sdk.storage.StorageService;

import java.io.*;
import java.nio.file.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.storage.impl
 * @ClassName LocalStorageService.java
 * @descript 本地存储
 * @date 2021/9/3 9:54
 */
public class LocalStorageService extends StorageService {

    private static final Logger logger = LoggerFactory.getLogger(LocalStorageService.class);

    public LocalStorageService(CloudStorageConfig config) {
        super(config);
    }

    @Override
    protected void init() {
        String dirRoot = config.getDirRoot();
        String node = config.getNode();
        File root = null;
        if (StringUtils.isNotBlank(node)) {
            root = new File(dirRoot, node);
        } else {
            root = new File(dirRoot);
        }
        if (!root.exists()) {
            root.mkdirs();
        }
    }

    public String getFileDir() {
        String dirRoot = config.getDirRoot();
        String node = config.getNode();
        if (StringUtils.isNotBlank(node)) {
            return dirRoot + File.separator + node;
        } else {
            return dirRoot;
        }
    }

    public File getAbsFileDir() {
        String dirRoot = config.getDirRoot();
        String node = config.getNode();
        if (StringUtils.isNotBlank(node)) {
            return new File(dirRoot, node);
        } else {
            return new File(dirRoot);
        }
    }

    public synchronized File createFileDir() {
        File fileDir = getAbsFileDir();
        String folderFormat = config.getFolderFormat();
        Date date = new Date();
        String format = DateUtils.format(date, folderFormat);
        File file = new File(fileDir, format);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private String getUploadUrl(File file) {
        String absolutePath = file.getAbsolutePath();
        String dirRoot = config.getDirRoot();
        absolutePath = absolutePath.replace(dirRoot, "");
        absolutePath = absolutePath.replaceAll("\\\\+", "/");
        String localDomain = config.getLocalDomain();
        String url = absolutePath;
        if (StringUtils.isNotBlank(localDomain)) {
            if (!absolutePath.startsWith("/")) {
                absolutePath = "/" + absolutePath;
            }
            url = localDomain + absolutePath;
        }
        return url;
    }

    @Override
    public String upload(byte[] data, String path) {
        Objects.requireNonNull(path);
        File fileDir = createFileDir();
        File file = new File(fileDir, path);
        Path nioPath = Paths.get(file.getAbsolutePath());
        try {
            Files.write(nioPath, data, StandardOpenOption.WRITE);
        } catch (IOException e) {
            logger.error("文件写入异常", e);
            throw new StorageException("文件写入异常", e);
        }
        String url = getUploadUrl(file);
        return url;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        Objects.requireNonNull(suffix);
        return upload(data, suffix);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        Objects.requireNonNull(path);
        File fileDir = createFileDir();
        File file = new File(fileDir, path);
        try {
            Path nioPath = Paths.get(file.getAbsolutePath());
            Files.copy(inputStream, nioPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("文件写入异常", e);
            throw new StorageException("文件写入异常", e);
        }
        String url = getUploadUrl(file);
        return url;
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        Objects.requireNonNull(suffix);
        return upload(inputStream, suffix);
    }

    @Override
    public void delete(String path) {
        File file = subPathFile(path);
        FileUtils.deleteQuietly(file);
    }

    public File subPathFile(String path) {
        String fileDir = getFileDir();
        String localDomain = config.getLocalDomain();
        path = path.replace(localDomain, "");
        File file = new File(fileDir, path);
        return file;
    }

    @Override
    public void download(String path, DownLoadHandler downLoadHandler) {
        File file = subPathFile(path);
        if (!file.exists()) {
            logger.error("文件不存在或已删除");
            throw new StorageException("文件不存在或已删除");
        }
        DownLoadHandler handler = (ins)-> {
            if (downLoadHandler != null) {
                downLoadHandler.press(ins);
            } else {
                logger.error("downLoadHandler下载监听器为空，不能提供下载服务");
            }
        };
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            handler.press(inputStream);
            logger.info("文件${}下载成功", path);
        } catch (FileNotFoundException e) {
            logger.error("文件不存在或已删除");
            throw new StorageException("文件不存在或已删除", e);
        } finally {
            IOUtils.closeQuietly(inputStream, null);
        }
    }
}
