package com.matag.cards;

import com.matag.cards.properties.Color;
import com.matag.cards.properties.Rarity;
import com.matag.cards.properties.Type;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.matag.cards.ability.type.AbilityType.TAP_ADD_MANA;
import static com.matag.cards.properties.Cost.COLORLESS;
import static java.util.Collections.emptyList;

public class CardUtils {
  public static Card hiddenCard() {
    return new Card("card", "/img/card-back.jpg", new TreeSet<>(), emptyList(), new TreeSet<>(), new TreeSet<>(), Rarity.COMMON, "", 0, 0, emptyList());
  }

  public static List<Color> colorsOfManaThatCanGenerate(Card card) {
    return card.getAbilities().stream()
      .filter(ability -> ability.getAbilityType().equals(TAP_ADD_MANA))
      .flatMap(ability -> ability.getParameters().stream())
      .map(Color::valueOf)
      .collect(Collectors.toList());
  }

  public static boolean isColorless(Card card) {
    return card.getCost().stream().noneMatch(cost -> cost != COLORLESS);
  }

  public static boolean isOfType(Card card, Type type) {
    return card.getTypes().contains(type);
  }

  public static boolean isNotOfType(Card card, Type type) {
    return !isOfType(card, type);
  }

  public static boolean isOfColor(Card card, Color color) {
    return card.getColors().contains(color);
  }

  public static boolean isOfOnlyAnyOfTheColors(Card card, Set<Color> colors) {
    if (card.getColors().isEmpty()) {
      return false;
    }

    for (Color color : card.getColors()) {
      if (!colors.contains(color)) {
        return false;
      }
    }
    return true;
  }
}
