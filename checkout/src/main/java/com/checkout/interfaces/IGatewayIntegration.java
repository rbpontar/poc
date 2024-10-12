package com.checkout.interfaces;

import com.checkout.dto.CardDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IGatewayIntegration {
  boolean integrate(CardDTO cardDTO) throws Exception;
}
