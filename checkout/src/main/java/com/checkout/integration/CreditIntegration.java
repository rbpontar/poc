package com.checkout.integration;

import com.checkout.dto.CardDTO;
import com.checkout.dto.GatewayResponseDTO;
import com.checkout.interfaces.IGatewayIntegration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreditIntegration implements IGatewayIntegration {
  public boolean integrate(CardDTO cardDTO) throws Exception {
    final String uri = "http://localhost:3000/payment";
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<CardDTO> request = new HttpEntity<>(cardDTO);
    String result = restTemplate.postForObject(uri, request, String.class);
    ObjectMapper objectMapper = new ObjectMapper();
    GatewayResponseDTO responseDTO = objectMapper.readValue(result, GatewayResponseDTO.class);
    assert result != null;
    if (responseDTO.getStatus() != 200) {
      throw new Exception(responseDTO.getMessage());
    }

    return responseDTO.getApproved();

  }
}
