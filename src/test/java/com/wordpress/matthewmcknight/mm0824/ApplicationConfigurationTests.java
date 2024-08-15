package com.wordpress.matthewmcknight.mm0824;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ApplicationConfiguration.class})
@EnableAutoConfiguration
@ActiveProfiles("test")
public class ApplicationConfigurationTests {

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Test
    @DisplayName("Configuration loads")
    public void loadTest() {
        assertNotNull(applicationConfiguration);
    }

    @Test
    @DisplayName("Independence Day falls on weekday")
    public void independenceDayNormalTest() throws Exception {
        assertEquals(4, applicationConfiguration.getHolidays().getFirst().of(2024).getDayOfMonth());
        assertNull(applicationConfiguration.getHolidays().get(1).of(2024));
        assertNull(applicationConfiguration.getHolidays().get(2).of(2024));
    }

    @Test
    @DisplayName("Independence Day falls on Saturday")
    public void independenceDaySaturdayTest() throws Exception {
        assertEquals(3, applicationConfiguration.getHolidays().get(1).of(2026).getDayOfMonth());
        assertNull(applicationConfiguration.getHolidays().getFirst().of(2026));
        assertNull(applicationConfiguration.getHolidays().get(2).of(2026));
    }

    @Test
    @DisplayName("Independence Day falls on Sunday")
    public void independenceDaySundayTest() throws Exception {
        assertEquals(5, applicationConfiguration.getHolidays().get(2).of(2021).getDayOfMonth());
        assertNull(applicationConfiguration.getHolidays().getFirst().of(2021));
        assertNull(applicationConfiguration.getHolidays().get(1).of(2021));
    }

    @Test
    @DisplayName("Labor Day on first Monday 2024")
    public void laborDay2024() throws Exception {
        assertEquals(2, applicationConfiguration.getHolidays().get(3).of(2024).getDayOfMonth());
    }

    @Test
    @DisplayName("Labor Day on first Monday 2023")
    public void laborDay2023() throws Exception {
        assertEquals(4, applicationConfiguration.getHolidays().get(3).of(2023).getDayOfMonth());
    }
}
