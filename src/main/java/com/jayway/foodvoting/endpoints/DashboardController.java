package com.jayway.foodvoting.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

  private static final Logger LOGGER = LoggerFactory.getLogger(VoteEndpoint.class);

  @RequestMapping({"/", "/index"})
  public ModelAndView getDashBoard(ModelAndView modelAndView) {
    LOGGER.info("INDEX ACCESSED!");

    //ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index.html");
    return modelAndView;
  }

}
