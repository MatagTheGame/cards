package com.matag.cards.properties;

import static java.lang.Integer.parseInt;

import lombok.Data;

@Data
public class PowerToughness {
  private final int power;
  private final int toughness;

  public PowerToughness(int power, int toughness) {
    this.power = power;
    this.toughness = toughness;
  }

  public static PowerToughness powerToughness(String powerToughnessString) {
    var powerToughness = powerToughnessString.split("/");
    return new PowerToughness(parseInt(powerToughness[0]), parseInt(powerToughness[1]));
  }
}
