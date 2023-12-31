package online.blog.app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {
	
	@Value("${value.from.file.kafka}")
	private String kafkaServer;
	
//    @Bean
//    public ProducerFactory<String, String> producerFactoryMsg() {
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
//    //@Qualifier("producer-1")
//    public KafkaTemplate<String, String> kafkaTemplateMsg() {
//        return new KafkaTemplate<>(producerFactoryMsg());
//    }
    

//	@Bean
//    public NewTopic createTopic(){
//        return new NewTopic("post-event-topic", 3, (short) 1);
//    }

    @Bean
    public Map<String,Object> producerConfig(){
        Map<String,Object> props=new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        		kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String,Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

}
