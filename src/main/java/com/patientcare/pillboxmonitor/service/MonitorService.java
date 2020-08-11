package com.patientcare.pillboxmonitor.service;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MonitorService {
	
	@Autowired
	private IMqttClient mqttClient;
	
	@Value("${PILLBOX_API}")
	private String PILLBOX_API;
	
	private String PILLCONSUME_PATH = "/pillConsumed?pillboxid=";
	private String PILLBOX_DISCONNECT_PATH = "/pillboxDisconnected?payload=";

	public void publish(final String topic, final String payload, int qos, boolean retained)
			throws MqttPersistenceException, MqttException {
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(payload.getBytes());
		mqttMessage.setQos(qos);
		mqttMessage.setRetained(retained);

		mqttClient.publish(topic, mqttMessage);
		
		//mqttClient.publish(topic, payload.getBytes(), qos, retained);

		mqttClient.disconnect();
	}

	public void subscribe(final String topic) throws MqttException, InterruptedException {
		System.out.println("Messages received:");

		mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {
			System.out.println(msg.getId() + " -> " + new String(msg.getPayload()));
			if (!(new String(msg.getPayload())).contains("come")) {
				String payload = new String(msg.getPayload());
				if (payload.matches("[0-9]+")) {
					System.out.println("number");
					String url = PILLBOX_API + PILLCONSUME_PATH + payload;
					RestTemplate restTemplate = new RestTemplate();
					String resp = restTemplate.getForObject(url, String.class);
					System.out.println(resp);
				} else {
					System.out.println("string");
					String url = PILLBOX_API + PILLBOX_DISCONNECT_PATH + payload;
					RestTemplate restTemplate = new RestTemplate();
					String resp = restTemplate.getForObject(url, String.class);
					System.out.println(resp);
				}
			}
			
		});
	}


}
