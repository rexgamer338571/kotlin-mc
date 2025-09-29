package dev.ng5m.server

import dev.ng5m.MinecraftConnection
import dev.ng5m.MinecraftServer
import dev.ng5m.NettyConnection
import dev.ng5m.mcio.MCDecoder
import dev.ng5m.mcio.MCEncoder
import dev.ng5m.mcio.MCHandler
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.MultiThreadIoEventLoopGroup
import io.netty.channel.nio.NioIoHandler
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

class NettyServer : TCPServer<Channel>(::NettyConnection) {
    private lateinit var shutdown: () -> Unit

    override fun start(port: Int) {
        val factory = NioIoHandler.newFactory()

        val bossGroup = MultiThreadIoEventLoopGroup(factory)
        val workerGroup = MultiThreadIoEventLoopGroup(factory)

        shutdown = {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }

        try {
            val bootstrap = ServerBootstrap()
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel?) {
                        ch?.let {
                            ch.pipeline().addLast(
                                MCDecoder(),
                                MCEncoder(),
                                MCHandler()
                            )
                        }
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)

            val future: ChannelFuture = bootstrap.bind(port).sync()
            future.channel().closeFuture().sync()
        } catch (x: Exception) {
            throw RuntimeException(x)
        } finally {
            stop()
        }
    }

    override fun stop() {
        shutdown()
    }

    override fun getOrRegisterConnection(key: Channel): MinecraftConnection {
        val connection = super.getOrRegisterConnection(key)
        MinecraftServer.getInstance().registerConnection(connection)
        return connection
    }

}