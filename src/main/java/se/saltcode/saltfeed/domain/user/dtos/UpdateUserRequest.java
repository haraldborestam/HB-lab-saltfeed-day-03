package se.saltcode.saltfeed.domain.user.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeName("user")
public record UpdateUserRequest(
        @Email
        String email,
        @Pattern(regexp = "[a-zA-Z0-9_.-]{1,30}", message = "must contains alphabet or digit with length 1 to 30")
        String username,
        String bio,
        String image,
        @Size(min = 8, max = 32)
        String password
) {}
