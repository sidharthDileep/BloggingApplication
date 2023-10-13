package online.blog.app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	
	@Value("${value.from.file.kafka}")
	private String kafkaServer;
	
//	@Bean
//	public ConsumerFactory<String, String> consumerFactoryMsg() {
//		Map<String, Object> configs = new HashMap<>();
//		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "user");
//		return new DefaultKafkaConsumerFactory<>(configs);
//	}
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryMsg() {
//		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
//		factory.setConsumerFactory(consumerFactory());
//		return factory;
//	}
//
//	// config for json data
//
//	@Bean
//	public ConsumerFactory<Object, Object> cqrsConsumerFactory() {
//		Map<String, Object> configs = new HashMap<>();
//		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "post-event-group");
//		return new DefaultKafkaConsumerFactory<>(configs, new JsonDeserializer(PostEvent.class), new JsonDeserializer<>(Post.class));
//	}
//	
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, User> cqrsKafkaListenerContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<String, User>();
//		factory.setConsumerFactory(cqrsConsumerFactory());
//		return factory;
//	}
	
	@Bean
    public NewTopic createTopic(){
        return new NewTopic("post-event-topic", 1, (short) 1);
    }
	
	@Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        		"54.236.223.174:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
