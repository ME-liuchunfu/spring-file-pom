# spring-file-pom
集成本地文件存储服务、阿里云存储、七牛云存储、腾讯云存储、（其他OSS对象存储、FastDfs、HDFS、存储持续上线中）


# 使用例子
- 1、添加依赖
~~~xml
 <dependency>
      <groupId>xin.spring</groupId>
      <artifactId>disk-sdk</artifactId>
      <version>1.0-SNAPSHOT</version>
  </dependency>
~~~
- 2、构建配置
~~~java
public class LocalStorageTest {

    CloudStorageConfig config;
    StorageService storageService;

    @Before
    public void before() {
        // 存储配置
        config = new CloudStorageConfig();
        // 设置存储类型，此处为本地存储
        config.setType(StorageEnum.LOCAL.getType());
        // 设置存储目录
        config.setDirRoot("D:\\storage-disk");
        // 设置二级目录文件夹格式（目前仅支持时间格式）
        config.setFolderFormat("yyyy-MM-dd");
        // 此处设置本地服务的代理域名， 如nginx代理地址
        config.setLocalDomain("http://127.0.0.1");
        // 构建存储服务
        storageService = StorageFactory.build(config);
    }

    @Test
    public void upload() throws FileNotFoundException {
        // 上传文件
        // 参数1：需要上传的文件，参数2：上传到服务器的文件名称+格式
        String url = storageService.upload(new FileInputStream(new File("D:\\1.xlsx")),"1.xlsx");
        System.out.println(url);
    }

    @Test
    public void delete() {
        // 删除文件，传上传后的地址即可
        String url = "http://127.0.0.1/2021-09-03/1.xlsx";
        storageService.delete(url);
    }

    @Test
    public void download() {
        // 下载文件
        String url = "http://127.0.0.1/2021-09-03/1.xlsx";
        // 参数1：文件在服务器的url，参数2：服务器返回的文件输入流，是一个对象
        storageService.download(url, inputStream -> {
            File file = new File("xxxx.xlsx");
            try {
                FileUtils.copyInputStreamToFile(inputStream, file);
                System.out.println("下载完成：" + file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
~~~

# 鸣谢
项目离不开其他的开源项目
- 1、该项目借鉴人人开源项目构建的服务
