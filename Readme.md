# Protobuf-To-Kafka using Lenses Box

Set things up by running:

1. ## mvn generate-sources
2. ## mvn package

---

Once you have Lenses box up and running execute:

## java -cp target/kafka-send-proto-0.1.0.jar kafka.SendKafkaProto

---

If facing unkwown host issue on MacOS try editing your local hosts by running:
**sudo vim /etc/hosts**

and matching 127.0.0.1 to the host ip address.
