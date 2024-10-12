package com.checkout.entity;


import com.checkout.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checkout")
@Embeddable
public class Checkout implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String customerName;
  private String cardNumber;
  private String cardName;
  private String cardFlag;
  private String cardDueDate;
  private String cardCvv;
  private Double amount;
  private Status status;
}
