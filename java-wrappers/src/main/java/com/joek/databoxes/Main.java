package com.joek.databoxes;

import io.grpc.netty.shaded.io.netty.bootstrap.Bootstrap;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.channel.*;
import io.grpc.netty.shaded.io.netty.channel.epoll.EpollDomainSocketChannel;
import io.grpc.netty.shaded.io.netty.channel.epoll.EpollEventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.unix.DomainSocketAddress;

import static com.joek.databoxes.DataBox.Box;

public class Main {
    private static final String AUTHORIZER_SOCK = "/tmp/authorizer.sock";

    public static void main(String[] args) {
        Box<Integer> foo = new Box<>(10);
        Box<Integer> someShitIDoWithData = foo + 10;
        System.out.println(someShitIDoWithData.getInner());

        // WIP, trying out connecting to local socket, which will eventually be the service that stores the auth logic

        EventLoopGroup group = new EpollEventLoopGroup();
        try {
            DomainSocketAddress socketAddress = new DomainSocketAddress(AUTHORIZER_SOCK);
            Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap.group(group)
                    .channel(EpollDomainSocketChannel.class)
                    .remoteAddress(socketAddress)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(final Channel channel) throws Exception {
                            channel.pipeline().addLast(
                                    new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
                                            final ByteBuf buff = (ByteBuf) msg;
                                            try {
                                                byte[] bytes = new byte[buff.readableBytes()];
                                                buff.getBytes(0, bytes);
                                                System.out.println(new String(bytes));
                                            } finally {
                                                buff.release();
                                            }
                                            ctx.close();
                                        }

                                        @Override
                                        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable e) throws Exception {
                                            System.out.println("Error occurred when reading from Unix domain socket: " + e.getMessage());
                                            ctx.close();
                                        }
                                    }
                            );
                        }
                    });

            ChannelFuture channelFuture = clientBootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }
}

