package com.jayway.foodvoting.enums;

public enum Months {
  JANUARY (0),
  FEBRUARY(1),
  MARCH(2),
  APRIL(3),
  MAY(4),
  JUNE(5),
  JULY(6),
  AUGUST(7),
  SEPTEMBER(8),
  OCTOBER(9),
  NOVEMBER(10),
  DECEMBER(11);

  private final int value;

  Months(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }


}
