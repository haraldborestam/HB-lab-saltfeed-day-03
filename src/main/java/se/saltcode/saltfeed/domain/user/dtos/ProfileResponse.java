package se.saltcode.saltfeed.domain.user.dtos;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("profile")
public record ProfileResponse(
        ProfileDto profile
){}
