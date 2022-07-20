package com.example.demo.src.comunity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/community")
public class CommunityController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CommunityProvider communityProvider;
    @Autowired
    private final CommunityService communityService;

    public CommunityController(CommunityProvider communityProvider, CommunityService communityService) {
        this.communityProvider = communityProvider;
        this.communityService = communityService;
    }
}
