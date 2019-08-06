package ru.cbr;

import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {
        from("file:F:\\temp\\demo\\prepare?noop=true").to("file:F:\\temp\\demo\\work");
    }

}