package com.bin.test.server;

/**
 * Created by zhangbin on 16/11/3.
 */


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by zhangbin on 16/11/2.
 */
public final class NettyServer {
    public  static int PORT = 9000;
    public  final int HEART_SYNC_TIME = 300;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private final int HEART_PING_TIME = 180;


    public  void bind(int port) throws InterruptedException{
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap=new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    /*socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));//设置定长解码器 长度设置为30
                    socketChannel.pipeline().addLast(new StringDecoder());//设置字符串解码器 自动将报文*/

                   // socketChannel.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
                    //socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                    socketChannel.pipeline().addLast(new StringDecoder());
                    socketChannel.pipeline().addLast(new StringEncoder());
                    socketChannel.pipeline().addLast(new MessageServerHandler());
                   // socketChannel.pipeline().addLast(new IdleStateHandler(0, 0, HEART_PING_TIME));
                    //pipe.addLast( new ProtobufVarint32FrameDecoder());
                    //pipe.addLast(new ProtobufDecoder(MessageProto.Message .getDefaultInstance()));
                    //pipe.addLast(new ProtobufVarint32LengthFieldPrepender());
                    //pipe.addLast(new ProtobufEncoder());
                }
            });
            // Bind and start to accept incoming connections.
            ChannelFuture f  =  bootstrap.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync().await();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public synchronized void stop(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String []args)  {
        NettyServer server = null;
        try {
            server = new NettyServer();



            server.bind(PORT);
        } catch (InterruptedException e) {
           // MessageManager.getInstance().stop();
            //UserManager.getInstance().clearAll();
            server.stop();
            e.printStackTrace();
        }
    }
}