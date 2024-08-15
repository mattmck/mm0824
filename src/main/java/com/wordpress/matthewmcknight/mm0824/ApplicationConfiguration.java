package com.wordpress.matthewmcknight.mm0824;

import com.wordpress.matthewmcknight.mm0824.model.Tool;
import com.wordpress.matthewmcknight.mm0824.model.ToolTypeInfo;
import lombok.Data;
import org.itsallcode.holidays.calculator.logic.parser.HolidaysFileParser;
import org.itsallcode.holidays.calculator.logic.variants.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;

/**
 * Application configuration from application.yml
 */
@Data
@ConfigurationProperties("app")
public class ApplicationConfiguration implements IApplicationConfiguration {

    private final List<Tool> tools;

    /**
     * List of tool type infos
     */
    private final List<ToolTypeInfo> toolInfo;

    @Autowired
    private final ResourceLoader resourceLoader;

    /**
     * Gets the parsed list of holidays
     * @return              list of holidays
     * @throws IOException  resource list of holidays not available
     */
    public List<Holiday> getHolidays() throws Exception {
        return new HolidaysFileParser(getHolidaysResource().getURI().toString()).parse(getHolidaysResource().getInputStream());
    }

    protected Resource getHolidaysResource() {
        return resourceLoader.getResource(
                "classpath:holidays.cfg");
    }
}

