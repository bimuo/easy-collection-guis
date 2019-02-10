package cn.stronglink.collection.guis.core.message;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;

public class MessageHandleContext<T,V> implements IMessageHandle<T,V> {
	private List<IMessageHandle<T,V>> handleList = new ArrayList<>();
	/**
	 * 是否只单独执
	 */
	private boolean isOnlyHandle=true;

	public List<IMessageHandle<T, V>> getHandleList() {
		return handleList;
	}

	public void setHandleList(List<IMessageHandle<T, V>> handleList) {
		this.handleList = handleList;
	}

	public boolean isOnlyHandle() {
		return isOnlyHandle;
	}

	public void setOnlyHandle(boolean isOnlyHandle) {
		this.isOnlyHandle = isOnlyHandle;
	}

	public MessageHandleContext() {
		this(true);
	}
	
	public MessageHandleContext(boolean isOnlayHandle) {
		this.isOnlyHandle=isOnlayHandle;
	}
	@Override
	public boolean isHandle(T t) {
		return true;
	}
	
	@Override
	public V handle(ChannelHandlerContext ctx, T t) {
		for(IMessageHandle<T,V> msg:handleList){
			if(msg.isHandle(t)){
				V obj = msg.handle(ctx,t);
				if(this.isOnlyHandle) return obj;
			}
		}
		return null;
	}
	
	/**
	 * 添加处理
	 * @param handle
	 */
	public void addHandleClass(IMessageHandle<T,V> handle){
		handleList.add(handle);
	}
	
	/**
	 * 清除有处理累
	 */
	public void clearHandleClass() {
		handleList.clear();
	}
}
