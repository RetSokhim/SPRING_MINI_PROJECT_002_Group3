package org.example.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse <T>{
    private HttpStatus status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
}
