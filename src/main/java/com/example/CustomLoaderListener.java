package com.example;

import org.jboss.resteasy.plugins.spring.SpringContextLoaderSupport;
import org.jboss.resteasy.plugins.spring.i18n.Messages;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class CustomLoaderListener extends ContextLoaderListener {
    private final SpringContextLoaderSupport springContextLoaderSupport = new SpringContextLoaderSupport();

    public CustomLoaderListener() {
    }

    public CustomLoaderListener(WebApplicationContext context) {
        super(context);
    }

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        boolean scanProviders = false;
        boolean scanResources = false;

        String sProviders = event.getServletContext().getInitParameter("resteasy.scan.providers");
        if (sProviders != null)
        {
            scanProviders = Boolean.valueOf(sProviders.trim());
        }
        String scanAll = event.getServletContext().getInitParameter("resteasy.scan");
        if (scanAll != null)
        {
            boolean tmp = Boolean.valueOf(scanAll.trim());
            scanProviders = tmp || scanProviders;
            scanResources = tmp;
        }
        String sResources = event.getServletContext().getInitParameter("resteasy.scan.resources");
        if (sResources != null)
        {
            scanResources = Boolean.valueOf(sResources.trim());
        }
        if (scanProviders || scanResources)
        {
            throw new RuntimeException(Messages.MESSAGES.cannotUseScanParameters());
        }

        super.contextInitialized(event);
    }

    @Override
    protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext configurableWebApplicationContext){
        super.customizeContext(servletContext, configurableWebApplicationContext);
        this.springContextLoaderSupport.customizeContext(servletContext, configurableWebApplicationContext);
    }
}
