package com.edu.tm4j_testng_integration;

import com.edu.tm4j_testng_integration.listeners.ConnectorListener;
import com.edu.tm4j_testng_integration.listeners.IntegrationListener;
import org.testng.annotations.Listeners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Listeners({ConnectorListener.class, IntegrationListener.class})
public class BaseTest {
    /*
     * Define the custom TestData tag for storing the
     * TM4J test key for all inherited tests.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface TestData {

        String key() default "";
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkOTU2ODBlYS0xYTg5LTMyNzEtYTRhZC1lMzIwNDQ2ZmRmOGMiLCJjb250ZXh0Ijp7ImJhc2VVcmwiOiJodHRwczpcL1wvYmx1ZXJvYm90Y21wLmF0bGFzc2lhbi5uZXQiLCJ1c2VyIjp7ImFjY291bnRJZCI6IjVkMmU0NzkwNzlkNGVlMGM4YjFkMjgzOCJ9fSwiaXNzIjoiY29tLmthbm9haC50ZXN0LW1hbmFnZXIiLCJleHAiOjE2MzUzNzgwMjAsImlhdCI6MTYwMzg0MjAyMH0.VkEEBObvGKV7vby2XcRc52fzfJdf19QrtO1DtxFvoQU
    }
}
