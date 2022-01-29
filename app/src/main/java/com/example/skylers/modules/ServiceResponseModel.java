package com.example.skylers.modules;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Lombok helps generate Geters and setters
 * This shall be used at viewModel to pass response messages from service call
 */

@Data
@AllArgsConstructor
public class ServiceResponseModel {

    public String responseCode;
    public String responseMessage;
    public String errorMessage;
}
