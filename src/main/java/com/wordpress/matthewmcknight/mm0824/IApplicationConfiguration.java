package com.wordpress.matthewmcknight.mm0824;

import com.wordpress.matthewmcknight.mm0824.model.Tool;
import com.wordpress.matthewmcknight.mm0824.model.ToolTypeInfo;
import org.itsallcode.holidays.calculator.logic.variants.Holiday;

import java.util.List;

/**
 * Required methods from any application configuration source
 */
public interface IApplicationConfiguration {

    /**
     * List of tools
     * @return  configured tools in inventory
     */
    List<Tool> getTools();

    /**
     * List of tool infos
     * @return  configured tool infos
     */
    List<ToolTypeInfo> getToolInfo();

    /**
     * List of holidays
     * @return  list of holidays
     */
    List<Holiday> getHolidays() throws Exception;
}
