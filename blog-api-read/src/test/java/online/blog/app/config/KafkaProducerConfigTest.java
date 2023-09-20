//package online.blog.app.config;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.junit.jupiter.api.Test;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class KafkaProducerConfigTest {
//
//    @Test
//    public void testProducerFactory() {
//        // Create an instance of KafkaProducerConfig
//        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
//
//        // Call the producerFactory() method
//        ProducerFactory<String, String> producerFactory = kafkaProducerConfig.producerFactory();
//
//        // Verify that the producerFactory is not null
//        assertNotNull(producerFactory);
//
//        // Get the producer configuration properties
//        Map<String, Object> producerProps = producerFactory.getConfigurationProperties();
//
//        // Verify that the producer properties are set correctly
//        assertEquals("http://localhost:9092", producerProps.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
//        assertEquals(StringSerializer.class, producerProps.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
//        assertEquals(StringSerializer.class, producerProps.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
//    }
//
//    @Test
//    public void testKafkaTemplate() {
//        // Create an instance of KafkaProducerConfig
//        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
//
//        // Mock the ProducerFactory
//        ProducerFactory<String, String> mockProducerFactory = mock(DefaultKafkaProducerFactory.class);
//
//        // Set up the KafkaTemplate
//        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(mockProducerFactory);
//
//        // Call the kafkaTemplate() method
//        KafkaTemplate<String, String> result = kafkaProducerConfig.kafkaTemplate();
//
//        // Verify that the returned KafkaTemplate is not null
//        assertNotNull(result);
//
//        // Verify that the KafkaTemplate returned is the same as the one set up with the mock
//        assertSame(kafkaTemplate, result);
//    }
//}
