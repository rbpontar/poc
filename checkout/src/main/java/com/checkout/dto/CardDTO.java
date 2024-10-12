package com.checkout.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
  private String number;
  private String name;
  private String flag;
  private String dueDate;
  private String cvv;
  private Double amount;
}
