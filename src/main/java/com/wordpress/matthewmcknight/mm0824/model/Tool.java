package com.wordpress.matthewmcknight.mm0824.model;

import lombok.Data;

/**
 * Represents a tool deserialized from configuration
 */
@Data
public class Tool {

    /**
     * Tool code
     */
    private String code;

    /**
     * Tool type
     */
    private ToolType type;

    /**
     * Tool brand
     */
    private String brand;
}

