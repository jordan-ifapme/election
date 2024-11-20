package be.ifapme.election.handler;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }



}