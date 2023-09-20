//package online.blog.app.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//@Configuration
//public class KafkaProducerConfig {
//	
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            "http://localhost:9092" );
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
//            StringSerializer.class);     
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//            StringSerializer.class);
//        return new DefaultKafkaProducerFactory<>(props);
//    }
//
//    @Bean
//    //@Qualifier("msgkafka")
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//    
//    @Bean
//    public ProducerFactory<Object, Object> producerFactoryCQRS() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            "http://localhost:9092" );
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
//        		JsonDeserializer.class);     
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//            JsonDeserializer.class);
//        return new DefaultKafkaProducerFactory<>(props);
//    }
//    
//    @Bean
//    //@Qualifier("cqrskafka")
//    public KafkaTemplate<Object, Object> CQRSkafka() {
//        return new KafkaTemplate<>(producerFactoryCQRS());
//    }
//
//}
