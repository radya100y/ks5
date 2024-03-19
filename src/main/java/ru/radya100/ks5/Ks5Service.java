package ru.radya100.ks5;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

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
    public KStream<String, User> kStream(StreamsBuilder kStreamBuilder) {

        KStream<String, User> userStream = kStreamBuilder
                .stream("src", Consumed.with(Serdes.String(), Serdes.String()))
                .mapValues(Ks5Mapper::getUserFromString)
                .filter((key, value) -> value != null)
                .filter((key, value) -> value.getBalance() != null)
                .filter((key, value) -> value.getBalance() <= 0);

        userStream.to("out", Produced.with(Serdes.String(), userSerde()));
        return userStream;
    }
}
