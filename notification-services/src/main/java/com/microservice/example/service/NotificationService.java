package com.microservice.example.service;

import com.microservice.example.dto.TorderDto;

public interface NotificationService {

    public void handleOrderMessage(TorderDto torderDto);
}
