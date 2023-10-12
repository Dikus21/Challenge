package com.aplikasi.challenge.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class MenuItem {
    private String name;
    private Integer price;

    public Optional<String> getNameOptional() {
        return Optional.ofNullable(name);
    }

    public Optional<Integer> getPriceOptional() {
        return Optional.ofNullable(price);
    }
}
