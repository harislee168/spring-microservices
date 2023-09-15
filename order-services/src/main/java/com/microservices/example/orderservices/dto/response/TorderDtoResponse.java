package com.microservices.example.orderservices.dto.response;

import com.microservices.example.orderservices.dto.TorderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TorderDtoResponse {

    private TorderDto torderDto;
    private String errorMessage;
}
