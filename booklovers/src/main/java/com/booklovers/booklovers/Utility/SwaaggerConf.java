package com.booklovers.booklovers.Utility;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(
        title = "book lovers APIs",
        version = "v1",
        description = "API documentation for VivideKode LTD"
    ),
    security = {
        @SecurityRequirement(name = "basicAuth")
    }
)
@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "Bearer",
    in = SecuritySchemeIn.HEADER,
    description = "Enter username: Admin, password: Admin"
)

public class SwaaggerConf {}
    

