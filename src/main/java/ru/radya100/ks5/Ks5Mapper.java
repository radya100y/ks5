package ru.radya100.ks5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class Ks5Mapper {

    public static User getUserFromString(String userString) {
        User user = null;
        try {
//            user = objectMapper().readValue(userString, User.class);
            user = new ObjectMapper().readValue(userString, User.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return user;
    }
}
