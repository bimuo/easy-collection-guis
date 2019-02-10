package cn.stronglink.collection.guis.iot.devices.guis.test.clientSend;


import java.io.IOException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.stronglink.collection.guis.core.util.ByteUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
/**
 * 模拟客户端（老唐的硬件）向采集发送消息
 * @author zhoujun
 *
 */
public class NettyClient {
	
	private String name;

	private static Logger logger = LogManager.getLogger(NettyClient.class.getName());
	
    private String ip;

    private int port;
    
    /// 通过nio方式来接收连接和处理连接   
    private static EventLoopGroup group = new NioEventLoopGroup(); 
    private static  Bootstrap b = new Bootstrap();
    private static Channel ch;

//    private boolean stop = false;

    public NettyClient(String ip, int port,String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;
    }

    public void run() throws IOException, InterruptedException {
    	 b.group(group);
         b.channel(NioSocketChannel.class);
         b.handler(new NettyClientFilter());
         // 连接服务端
         ch = b.connect(ip, port).sync().channel();
        try {
               while (true) {
            	byte[] heartByte = getHeartByte(); 
            	send(heartByte,ch);
            	logger.info("客户端"+name+"向采集发送心跳包  message={}",ByteUtil.byteArrToHexString(heartByte));
            	Thread.sleep(3000);
            	byte[] inventoryByte = getInventoryByte();
            	send(inventoryByte,ch);
            	logger.info("客户端"+name+"向采集发送盘点数据  message={}",ByteUtil.byteArrToHexString(inventoryByte));	
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
          //  workerGroup.shutdownGracefully();
        }
    }
    /**
     * 获取盘点 byte
     * @return
     */
    private byte[] getInventoryByte() {
    	StringBuilder str = new StringBuilder("55AA01120045");
    	str.append(getRandomName()).append("8036000000003500000000340000000033000000003200000000310000000030000000002F000000002E000000002D000000002C000000002B000000002A0000000029000000002800000000270000000026000000002500000000240000000023000000002200000000210000000020000000001F000000001E000000001D000000001CB4ACD8541B000000001A0000000019000000001800000000170000000016000000001500000000140000000013000000001200000000110000000010000000000F000000000E000000000D000000000C000000000B000000000A0000000009000000000800000000070000000006000000000500000000040000000003000000000200000000010000000039");
		return toBytes(str.toString());
	}
    /**
     * 将16进制字符串转换为byte[]
     * 
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }
    /**
     * 发送
     * @param heartByte
     * @param channel
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void send(byte[] heartByte,Channel channel) {
/*		if (channel != null && channel.isOpen() && channel.isWritable()) {
			System.out.println("客户端利用连接: " + channel + " 发送消息");
			String s =  " say Hello";
			channel.write(s);
		}*/
		
    	ByteBuf bs  = Unpooled.copiedBuffer(heartByte);
    	ChannelFuture cf = channel.writeAndFlush(bs);
    	cf.addListener(new GenericFutureListener() {

			@Override
			public void operationComplete(Future future) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("==============发送成功"+future.isSuccess());
			}
    		
    	});
		
	}

	/**
     * 获取心跳比特
     * @return
     */
    private byte[] getHeartByte() {
		byte[] heartByte = new byte[9];
		//55AA000400459B8241   第7个字节是name
		heartByte[0]=(byte)0x55;
		heartByte[1]=(byte)0xAA;
		heartByte[2]=(byte)0x00;
		heartByte[3]=(byte)0x04;
		heartByte[4]=(byte)0x00;
		heartByte[5]=(byte)0x45;
//		String a =getRandomName();
		heartByte[6]=(byte)0xa;
		heartByte[7]=(byte)0x82;
		heartByte[8]=(byte)0x41;
		
		return heartByte;
	}

	private String getRandomName() {  //随机获取个名称
		String [] arr = {"9B","36","3A","9F","77","18","08"};
		
		return arr[new Random().nextInt(arr.length)];
	}

	public static void main(String[] args) throws Exception {
        new NettyClient("127.0.0.1", 16868,"小米").run();
    }
}
