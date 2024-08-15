package com.wordpress.matthewmcknight.mm0824;

import com.wordpress.matthewmcknight.mm0824.model.RentalAgreement;
import com.wordpress.matthewmcknight.mm0824.model.Tool;
import com.wordpress.matthewmcknight.mm0824.model.ToolTypeInfo;
import org.itsallcode.holidays.calculator.logic.variants.Holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class RentalAgreementTools {

    public static RentalAgreement calculateRentalAgreement(List<Holiday> holidays,
                                                           Tool tool,
                                                           ToolTypeInfo info,
                                                           LocalDate checkoutDate,
                                                           int rentalDays,
                                                           int discount) {
        return RentalAgreement.builder()
                    .dailyRentalCharge(info.getDailyCharge())
                    .chargeableDays(calculateChargeableDays(holidays, info, checkoutDate, rentalDays))
                    .checkoutDay(checkoutDate)
                    .discountPercent(discount)
                    .tool(tool)
                    .toolTypeInfo(info)
                    .rentalDays(rentalDays)
                .build();
    }

    public static int calculateChargeableDays(List<Holiday> holidays, ToolTypeInfo toolTypeInfo, LocalDate checkoutDate, int rentalDays) {
        var chargableDays = rentalDays;
        // go through each of the days for the rental
        for(int i = 0; i < rentalDays; i++) {
            var charge  = true;
            var date = checkoutDate.plusDays(i);

            // if the rental day is a Saturday or Sunday and the tool disregards weekends
            if((date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) && !toolTypeInfo.isWeekendCharge()) {
                charge = false;
            }

            // go through each of the holidays
            for(Holiday holiday : holidays) {
                var holidayDate = holiday.of(date.getYear());
                // if the rental day falls on a holiday and the tool disregards holidays
                if(holidayDate != null && holidayDate.equals(date) && !toolTypeInfo.isHolidayCharge()) {
                    charge = false;
                }
            }

            if(date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY && !toolTypeInfo.isWeekdayCharge()) {
                charge = false;
            }

            // if it isn't a charge-able day, subtract
            chargableDays = chargableDays - (charge ? 0 : 1);
        }
        return chargableDays;
    }

    public static Tool lookupTool(ApplicationConfiguration configuration, String toolCode) throws Exception {
        List<Tool> tools = configuration.getTools().stream().filter(t -> t.getCode().equals(toolCode)).toList();
        if(tools.isEmpty()) {
            throw new Exception(String.format("Tool with code %s not found", toolCode));
        }
        return tools.getFirst();
    }

    public static ToolTypeInfo lookupToolType(ApplicationConfiguration configuration, Tool tool) throws Exception {
        List<ToolTypeInfo> infos = configuration.getToolInfo().stream().filter(i -> i.getToolType().equals(tool.getType())).toList();
        if(infos.isEmpty()) {
            throw new Exception(String.format("No information found for tool with type %s", tool.getType()));
        }
       return infos.getFirst();
    }
}
