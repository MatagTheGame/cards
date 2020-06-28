package com.matag.cards;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.matag.cards.sets.MtgSet;
import com.matag.cards.sets.MtgSets;
import com.matag.downloader.CardScryFallLinker;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.matag.cards.properties.Type.BASIC;
import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
@Import(CardsConfiguration.class)
public class LinkerTest {

  @Autowired
  private Cards cards;

  @Autowired
  private MtgSets mtgSets;

  @Test
  public void scryFallLinker() throws Exception {
    ObjectMapper cardsObjectMapper = createCardsObjectMapper();
    ObjectMapper setsObjectMapper = createSetsObjectMapper();

    Map<String, MtgSet> sets = mtgSets.getSets();

    List<Card> cardsToLink = cards.getAll().stream()
      .filter(card -> StringUtils.isBlank(card.getImageUrl()))
      .collect(toList());

    for (int i = 0; i < cardsToLink.size(); i++) {
      Card card = cardsToLink.get(i);
      CardScryFallLinker cardScryFallLinker = new CardScryFallLinker(card);
      Card updatedCard = card.toBuilder()
          .imageUrl(cardScryFallLinker.getImage())
          .types(cardScryFallLinker.getTypes())
          .subtypes(cardScryFallLinker.getSubtypes())
          .power(cardScryFallLinker.getPower())
          .toughness(cardScryFallLinker.getToughness())
          .rarity(cardScryFallLinker.getRarity())
          .ruleText(cardScryFallLinker.getOracleText())
          .colors(cardScryFallLinker.getColors())
          .cost(cardScryFallLinker.getManaCost())
          .build();
      String cardJson = cardsObjectMapper.writeValueAsString(updatedCard);
      Files.write(Paths.get(CardsConfiguration.getResourcesPath() + "/cards/" + card.getName() + ".json"), cardJson.getBytes());
      System.out.println("Downloaded " + (i + 1) + " of " + cardsToLink.size() + " -> " + card.getName());

      if (!card.getTypes().contains(BASIC)) {
        for (String scryFallSet : cardScryFallLinker.getSets()) {
          if (sets.containsKey(scryFallSet)) {
            sets.get(scryFallSet).getCards().add(card.getName());
            String setJson = setsObjectMapper.writeValueAsString(sets.get(scryFallSet));
            Files.write(Paths.get(CardsConfiguration.getResourcesPath() + "/sets/" + scryFallSet + ".json"), setJson.getBytes());
          }
        }
      }
    }
  }

  public ObjectMapper createCardsObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
    return objectMapper;
  }

  public ObjectMapper createSetsObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
    prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
    objectMapper.setDefaultPrettyPrinter(prettyPrinter);
    return objectMapper;
  }
}
