package com.melik.user.service.user.port;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

public interface JsonPort<T> {

    String serialize(T object);

    T deserialize(String jsonStr);
}
