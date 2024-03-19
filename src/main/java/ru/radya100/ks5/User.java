package ru.radya100.ks5;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class User {

    @JsonProperty("phone_number")
    private Long phoneNumber;

    @NotNull
    @JsonProperty("balance")
    private Double balance;
}
