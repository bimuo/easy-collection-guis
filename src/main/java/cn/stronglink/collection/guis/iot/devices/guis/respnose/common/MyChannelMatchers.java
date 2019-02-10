package cn.stronglink.collection.guis.iot.devices.guis.respnose.common;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;

public class MyChannelMatchers implements ChannelMatcher {
	private String currentIP;
	public MyChannelMatchers(String ip)
	{
		this.currentIP=ip;
	}
	
	@Override
	public boolean matches(Channel channel) {
		InetSocketAddress insocket = (InetSocketAddress)channel.remoteAddress();
		if(insocket.getAddress().getHostAddress().equals(this.currentIP)){
			return true;
		}else{
			return false;
		}
	}
}
