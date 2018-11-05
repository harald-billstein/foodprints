package com.jayway.foodvoting.controller.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FoodPrintErrorController implements ErrorController {

  private static final Logger LOGGER = LoggerFactory.getLogger(FoodPrintErrorController.class);

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      int statusCode = Integer.valueOf(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        LOGGER.info("Serving a 404");
        return "404";
      }
    }
    LOGGER.info("Serving general error page");
    return "error";
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
