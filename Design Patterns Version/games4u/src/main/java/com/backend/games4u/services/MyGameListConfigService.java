package com.backend.games4u.services;

import com.backend.games4u.models.MyGameListConfig;
import com.backend.games4u.models.User;
import com.backend.games4u.repository.MyGameListConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyGameListConfigService {
    @Autowired
    private MyGameListConfigRepository myGameListConfigRepository;

    public void initializeMyGameListConfigIfNotExists(User user) {
        if (user.getMyGameListConfig() == null) {
            MyGameListConfig myGameListConfig = new MyGameListConfig();
            myGameListConfig.setUser(user);
            myGameListConfig.setBackgroundColor("#FFFFFF");
            myGameListConfig.setTextColor("#840428");
            myGameListConfigRepository.save(myGameListConfig);
        }
    }
}

