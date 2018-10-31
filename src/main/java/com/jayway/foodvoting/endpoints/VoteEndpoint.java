package com.jayway.foodvoting.endpoints;

import com.jayway.foodvoting.VoteHandler;
import com.jayway.foodvoting.model.VoteRequest;
import com.jayway.foodvoting.model.VoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class VoteEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(VoteEndpoint.class);

  private VoteHandler voteHandler;

  public VoteEndpoint(VoteHandler voteHandler) {
    this.voteHandler = voteHandler;
  }

  @PostMapping(value = "/votes/")
  public ResponseEntity<VoteResponse> vote(@RequestBody VoteRequest vote) {
    LOGGER.info("ENDPOINT : VOTE : " + vote.getVote());
    return voteHandler.registerVote(vote.getVote().toUpperCase());
  }
}
