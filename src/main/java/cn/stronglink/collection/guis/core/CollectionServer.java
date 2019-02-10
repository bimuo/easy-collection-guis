package cn.stronglink.collection.guis.core;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.stronglink.collection.guis.core.message.IMessageHandleFactory;
import cn.stronglink.collection.guis.core.server.TimeoutHandler;
import cn.stronglink.collection.guis.core.util.ContextUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

public class CollectionServer  implements ApplicationListener<ContextRefreshedEvent> {
	public static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	private static Logger logger = LogManager.getLogger(CollectionServer.class.getName());

	/**
	 * 鏈嶅姟鍣ㄥ悕绉?
	 */
	private String serverName;
	/**
	 * 鏈嶅姟鍣ㄧ鍙ｅ彿
	 */
	private int serverPort;
	/**
	 * 鏈嶅姟鍣╯ocket璇昏秴鏃剁殑绉掓暟
	 */
	private long readTimeout=600;
	/**
	 * 鏈嶅姟鍣╯ocket鍐欒秴鏃剁殑绉掓暟
	 */
	private long writeTimeout=Long.MAX_VALUE;
	/**
	 * 鏈嶅姟鍣ㄥ鐞嗗伐鍘?
	 */
	private IMessageHandleFactory messageHandleFactory;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ContextUtils.setApplicationContext(event.getApplicationContext());
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					CollectionServer.this.bind(
							CollectionServer.this.getMessageHandleFactory(),
							CollectionServer.this.getServerName(),
							CollectionServer.this.getServerPort(),
							CollectionServer.this.getWriteTimeout(),
							CollectionServer.this.getReadTimeout()
							);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		}).start();
	}
	
	private void bind(IMessageHandleFactory factory,String serverName,int port,long writeTimetou,long readTime) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup);
			serverBootstrap.channel(NioServerSocketChannel.class);
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
			serverBootstrap.handler(new LoggingHandler());
			serverBootstrap.childHandler(new NettyChannelHandler(factory,writeTimetou,readTime));
			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
			logger.info("======="+serverName+"[port:"+port+"] 服务已开启=======");
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	// 娑堟伅澶勭悊绫?
	private class NettyChannelHandler extends ChannelInitializer<SocketChannel> {
		IMessageHandleFactory factory;
		long writeTimeout,readTimeout;
		public NettyChannelHandler(IMessageHandleFactory factory,long writeTimeout,long readTimeout){
			super();
			this.factory=factory;
			this.readTimeout=readTimeout;
			this.writeTimeout=writeTimeout;
		}
		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception {
			// 璁剧疆瓒呮椂
			socketChannel.pipeline().addLast(
					new IdleStateHandler(
							this.readTimeout,
							this.writeTimeout, 
							Math.max(this.readTimeout, this.writeTimeout), 
							TimeUnit.SECONDS)
					); 
			// 瓒呮椂澶勭悊
			socketChannel.pipeline().addLast(new TimeoutHandler());
			// 瑙ｇ爜澶勭悊
			socketChannel.pipeline().addLast(factory.createMessageDecoder());
			// 涓氬姟澶勭悊
			socketChannel.pipeline().addLast(factory.createMessageHandle());
		}
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public IMessageHandleFactory getMessageHandleFactory() {
		return messageHandleFactory;
	}

	public void setMessageHandleFactory(IMessageHandleFactory messageHandleFactory) {
		this.messageHandleFactory = messageHandleFactory;
	}

	public long getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(long readTimeout) {
		this.readTimeout = readTimeout;
	}

	public long getWriteTimeout() {
		return writeTimeout;
	}

	public void setWriteTimeout(long writeTimeout) {
		this.writeTimeout = writeTimeout;
	}
}
