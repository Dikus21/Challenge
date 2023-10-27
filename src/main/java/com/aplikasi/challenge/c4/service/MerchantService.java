package com.aplikasi.challenge.c4.service;

import com.aplikasi.challenge.c4.entity.Merchant;

import java.util.Map;
import java.util.UUID;

public interface MerchantService {
    Map<Object, Object> save(Merchant merchant);

    Map<Object, Object> update(Merchant merchant);

    Map<Object, Object> delete(UUID uuid);

    Map<Object, Object> getById(UUID uuid);
}
