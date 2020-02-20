package com.carlos.camelspringsimple.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SimpleCamelRoute extends RouteBuilder {

    @Autowired
    Environment env;

    @Override
    public void configure() throws Exception {
        from("timer:hello?period=10s")
                .log("Timer invoke and the body ${body}")
                .pollEnrich("file:data/input?delete=true&readLock=none")
                .to("file:data/output");
        from("{{startRoute}}")
                .log("Timer invoke and the body using dynamic values " + env.getProperty("message"))
                .pollEnrich("{{fromRoute}}")
                .to("{{toRoute1}}");

    }
}
