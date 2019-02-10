package cn.stronglink.collection.guis.iot.devices.guis.test.clientSend;
import java.nio.charset.Charset;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
public class NettyClientFilter extends ChannelInitializer<SocketChannel> {
	 @Override
	    protected void initChannel(SocketChannel ch) throws Exception {
	        ChannelPipeline ph = ch.pipeline();
	        /*
	         * 解码和编码，应和服务端一致
	         * */
	        ph.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
	        ph.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
	        ph.addLast("encoder", new StringEncoder(Charset.forName("GBK")));
	        ph.addLast("handler", new NettyClientHandler()); //客户端的逻辑
	    }
}
