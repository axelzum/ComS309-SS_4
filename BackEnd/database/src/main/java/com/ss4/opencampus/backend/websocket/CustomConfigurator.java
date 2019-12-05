package com.ss4.opencampus.backend.websocket;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

/**
 * Class was implemented following tutorial on Piazza
 */
public class CustomConfigurator extends ServerEndpointRegistration.Configurator implements ApplicationContextAware
{
  private static volatile BeanFactory context;

  @Override
  public <T> T getEndpointInstance(Class<T> endpoint)
  {
    return context.getBean(endpoint);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
  {
    CustomConfigurator.context = applicationContext;
  }


}
