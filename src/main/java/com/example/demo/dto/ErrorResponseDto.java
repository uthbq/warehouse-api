package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponseDto {

    @Schema(description = "Сообщение с описанием ошибки", example = "Недостаточно товара на складе")
    private String message;
    @Schema(description = "Временная метка возникновения ошибки в миллисекундах", example = "1722000000000")
    private long time;

    public ErrorResponseDto(String message, long time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
