package com.vlc.maeummal.global.apiPayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vlc.maeummal.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "data"})
public class ApiErrResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ApiErrResponse<T> onFailure(String code, String message, T data){
        return new ApiErrResponse<>(false, code, message, data);
    }
    public static <T> ApiErrResponse<T> onFailureWithCode(BaseErrorCode code, String message, T data) {
        return new ApiErrResponse<>(false, code.getReasonHttpStatus().getCode(), message, data);
    }
}
