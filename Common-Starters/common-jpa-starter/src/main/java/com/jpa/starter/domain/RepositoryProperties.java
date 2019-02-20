package com.jpa.starter.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("repository.scan")
public class RepositoryProperties {

    private String base;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
