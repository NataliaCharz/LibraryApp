package com.bookcase.demo.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
public class MqttService {

    private final String topic = "topic/library";
    private MqttClient client;

    @Setter
    private Consumer<String> messageHandler;

    @PostConstruct
    public void connect() {
        try {
            String broker = "ssl://broker.emqx.io:8883";
            String clientId = "springboot_mqtt_client" + UUID.randomUUID();
            client = new MqttClient(broker, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            client.connect(options);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.err.println("MQTT connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    String payload = new String(message.getPayload());
                    System.out.println("MQTT received: " + payload);
                    if (messageHandler != null) {
                        messageHandler.accept(payload);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
            client.subscribe(topic, 1);
            System.out.println("MQTT connected and subscribed to topic: " + topic);

        } catch (MqttException e) {
            throw new RuntimeException("Failed to connect to MQTT broker", e);
        }
    }

    public void publish(String msg) {
        try {
            if (client != null && client.isConnected()) {
                MqttMessage message = new MqttMessage(msg.getBytes());
                message.setQos(1);
                client.publish(topic, message);
            }
        } catch (MqttException e) {
            throw new RuntimeException("Failed to publish MQTT message", e);
        }
    }

    @PreDestroy
    public void disconnect() {
        try {
            if (client != null) {
                client.disconnect();
                client.close();
            }
        } catch (MqttException e) {
            throw new RuntimeException("Failed to disconnect from MQTT broker", e);
        }
    }
}


