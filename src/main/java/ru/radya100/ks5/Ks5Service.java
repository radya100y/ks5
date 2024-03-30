package ru.radya100.ks5;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@EnableKafka
@EnableKafkaStreams
@RequiredArgsConstructor
public class Ks5Service {

    @Autowired
    private final StreamsBuilder streamsBuilder;

    @Bean
    private Serde<User> userSerde() {
        return Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(User.class));
    }

    @Bean
    public KStream<String, User> userKStream(StreamsBuilder streamsBuilder) {
        KStream<String, User> userKStream = streamsBuilder
                .stream("src", Consumed.with(Serdes.String(), userSerde()))
                .filter((key, value) -> value.getBalance() != null)
                .filter((key, value) -> value.getBalance() <= 0);

        userKStream.to("out", Produced.with(Serdes.String(), userSerde()));
        return userKStream;
    }

    @Bean
    public KTable<String, User> userKTable(StreamsBuilder streamsBuilder) {
        KTable<String, User> userKTable = streamsBuilder
                .stream("src", Consumed.with(Serdes.String(), userSerde()))
                .map((key, value) -> new KeyValue<>(value.getId().toString(), value))
                .peek((key, value) -> System.out.println("key -> " + key + " value -> " + value))
                .toTable(Materialized.with(Serdes.String(), userSerde()));

        return userKTable;
    }
}
