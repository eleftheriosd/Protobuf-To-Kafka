package kafka;

import com.github.javafaker.Faker;

import java.util.Random;

// import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
// import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
// import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializerConfig;
import com.example.CardData;

import java.util.Properties;
// import java.util.concurrent.ExecutionException;

public class SendKafkaProto {
  public static void main(String[] args) {
    String bootstrapServers = "127.0.0.1:9092";
    var properties = new Properties();

    properties.setProperty("bootstrap.servers", bootstrapServers);
    properties.setProperty("schema.registry.url", "http://localhost:8081");

    properties.setProperty("key.serializer", StringSerializer.class.getName());
    properties.setProperty("value.serializer", KafkaProtobufSerializer.class.getName());

    KafkaProducer<String, CardData.CreditCard> producer = new KafkaProducer<>(properties);
    // Specify Topic
    var topic = "protos_topic_cards";

    for (int i = 0; i < 10; i++) {

      //
      // Construct Fake Object
      //
      Random rd = new Random(); // creating Random object
      Faker faker = new Faker();

      String name = faker.name().fullName();
      String countryCode = faker.address().countryCode();

      String cardNumber = faker.business().creditCardNumber();
      Integer typeValue = rd.nextInt(3);
      String currencyCode = faker.country().currencyCode();

      var cardData = CardData.CreditCard.newBuilder()
          .setName(name)
          .setCountry(countryCode)
          .setCurrency(currencyCode)
          .setTypeValue(typeValue)
          .setBlocked(false)
          .setCardNumber(cardNumber)
          .build();

      var record = new ProducerRecord<String, CardData.CreditCard>(topic, "Credit Card", cardData);
      producer.send(record);
    }
    producer.flush();
    producer.close();
    System.out.println("Sent Data Successfully");
  }
}