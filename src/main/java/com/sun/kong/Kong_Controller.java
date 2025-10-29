package com.sun.kong;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.kong.config.SecurityConfig;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class Kong_Controller {
    private final SecurityConfig security_Config;

    Kong_Controller(SecurityConfig security_Config) {
        this.security_Config = security_Config;
    }
}