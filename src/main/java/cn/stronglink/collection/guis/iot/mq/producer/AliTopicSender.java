package cn.stronglink.collection.guis.iot.mq.producer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.aliyun.alink.dm.api.DeviceInfo;
import com.aliyun.alink.dm.api.InitResult;
import com.aliyun.alink.linkkit.api.ILinkKitConnectListener;
import com.aliyun.alink.linkkit.api.IoTMqttClientConfig;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linkkit.api.LinkKitInitParams;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;

import cn.stronglink.collection.guis.iot.vo.TagVo;

@Component("topicSender")
public class AliTopicSender {
	private final static String TAG = "AliTopicSender";
	/**
	 * 产品key
	 */
	private final static String pk = "a1ygc8aR7Yq";
	/**
	 * 设备名称
	 */
	private final static String dn = "guis";
	/**
	 * 设备密钥
	 */
	private final static String ds = "JqRuioJ67lWJSqPpeEvp8tto7AeboQSr";

	public AliTopicSender() {
		this.sdkInit();
	}

	@PreDestroy
	public void destory() {
		this.sdkDestory();
	}

	@SuppressWarnings("rawtypes")
	public void devPropertyPush(String deviceCode, List<TagVo> tags) {
		List<ValueWrapper> vwTags = new ArrayList<>();
		for(TagVo tag : tags){
			Map<String,ValueWrapper> svwTag = new HashMap<>();
			svwTag.put("tag", new ValueWrapper.StringValueWrapper(tag.getTag()));
			svwTag.put("u", new ValueWrapper.IntValueWrapper(tag.getU()));
			vwTags.add(new ValueWrapper.StructValueWrapper(svwTag));
		}
		// 设备上报
		Map<String, ValueWrapper> reportData = new HashMap<>();
		// identifier 是云端定义的属性的唯一标识，valueWrapper是属性的值
		reportData.put("deviceCode", new ValueWrapper.StringValueWrapper("12345"));
		reportData.put("tags", new ValueWrapper.ArrayValueWrapper(vwTags));
		LinkKit.getInstance().getDeviceThing().thingPropertyPost(reportData, new IPublishResourceListener() {
			public void onSuccess(String s, Object o) {
				// 属性上报成功
			}

			public void onError(String s, AError aError) {
				// 属性上报失败
			}
		});
	}

	public void send(String topic, String msg) {
		MqttPublishRequest request = new MqttPublishRequest();
		// topic 用户根据实际场景填写
		request.topic = "/sys/" + pk + "/" + dn + "/thing/deviceinfo/update";
		/**
		 * 订阅回复的 replyTopic 如果业务有相应的响应需求，可以设置 replyTopic，且 isRPC=true
		 */
//		        request.replyTopic = request.topic + "_reply";
		/**
		 * isRPC = true; 表示先订阅 replyTopic，然后再发布； isRPC = false; 不会订阅回复
		 */
//		        request.isRPC = true;
		// 更新标签 仅做测试
		request.payloadObj = msg;
		LinkKit.getInstance().getMqttClient().publish(request, new IConnectSendListener() {
			@Override
			public void onResponse(ARequest aRequest, AResponse aResponse) {
				// publish 结果
				ALog.d(TAG, "onResponse " + (aResponse == null ? "" : aResponse.data));
			}

			@Override
			public void onFailure(ARequest aRequest, AError aError) {
				// publish 失败
				ALog.d(TAG, "onFailure " + (aError == null ? "" : (aError.getCode() + aError.getMsg())));
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private void sdkInit() {
		LinkKitInitParams params = new LinkKitInitParams();
		/**
		 * 设置 Mqtt 初始化参数
		 */
		IoTMqttClientConfig config = new IoTMqttClientConfig();
		config.productKey = pk;
		config.deviceName = dn;
		config.deviceSecret = ds;
		/**
		 * 是否接受离线消息 对应 mqtt 的 cleanSession 字段
		 */
		params.mqttClientConfig = config;
		/**
		 * 设置初始化三元组信息，用户传入
		 */
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.productKey = pk;
		deviceInfo.deviceName = dn;
		deviceInfo.deviceSecret = ds;
		params.deviceInfo = deviceInfo;
		/**
		 * 设置设备当前的初始状态值，属性需要和云端创建的物模型属性一致 如果这里什么属性都不填，物模型就没有当前设备相关属性的初始值。
		 * 用户调用物模型上报接口之后，物模型会有相关数据缓存。
		 */
		Map<String, ValueWrapper> propertyValues = new HashMap<String, ValueWrapper>();
		// propertyValues.put("LightSwitch", new ValueWrapper.BooleanValueWrapper(0));
		propertyValues.put("deviceCode", new ValueWrapper.StringValueWrapper("12345"));
		propertyValues.put("tags", new ValueWrapper.ArrayValueWrapper(null));
		params.propertyValues = propertyValues;
		LinkKit.getInstance().init(params, new ILinkKitConnectListener() {

			@Override
			public void onError(AError aError) {
				ALog.e(TAG, "Init Error error=" + aError);
			}

			public void onInitDone(InitResult initResult) {
				ALog.i(TAG, "onInitDone result=" + initResult);
			}
		});
	}

	private void sdkDestory() {
		// 取消注册 notifyListener，notifyListener对象需和注册的时候是同一个对象
//		LinkKit.getInstance().unRegisterOnNotifyListener(notifyListener);
		LinkKit.getInstance().deinit();
	}
}
