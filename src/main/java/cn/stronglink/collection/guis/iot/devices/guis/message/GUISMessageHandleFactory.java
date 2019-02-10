package cn.stronglink.collection.guis.iot.devices.guis.message;

import cn.stronglink.collection.guis.core.message.IMessageHandleFactory;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.coder.GUISDecoder;
import cn.stronglink.collection.guis.iot.devices.guis.respnose.handle.GUISResponseHandleContext;
import io.netty.channel.ChannelHandler;

public class GUISMessageHandleFactory implements IMessageHandleFactory {

	@Override
	public ChannelHandler createMessageDecoder() {
		return new GUISDecoder(Integer.MAX_VALUE, 6, 2, 2, 0, true);
	}

	@Override
	public ChannelHandler createMessageHandle() {
		return new GUISResponseHandleContext();
	}

}
