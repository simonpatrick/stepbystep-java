package com.hedwig.dropwizardssamples.simple.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by patrick on 15/6/7.
 *
 * @version $Id$
 */


public class HelloWorldConfiguration  extends Configuration {
    @JsonProperty
    @NotEmpty
    private String template;
    @JsonProperty
    @NotEmpty
    private String defaultName="hello world";

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }
}
