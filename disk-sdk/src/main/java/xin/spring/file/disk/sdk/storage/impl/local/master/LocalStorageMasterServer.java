package xin.spring.file.disk.sdk.storage.impl.local.master;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.storage.impl.local.master
 * @ClassName LocalStorageMasterServer.java
 * @descript
 * @date 2021/9/18 16:00
 */
public class LocalStorageMasterServer {

    public void bind(int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            System.out.println("客户端链接上来了：" + ch.localAddress().toString());
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline()
                                    .addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                            ClassResolvers.weakCachingConcurrentResolver(null))); // 最大长度
                            ch.pipeline().addLast(new LocalStorageSyncServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("集群同步端口已经启动. port = " + port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
