package com.checkout;

import static org.hamcrest.Matchers.containsString;

import com.checkout.api.CheckoutAPI;
import com.checkout.dto.CheckoutDTO;
import com.checkout.dto.GatewayResponseDTO;
import com.checkout.dto.ResponseDTO;
import com.checkout.entity.Checkout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class CheckoutApplicationTests {
  @Autowired
  private CheckoutAPI checkoutAPI;

  @Test
  void contextLoads() {
    assertThat(checkoutAPI).isNotNull();
  }


  @Test
  void shouldBeApproved() throws JsonProcessingException {
    String json = "{\n" +
        "      \"name\": \"Vanessa\",\n" +
        "        \"cardInfo\": {\n" +
        "      \"number\": \"9999999999999999\",\n" +
        "          \"name\": \"Vanessa\",\n" +
        "          \"flag\": \"master\",\n" +
        "          \"dueDate\": \"12/29\",\n" +
        "          \"cvv\": \"999\",\n" +
        "          \"amount\": 988.87\n" +
        "    }\n" +
        "    }\n";

    ObjectMapper objectMapper = new ObjectMapper();
    CheckoutDTO checkoutDTO = objectMapper.readValue(json, CheckoutDTO.class);

    ResponseEntity<ResponseDTO> checkout = checkoutAPI.post(checkoutDTO);
    assertThat(checkout.getBody()).isNotNull();
    assertThat(checkout.getBody().getStatus()).isEqualTo("APPROVED");
  }

  @Test
  void shouldBeReproved() throws JsonProcessingException {
    String json = "{\n" +
        "      \"name\": \"Vanessa\",\n" +
        "        \"cardInfo\": {\n" +
        "      \"number\": \"8888888888888888\",\n" +
        "          \"name\": \"Vanessa\",\n" +
        "          \"flag\": \"master\",\n" +
        "          \"dueDate\": \"12/29\",\n" +
        "          \"cvv\": \"999\",\n" +
        "          \"amount\": 988.87\n" +
        "    }\n" +
        "    }\n";

    ObjectMapper objectMapper = new ObjectMapper();
    CheckoutDTO checkoutDTO = objectMapper.readValue(json, CheckoutDTO.class);

    ResponseEntity<ResponseDTO> checkout = checkoutAPI.post(checkoutDTO);
    assertThat(checkout.getBody()).isNotNull();
    assertThat(checkout.getBody().getStatus()).isEqualTo("FAILED");
  }

  @Test
  void shouldBeCardNumberInvalid() throws JsonProcessingException {
    String json = "{\n" +
        "      \"name\": \"Vanessa\",\n" +
        "        \"cardInfo\": {\n" +
        "      \"number\": \"99999\",\n" +
        "          \"name\": \"Vanessa\",\n" +
        "          \"flag\": \"master\",\n" +
        "          \"dueDate\": \"12/29\",\n" +
        "          \"cvv\": \"999\",\n" +
        "          \"amount\": 988.87\n" +
        "    }\n" +
        "    }\n";

    ObjectMapper objectMapper = new ObjectMapper();
    CheckoutDTO checkoutDTO = objectMapper.readValue(json, CheckoutDTO.class);

    ResponseEntity<ResponseDTO> checkout = checkoutAPI.post(checkoutDTO);
    assertThat(checkout.getBody()).isNotNull();
    assertThat(checkout.getBody().getStatus()).isEqualTo("FAILED");
    assertThat(checkout.getBody().getMessage()).isEqualTo("Erro ao processar pagamento: Cartao invalido");
  }

//  @Test
//  void shouldReturnDefaultMessage() throws Exception {
//    this.mockMvc.perform(get("http://localhost:3000/health")).andDo(print()).andExpect(status().isOk())
//        .andExpect(content().string(containsString("Gateway is on")));
//  }

}
