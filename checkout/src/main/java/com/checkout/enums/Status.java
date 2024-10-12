package com.checkout.enums;

public enum Status {

  RECIEVED(0, "RECIEVED"),
  APPROVED(1, "APPROVED"),
  FAILED(2, "FAILED");

  private int id;
  private String name;

  Status(final int id, final String name) {
    this.id = id;
    this.name = name;
  }
}
