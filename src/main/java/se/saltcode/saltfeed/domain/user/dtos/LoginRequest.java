package se.saltcode.saltfeed.domain.user.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(use = Id.NAME, include = As.WRAPPER_OBJECT)
@JsonTypeName("user")
public record LoginRequest(
        @Email
        String email,
        @Size(min = 8, max = 32)
        String password
) {}
