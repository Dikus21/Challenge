package com.aplikasi.challenge.service.oauth;

import com.aplikasi.challenge.entity.oauth.User;
import com.aplikasi.challenge.request.LoginModel;
import com.aplikasi.challenge.request.RegisterModel;

import java.util.Map;

public interface UserService {
    Map registerManual(RegisterModel objModel) ;
    Map registerMerchant (User request);
    Map registerByGoogle(RegisterModel objModel);
    public Map login(LoginModel objLogin);
    Map<Object, Object> getById(Long id);
    Map<Object, Object> delete(User request);
    Map<Object, Object> update(User request);
}

