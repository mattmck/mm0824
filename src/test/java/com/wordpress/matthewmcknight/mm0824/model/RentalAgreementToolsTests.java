package com.wordpress.matthewmcknight.mm0824.model;

import com.wordpress.matthewmcknight.mm0824.ApplicationConfiguration;
import com.wordpress.matthewmcknight.mm0824.RentalAgreementTools;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ApplicationConfiguration.class})
@EnableAutoConfiguration
@ActiveProfiles("test")
public class RentalAgreementToolsTests {

    @Autowired
    ApplicationConfiguration applicationConfiguration;

    @Test
    @DisplayName("No exceptions for holidays, weekdays, or weekends")
    void allChargeableDaysTest() throws Exception {
        assertEquals(5,
                RentalAgreementTools.calculateChargeableDays(applicationConfiguration.getHolidays(),
                    ToolTypeInfo.builder().holidayCharge(true).weekdayCharge(true).weekendCharge(true).build(),
                    LocalDate.of(2024, 7, 2),
            5));
    }

    @Test
    @DisplayName("Exceptions for holidays")
    void unchargeableHolidaysTest() throws Exception {
        assertEquals(4,
                RentalAgreementTools.calculateChargeableDays(applicationConfiguration.getHolidays(),
                        ToolTypeInfo.builder().holidayCharge(false).weekdayCharge(true).weekendCharge(true).build(),
                        LocalDate.of(2024, 7, 2),
                        5));
    }

    @Test
    @DisplayName("Exceptions for weekends")
    void unchargeableWeekendsTest() throws Exception {
        assertEquals(4,
                RentalAgreementTools.calculateChargeableDays(applicationConfiguration.getHolidays(),
                        ToolTypeInfo.builder().holidayCharge(true).weekdayCharge(true).weekendCharge(false).build(),
                        LocalDate.of(2024, 7, 2),
                        5));
    }

    @Test
    @DisplayName("Exceptions for weekdays")
    void unchargeableWeekdaysTest() throws Exception {
        assertEquals(1,
                RentalAgreementTools.calculateChargeableDays(applicationConfiguration.getHolidays(),
                        ToolTypeInfo.builder().holidayCharge(true).weekdayCharge(false).weekendCharge(true).build(),
                        LocalDate.of(2024, 7, 2),
                        5));
    }

    @Test
    @DisplayName("Sample rental agreements")
    void rentalAgreementsTest() {
        assertDoesNotThrow(() -> testCheckout("JAKR", LocalDate.of(2015, 9, 13), 5, 101, -0.12));
        assertDoesNotThrow(() -> testCheckout("LADW", LocalDate.of(2020, 7, 2), 3, 10, 3.58));
        assertDoesNotThrow(() -> testCheckout("CHNS", LocalDate.of(2015, 7, 2), 5, 25, 3.35));
        assertDoesNotThrow(() -> testCheckout("JAKD", LocalDate.of(2015, 9, 3), 6, 0, 11.96));
        assertDoesNotThrow(() -> testCheckout("JAKR", LocalDate.of(2015, 7, 2), 9, 0, 17.94));
        assertDoesNotThrow(() -> testCheckout("JAKR", LocalDate.of(2020, 7, 2), 4, 50, 1.49));
    }

    private void testCheckout(String toolCode, LocalDate date, int rentalDays, int discount, double finalCharge) throws Exception {
        var tool = RentalAgreementTools.lookupTool(applicationConfiguration, toolCode);

        var agreement = RentalAgreementTools.calculateRentalAgreement(
                applicationConfiguration.getHolidays(),
                tool,
                RentalAgreementTools.lookupToolType(applicationConfiguration, tool),
                date,
                rentalDays,
                discount);
        assertEquals(finalCharge, agreement.getFinalCharge(), .001);
    }
}