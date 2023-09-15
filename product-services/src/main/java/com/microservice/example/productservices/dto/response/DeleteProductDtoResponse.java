package com.microservice.example.productservices.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductDtoResponse {

    private int noOfRecordDeleted;
    private String errorMessage;
}
