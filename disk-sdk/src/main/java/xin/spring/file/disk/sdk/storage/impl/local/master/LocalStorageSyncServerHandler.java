package xin.spring.file.disk.sdk.storage.impl.local.master;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author spring
 * @Package xin.spring.file.disk.sdk.storage.impl.local.master
 * @ClassName LocalStorageSyncServerHandler.java
 * @descript
 * @date 2021/9/18 16:00
 */
public class LocalStorageSyncServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("服务端1：channelActive()");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("服务端1：channelInactive()");
        ctx.flush();
        ctx.close();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(JSON.toJSONString(msg));
        ctx.writeAndFlush(msg);
        if (((String)msg).contains("1")) {
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        System.out.println("FileUploadServerHandler--exceptionCaught()");
    }
}

