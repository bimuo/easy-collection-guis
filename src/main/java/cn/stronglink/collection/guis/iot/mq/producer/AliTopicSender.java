package cn.stronglink.collection.guis.iot.mq.producer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PreDestroy;

import com.aliyun.alink.apiclient.CommonRequest;
import com.aliyun.alink.apiclient.CommonResponse;
import com.aliyun.alink.apiclient.IoTCallback;
import com.aliyun.alink.apiclient.utils.StringUtils;
import com.aliyun.alink.dm.api.DeviceInfo;
import com.aliyun.alink.dm.api.InitResult;
import com.aliyun.alink.dm.model.ResponseModel;
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
import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import cn.stronglink.collection.guis.core.util.ContextUtils;
import cn.stronglink.collection.guis.iot.moudle.Device;
import cn.stronglink.collection.guis.iot.moudle.DeviceRepository;
import cn.stronglink.collection.guis.iot.vo.TagVo;

public class AliTopicSender {
	private final static String TAG = "AliTopicSender";
	/**
	 * 产品key
	 */
	private final static String PRODUCT_KEY = "a1ygc8aR7Yq";
	/**
	 * 设备信息
	 */
	private DeviceInfo deviceInfo = new DeviceInfo();

	private DeviceRepository deviceRepository = (DeviceRepository) ContextUtils.getBean(DeviceRepository.class);

	private boolean isInit = false;

	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

	/**
	 * 初始化设备
	 * 
	 * @param deviceCode
	 */
	public void init(String deviceCode) {
		this.setInit(false);
		Preconditions.checkNotNull(deviceCode, "设备编号为空。");

		deviceInfo.productKey = PRODUCT_KEY;
		deviceInfo.deviceName = deviceCode;
		deviceInfo.deviceSecret = null;

		Optional<Device> device = deviceRepository.findById(deviceCode);
		if (device.isPresent()) {
			deviceInfo.deviceSecret = device.get().getDeviceSecret();
			this.sdkInit(deviceInfo);
		} else {
			this.register(deviceInfo);
		}
	}

	@PreDestroy
	public void destory() {
		this.sdkDestory();
	}

	private void register(DeviceInfo deviceInfo) {
		// ####### 一型一密动态注册接口开始 ######
		/**
		 * 注意：动态注册成功，设备上线之后，不能再次执行动态注册，云端会返回已注册。
		 */
		LinkKitInitParams params = new LinkKitInitParams();
		IoTMqttClientConfig config = new IoTMqttClientConfig();
		params.mqttClientConfig = config;
		params.deviceInfo = deviceInfo;
		final CommonRequest request = new CommonRequest();
		request.setPath("/auth/register/device");
		LinkKit.getInstance().deviceRegister(params, request, new IoTCallback() {
			public void onFailure(CommonRequest commonRequest, Exception e) {
				ALog.e(TAG, "动态注册失败 " + e);
			}

			public void onResponse(CommonRequest commonRequest, CommonResponse commonResponse) {
				if (commonResponse == null || StringUtils.isEmptyString(commonResponse.getData())) {
					ALog.e(TAG, "动态注册失败 response=null");
					return;
				}
				try {
					@SuppressWarnings("serial")
					ResponseModel<Map<String, String>> response = new Gson().fromJson(commonResponse.getData(),
							new TypeToken<ResponseModel<Map<String, String>>>() {
							}.getType());
					if (response != null && "200".equals(response.code)) {
						ALog.d(TAG, "动态注册成功" + (commonResponse == null ? "" : commonResponse.getData()));
						/**
						 * 获取 deviceSecret, 存储到本地，然后执行初始化建联 这个流程只能走一次，获取到 secret 之后，下次启动需要读取本地存储的三元组，
						 * 直接执行初始化建联，不可以再走动态初始化
						 */
						deviceInfo.deviceSecret = response.data.get("deviceSecret");
						AliTopicSender.this.save(deviceInfo); // 将自动注册的设备三元素信息保存到数据库
						AliTopicSender.this.sdkInit(deviceInfo); // 初始化设备
						return;
					}
				} catch (Exception e) {
				}
				ALog.d(TAG, "动态注册失败" + commonResponse.getData());
			}
		});
		// ####### 一型一密动态注册接口结束 ######
	}

	@SuppressWarnings("rawtypes")
	public void devPropertyPush(String deviceCode, List<TagVo> tags) {
		Preconditions.checkArgument(this.isInit(),"设备未在阿里初始化，无法推送设备属性信息。");
		List<ValueWrapper> vwTags = new ArrayList<>();
		for (TagVo tag : tags) {
			Map<String, ValueWrapper> svwTag = new HashMap<>();
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
		Preconditions.checkArgument(this.isInit(),"设备未在阿里初始化，无法发送信息。");
		MqttPublishRequest request = new MqttPublishRequest();

		// topic 用户根据实际场景填写
		request.topic = "/sys/" + this.deviceInfo.productKey + "/" + this.deviceInfo.deviceName
				+ "/thing/deviceinfo/update";
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

	/**
	 * 保存设备数据到数据库
	 * 
	 * @param deviceInfo
	 */
	private void save(DeviceInfo deviceInfo) {
		Device device = new Device();
		device.setProductKey(deviceInfo.productKey);
		device.setDeviceName(deviceInfo.deviceName);
		device.setDeviceSecret(deviceInfo.deviceSecret);
		deviceRepository.save(device);
	}

	@SuppressWarnings("rawtypes")
	private void sdkInit(DeviceInfo deviceInfo) {
		LinkKitInitParams params = new LinkKitInitParams();
		/**
		 * 设置 Mqtt 初始化参数
		 */
		IoTMqttClientConfig config = new IoTMqttClientConfig();
		/**
		 * 是否接受离线消息 对应 mqtt 的 cleanSession 字段
		 */
		params.mqttClientConfig = config;
		/**
		 * 设置初始化三元组信息，用户传入
		 */
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
				AliTopicSender.this.setInit(false);
			}

			public void onInitDone(InitResult initResult) {
				ALog.i(TAG, "onInitDone result=" + initResult);
				AliTopicSender.this.setInit(true);
			}
		});
	}

	private void sdkDestory() {
		// 取消注册 notifyListener，notifyListener对象需和注册的时候是同一个对象
//		LinkKit.getInstance().unRegisterOnNotifyListener(notifyListener);
		LinkKit.getInstance().deinit();
		this.setInit(false);
	}
}
