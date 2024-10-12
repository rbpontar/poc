package com.checkout.business;


import com.checkout.dto.CardDTO;
import com.checkout.dto.CheckoutDTO;
import com.checkout.entity.Checkout;
import com.checkout.enums.Status;
import com.checkout.interfaces.IGatewayIntegration;
import com.checkout.repository.CheckoutRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CheckoutBusiness {

  @Autowired
  private CheckoutRepository repository;

  @Autowired
  private IGatewayIntegration gatewayIntegration;

  private Checkout convertToEntity(CheckoutDTO checkoutDTO, Status status) {
    Checkout checkout = new Checkout();
    checkout.setCustomerName(checkoutDTO.getName());
    checkout.setAmount(checkoutDTO.getCardInfo().getAmount());

    checkout.setCardName(checkoutDTO.getCardInfo().getName());
    checkout.setCardNumber(checkoutDTO.getCardInfo().getNumber());
    checkout.setCardCvv(checkoutDTO.getCardInfo().getCvv());
    checkout.setCardFlag(checkoutDTO.getCardInfo().getFlag());
    checkout.setCardDueDate(checkoutDTO.getCardInfo().getDueDate());
    checkout.setStatus(status);

    return checkout;
  }

  public Checkout checkout(CheckoutDTO checkoutDTO) throws Exception {
    Checkout checkout = convertToEntity(checkoutDTO, Status.RECIEVED);
    checkout.setId(UUID.randomUUID());
    checkout = repository.save(checkout);
    boolean paymentOk = integrate(checkoutDTO.getCardInfo());
    checkout.setStatus(paymentOk ? Status.APPROVED : Status.FAILED);
    checkout = repository.save(checkout);
    return checkout;
  }


//  public List<DocumentDTO> find() {
//    return documentRepository.findAll().stream().map(this::convertToDTO).toList();
//  }

  public boolean integrate(CardDTO cardDTO) throws Exception {
    return gatewayIntegration.integrate(cardDTO);
  }
}
