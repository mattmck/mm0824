package com.wordpress.matthewmcknight.mm0824.model;

import io.jstach.jstache.JStache;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents a rental agreement and all properties associated
 */
@Data
@JStache(path = "rentalAgreement.mustache")
public class RentalAgreement implements RentalAgreementFormattingSupport {

    /**
     * The tool being rented
     */
    private Tool tool;

    /**
     * The type of tool being rented
     */
    private ToolTypeInfo toolTypeInfo;

    /**
     * How long the rental is in days
     */
    private int rentalDays;

    /**
     * Number of charge-able days
     */
    private int chargeableDays;

    /**
     * Date tool is being checked out
     */
    private LocalDate checkoutDay;

    /**
     * Date tool needs to be returned
     */
    private LocalDate returnDate;

    /**
     * Daily charge for renting the tool
     */
    private double dailyRentalCharge;

    /**
     * Subtotal of rental
     */
    private double preDiscountCharge;

    /**
     * Discount percentage
     */
    private int discountPercent;

    /**
     * Discount amount
     */
    private double discountAmount;

    /**
     * Final amount charged to customer
     */
    private double finalCharge;

    /**
     * Builds a rental agreement and sets calculated fields
     * @param tool              tool being rented
     * @param toolTypeInfo      type of tool being rented
     * @param rentalDays        how long the rental dis
     * @param checkoutDay       when the tool is to be checked out
     * @param dailyRentalCharge daily charge for the tool
     * @param chargeableDays    charge-able days
     * @param discountPercent   discount percentage
     */
    @Builder
    public RentalAgreement(Tool tool, ToolTypeInfo toolTypeInfo, int rentalDays, LocalDate checkoutDay, double dailyRentalCharge, int chargeableDays, int discountPercent) {
        this.tool = tool;
        this.toolTypeInfo = toolTypeInfo;
        this.rentalDays = rentalDays;
        this.chargeableDays = chargeableDays;
        this.checkoutDay = checkoutDay;
        this.dailyRentalCharge = Math.round(dailyRentalCharge * 100.0) / 100.0;
        this.discountPercent = discountPercent;

        this.returnDate = checkoutDay.plusDays(rentalDays-1);
        this.preDiscountCharge = Math.round(dailyRentalCharge * chargeableDays * 100.0) / 100.0;
        this.discountAmount = Math.round(preDiscountCharge * (discountPercent / 100.0) * 100.0) / 100.0;
        this.finalCharge = this.preDiscountCharge - this.discountAmount;
    }
}

