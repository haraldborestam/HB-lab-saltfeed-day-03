package se.saltcode.saltfeed.web;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.validation.FieldError;

import java.util.List;

@JsonTypeName("errors")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public record ErrorResponse(
        List<String> body
) {

    public static String toErrorMessage(FieldError fieldError) {
        return "Field: '" + fieldError.getField() + "' " +
                fieldError.getDefaultMessage() +". ";
    }
}
