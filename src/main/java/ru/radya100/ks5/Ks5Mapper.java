package ru.radya100.ks5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@UtilityClass
@Slf4j
public class Ks5Mapper {

    public static Optional<User> getUserFromString(String userString) {
        Optional<User> user;
        try {
            user = Optional.of(new ObjectMapper().readValue(userString, User.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
