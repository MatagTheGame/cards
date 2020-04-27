package com.matag.cards.properties;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PowerToughnessTest {
    @Test
    public void powerToughnessPositiveTest() {
        // Given
        String powerToughnessString = "+1/+1";

        // When
        PowerToughness PowerToughness = com.matag.cards.properties.PowerToughness.powerToughness(powerToughnessString);

        // Then
        assertThat(PowerToughness).isEqualTo(new PowerToughness(1, 1));
    }

    @Test
    public void powerToughnessNegativeTest() {
        // Given
        String powerToughnessString = "-1/-1";

        // When
        PowerToughness PowerToughness = com.matag.cards.properties.PowerToughness.powerToughness(powerToughnessString);

        // Then
        assertThat(PowerToughness).isEqualTo(new PowerToughness(-1, -1));
    }

    @Test
    public void powerToughnessMixedTest() {
        // Given
        String powerToughnessString = "+2/-3";

        // When
        PowerToughness PowerToughness = com.matag.cards.properties.PowerToughness.powerToughness(powerToughnessString);

        // Then
        assertThat(PowerToughness).isEqualTo(new PowerToughness(2, -3));
    }
}