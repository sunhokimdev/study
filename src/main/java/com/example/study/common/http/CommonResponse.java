package com.example.study.common.http;

import com.example.study.common.exception.Code;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.Errors;

import java.util.List;

@Getter
@Builder
public class CommonResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Code errorCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Errors> errors;
}
