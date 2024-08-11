package com.engineerpro.rest.example.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.engineerpro.rest.example.common.AuthenticationFilter;
import com.engineerpro.rest.example.model.App;

import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseController {

  @Autowired
  protected HttpServletRequest request;

  protected App getAuthenticatedApp() {
    return (App) request.getAttribute(AuthenticationFilter.APP_ATTRIBUTE);
  }
}
