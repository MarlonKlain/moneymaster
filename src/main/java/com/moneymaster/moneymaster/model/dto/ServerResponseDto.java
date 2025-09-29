package com.moneymaster.moneymaster.model.dto;

import java.util.Objects;

public class ServerResponseDto {
    String message;

    public ServerResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ServerResponseDto that = (ServerResponseDto) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }

    @Override
    public String toString() {
        return "ServerResponseDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
