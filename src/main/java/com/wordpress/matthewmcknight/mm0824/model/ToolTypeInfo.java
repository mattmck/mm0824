package com.wordpress.matthewmcknight.mm0824.model;

import lombok.Builder;
import lombok.Data;

/**
 * Required information for Tool types
 */
@Data
@Builder
public class ToolTypeInfo {

    /**
     * Tool type
     */
    private ToolType toolType;

    /**
     * Charge daily
     */
    private float dailyCharge;

    /**
     * Charge weekdays for this tool type
     */
    private boolean weekdayCharge;

    /**
     * Charge weekends for this tool type
     */
    private boolean weekendCharge;

    /**
     * Charge holidays for this tool type
     */
    private boolean holidayCharge;
}
