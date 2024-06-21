package com.blackshoe.esthete.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private T payload;
    private String error;

    @Builder(builderMethodName = "success")
    public static <T> ResponseDto<T> success(T payload) {
        ResponseDto<T> dto = new ResponseDto<>();
        dto.setPayload(payload);
        return dto;
    }

    @Builder(builderMethodName = "error")
    public ResponseDto(String error) {
        this.error = error;
    }


    @Builder
    public ResponseDto(T payload, String error) {
        this.payload = payload;
        this.error = error;
    }

    public static class ResponseDtoBuilder<T> {
        private T payload;
        private String error;

        ResponseDtoBuilder() {}

        public ResponseDtoBuilder<T> payload(T payload) {
            this.payload = payload;
            return this;
        }

        public ResponseDtoBuilder<T> error(String error) {
            this.error = error;
            return this;
        }

        public ResponseDto<T> build() {
            return new ResponseDto<>(payload, error);
        }
    }
}