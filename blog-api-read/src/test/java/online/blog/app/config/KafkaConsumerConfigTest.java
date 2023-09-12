//package online.blog.app.config;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.mockito.Mockito.mock;
//
//import java.util.Map;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.kafka.test.utils.ContainerTestUtils;
//import org.springframework.kafka.test.utils.KafkaEmbedded;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//
//import online.blog.app.entity.User;
//
//@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
//public class KafkaConsumerConfigTest {
//
//    @Test
//    public void testConsumerFactory() {
//        // Create an instance of KafkaConsumerConfig
//        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();
//
//        // Call the consumerFactory() method
//        ConsumerFactory<String, String> consumerFactory = kafkaConsumerConfig.consumerFactory();
//
//        // Verify that the consumerFactory is not null
//        assertNotNull(consumerFactory);
//
//        // Get the consumer configuration properties
//        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("group-id", "true", KafkaEmbedded.SPRING_EMBEDDED_KAFKA_BROKERS);
//        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
//        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
//
//        // Verify that the consumer properties are set correctly
//        assertEquals(consumerProps, consumerFactory.getConfigurationProperties());
//    }
//
//    @Test
//    public void testKafkaListenerContainerFactory() {
//        // Create an instance of KafkaConsumerConfig
//        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();
//
//        // Mock the ConsumerFactory
//        ConsumerFactory<String, String> mockConsumerFactory = mock(DefaultKafkaConsumerFactory.class);
//
//        // Set up the ConcurrentKafkaListenerContainerFactory
//        ConcurrentMessageListenerContainer<String, String> container = new ConcurrentMessageListenerContainer<>(mockConsumerFactory);
//        ContainerTestUtils.waitForAssignment(container, 0);
//
//        // Call the kafkaListenerContainerFactory() method
//        ConcurrentMessageListenerContainer<String, String> result = kafkaConsumerConfig.kafkaListenerContainerFactory().createContainer("test");
//
//        // Verify that the returned container is not null
//        assertNotNull(result);
//
//        // Verify that the ConcurrentKafkaListenerContainerFactory returned is the same as the one set up with the mock
//        assertSame(container, result);
//    }
//
//    @Test
//    public void testUserConsumerFactory() {
//        // Create an instance of KafkaConsumerConfig
//        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();
//
//        // Call the userConsumerFactory() method
//        ConsumerFactory<String, User> userConsumerFactory = kafkaConsumerConfig.userConsumerFactory();
//
//        // Verify that the userConsumerFactory is not null
//        assertNotNull(userConsumerFactory);
//
//        // Get the user consumer configuration properties
//        Map<String, Object> userConsumerProps = KafkaTestUtils.consumerProps("group-id-2", "true", KafkaEmbedded.SPRING_EMBEDDED_KAFKA_BROKERS);
//        userConsumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
//        userConsumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
//        userConsumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        // Verify that the user consumer properties are set correctly
//        assertEquals(userConsumerProps, userConsumerFactory.getConfigurationProperties());
//    }
//
//    @Test
//    public void testUserKafkaListenerContainerFactory() {
//        // Create an instance of KafkaConsumerConfig
//        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();
//
//        // Mock the User ConsumerFactory
//        ConsumerFactory<String, User> mockUserConsumerFactory = mock(DefaultKafkaConsumerFactory.class);
//
//        // Set up the ConcurrentKafkaListenerContainerFactory for User
//        ConcurrentMessageListenerContainer<String, User> userContainer = new ConcurrentMessageListenerContainer<>(mockUserConsumerFactory);
//        ContainerTestUtils.waitForAssignment(userContainer, 0);
//
//        // Call the userKafkaListenerContainerFactory() method
//        ConcurrentMessageListenerContainer<String, User> result = kafkaConsumerConfig.userKafkaListenerContainerFactory().createContainer("test");
//
//        // Verify that the returned User container is not null
//        assertNotNull(result);
//
//        // Verify that the ConcurrentKafkaListenerContainerFactory for User returned is the same as the one set up with the mock
//        assertSame(userContainer, result);
//    }
//}
