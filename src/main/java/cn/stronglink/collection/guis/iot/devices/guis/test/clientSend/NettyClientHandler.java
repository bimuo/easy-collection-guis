package cn.stronglink.collection.guis.iot.devices.guis.test.clientSend;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
* Title: NettyClientHandler
* Description: 客户端业务逻辑实现
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
	private static SimpleDateFormat df=null;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {  
    	 df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	    String strGBK = URLEncoder.encode(msg, "UTF-8");    
        System.out.println(df.format(new Date())+ "    客户端接受到消息: " + stringToHexString(strGBK));
    }

    //
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("正在连接... ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭! ");
        super.channelInactive(ctx);
    }
    
    public static String stringToHexString(String s) {  
        String str = "";  
        for (int i = 0; i < s.length(); i++) {  
            int ch = (int) s.charAt(i);  
            String s4 = Integer.toHexString(ch);  
            str = str + s4;  
        }  
        return str;  
    }  
}