//package online.blog.app.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//
//@Configuration
//@EnableKafka
//public class KafkaConsumerConfig {
//	
//	@Bean
//	public ConsumerFactory<String, String> consumerFactory() {
//		Map<String, Object> configs = new HashMap<>();
//		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "user");
//		return new DefaultKafkaConsumerFactory<>(configs);
//	}
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
//		factory.setConsumerFactory(consumerFactory());
//		return factory;
//	}
//
//	// config for json data
//
////	@Bean
////	public ConsumerFactory<Object, Object> cqrsConsumerFactory() {
////		Map<String, Object> configs = new HashMap<>();
////		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
////		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
////		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
////		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "post-event-group");
////		return new DefaultKafkaConsumerFactory<>(configs, new JsonDeserializer(PostEvent.class), new JsonDeserializer<>(Post.class));
////	}
//	
//
////	@Bean
////	public ConcurrentKafkaListenerContainerFactory<String, User> cqrsKafkaListenerContainerFactory() {
////		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<String, User>();
////		factory.setConsumerFactory(cqrsConsumerFactory());
////		return factory;
////	}
//
//}
