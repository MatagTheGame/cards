package com.matag.cards;

import java.util.Set;

import com.matag.cards.properties.Color;
import com.matag.cards.properties.Subtype;
import com.matag.cards.properties.Type;

public class CardUtils {
  public static boolean isOfType(Card card, Type type) {
    return card.getTypes().contains(type);
  }

  public static boolean isNotOfType(Card card, Type type) {
    return !isOfType(card, type);
  }

  public static boolean isOfSubtype(Card card, Subtype subtype) {
    return card.getSubtypes().contains(subtype);
  }

  public static boolean isNotOfSubtype(Card card, Subtype subtype) {
    return !isOfSubtype(card, subtype);
  }

  public static boolean isColorless(Card card) {
    return card.getColors().isEmpty();
  }

  public static boolean isMulticolor(Card card) {
    return card.getColors().size() > 1;
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
