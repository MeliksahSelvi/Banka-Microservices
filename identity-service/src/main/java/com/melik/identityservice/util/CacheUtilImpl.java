package com.melik.identityservice.util;

import com.google.gson.Gson;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.melik.identityservice.dto.JwtToken;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Service
public class CacheUtilImpl implements CacheUtil {

    @Value("${application-user}")
    private String applicationUser;

    private HazelcastInstance hazelcastInstance;

    private final String CUSTOMER_TOKEN_MAP = "CUSTOMER_TOKEN";


    @PostConstruct
    public void init() {

        Config config = new Config();
        config.setClusterName(applicationUser);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    public JwtToken readTokenMap(String token) {
        IMap<String, String> customerTokenMap = getCustomerTokenMap();

        String tokenJson = customerTokenMap.get(token);
        return new Gson().fromJson(tokenJson, JwtToken.class);
    }

    public void writeTokenMap(JwtToken jwtToken) {
        IMap<String, String> tokenMap = getCustomerTokenMap();
        String token = jwtToken.getToken();
        String jwtTokenJson = new Gson().toJson(jwtToken);
        tokenMap.putIfAbsent(token, jwtTokenJson);
    }

    public void deleteFromTokenMap(String token) {
        IMap<String, String> customerTokenMap = getCustomerTokenMap();

        customerTokenMap.delete(customerTokenMap);
    }

    private IMap<String, String> getCustomerTokenMap() {
        IMap<String, String> customerTokenMap = hazelcastInstance.getMap(CUSTOMER_TOKEN_MAP);
        return customerTokenMap;
    }
}
