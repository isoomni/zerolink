package com.example.demo.src.comunity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommunityProvider {

    private final CommunityDao communityDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommunityProvider(CommunityDao communityDao) {
        this.communityDao = communityDao;
    }
}
