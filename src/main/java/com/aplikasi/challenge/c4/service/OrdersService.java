package com.aplikasi.challenge.c4.service;

import com.aplikasi.challenge.c4.entity.Orders;

import java.util.Map;
import java.util.UUID;

public interface OrdersService {
    Map<Object, Object> save(Orders orders);

    Map<Object, Object> update(Orders orders);

    Map<Object, Object> delete(UUID uuid);

    Map<Object, Object> getById(UUID uuid);
}
