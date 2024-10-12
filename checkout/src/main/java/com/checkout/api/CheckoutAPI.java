package com.checkout.api;

import com.checkout.business.CheckoutBusiness;
import com.checkout.dto.CheckoutDTO;
import com.checkout.dto.ResponseDTO;
import com.checkout.entity.Checkout;
import com.checkout.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "checkout", produces = MediaType.APPLICATION_JSON_VALUE)
public class CheckoutAPI {

  @Autowired
  private CheckoutBusiness checkoutBusiness;

  @PostMapping()
  @ResponseBody
  public ResponseEntity<ResponseDTO> post(@RequestBody CheckoutDTO checkoutDTO) {
    try {
      Checkout checkout = checkoutBusiness.checkout(checkoutDTO);

      return new ResponseEntity<>(new ResponseDTO(checkout.getStatus().name(), "Pagamento Processado"), HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(new ResponseDTO(Status.FAILED.name(), "Erro ao processar pagamento: " + e.getMessage()),
          HttpStatus.BAD_REQUEST);
    }
  }
}
