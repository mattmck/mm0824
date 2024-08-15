package com.wordpress.matthewmcknight.mm0824.model;

import io.jstach.jstache.JStacheLambda;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Provides formatted text for printing rental agreements
 * TODO: move away from mustache to freemarker as it allows date and currency formatting inline
 */
public interface RentalAgreementFormattingSupport {

    DecimalFormat currencyFormatter = new DecimalFormat("$###,###,###.##");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @JStacheLambda
    default String checkoutDayFormatted(RentalAgreement agreement) {
        return dateFormatter.format(agreement.getCheckoutDay());
    }

    @JStacheLambda
    default String returnDateFormatted(RentalAgreement agreement) {
        return dateFormatter.format(agreement.getReturnDate());
    }

    @JStacheLambda
    default String dailyRentalChargeFormatted(RentalAgreement agreement) {
        return currencyFormatter.format(agreement.getDailyRentalCharge());
    }

    @JStacheLambda
    default String preDiscountChargeFormatted(RentalAgreement agreement) {
        return currencyFormatter.format(agreement.getPreDiscountCharge());
    }

    @JStacheLambda
    default String discountAmountFormatted(RentalAgreement agreement) {
        return currencyFormatter.format(agreement.getDiscountAmount());
    }


    @JStacheLambda
    default String finalChargeFormatted(RentalAgreement agreement) {
        return currencyFormatter.format(agreement.getFinalCharge());
    }
}
