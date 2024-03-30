package ru.radya100.ks5;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("sms")
    private Long phoneNumber;

    @NotNull
    @JsonProperty("balance")
    private Double balance;
}
