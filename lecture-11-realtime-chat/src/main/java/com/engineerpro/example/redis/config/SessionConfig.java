package com.engineerpro.example.redis.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableRedisHttpSession(redisNamespace = "engineerpro:app")
public class SessionConfig implements BeanClassLoaderAware {

  private ClassLoader loader;

  /**
   * Customized {@link ObjectMapper} to add mix-in for class that doesn't have
   * default
   * constructors
   * 
   * @return the {@link ObjectMapper} to use
   */
  private ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
    return mapper;
  }

  /*
   * @see
   * org.springframework.beans.factory.BeanClassLoaderAware#setBeanClassLoader(
   * java.lang
   * .ClassLoader)
   */
  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    this.loader = classLoader;
  }

}
