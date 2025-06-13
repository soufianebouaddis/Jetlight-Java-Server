package org.os.controller;

import org.os.annotations.Controller;

@Controller
public class HealthController {
    public String checkHealth(){
        return String.format(
                "{ \"status\": \"200\",\"message\": \"server is up\",\"data\": {}}"
        );
    }
}
