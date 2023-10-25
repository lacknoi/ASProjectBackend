# ASProjectFrontend

--Kafka
https://kafka.apache.org/quickstart
Step 2: Start the Kafka environment
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
bin\windows\kafka-server-start.bat config\server.properties
bin\windows\kafka-topics.bat --create --topic account-topic --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic mobile-topic --bootstrap-server localhost:9092
Step 3: Create a topic to store your events
bin\windows\kafka-topics.bat --describe --topic account-topic --bootstrap-server localhost:9092
Step 4: Write some events into the topic
bin\windows\kafka-console-producer.bat --topic account-topic --bootstrap-server localhost:9092
Step 5: Read the events
bin\windows\kafka-console-consumer.bat --topic account-topic --from-beginning --bootstrap-server localhost:9092
Step 6: Import/export your data as streams of events with Kafka Connect
bin\windows\connect-standalone.bat config\connect-standalone.properties config\connect-file-source.properties config\connect-file-sink.properties