package com.aplikasi.challenge.service.impl;

import com.aplikasi.challenge.entity.Users;
import com.aplikasi.challenge.repository.UsersRepository;
import com.aplikasi.challenge.service.UserService;
import com.aplikasi.challenge.utils.PasswordValidatorUtil;
import com.aplikasi.challenge.utils.TemplateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserImpl implements UserService {
    @Autowired
    public UsersRepository usersRepository;
    @Autowired
    public TemplateResponse templateResponse;
    public PasswordValidatorUtil passwordValidatorUtil = new PasswordValidatorUtil();

    @Override
    public Map<Object, Object> save(Users request) {
        try {
            log.info("Save New User");
            if (request.getUsername().isEmpty()) return templateResponse.error("Username is required");
            if (usersRepository.getByUsername(request.getUsername()) > 0) return templateResponse.error("Username Used");

            if (request.getEmailAddress().isEmpty()) return templateResponse.error("Email Address is required");
            if (usersRepository.getByEmailAddress(request.getEmailAddress()) > 0) return templateResponse.error("Email Address is already Registered");

            if (request.getPassword().isEmpty()) return templateResponse.error("Password is required");
            if (!passwordValidatorUtil.validatePassword(request.getPassword())) {
                return templateResponse.error(passwordValidatorUtil.getMessage());
            }

            log.info("User Save Success");
            return templateResponse.success(usersRepository.save(request));
        } catch (Exception e) {
            log.error("Save User Error: " + e.getMessage());
            return templateResponse.error("Save User: " + e.getMessage());
        }
    }

    @Override
    public Map<Object, Object> update(Users request) {
        try {
            log.info("Update User");
            if (request.getId() == null) return templateResponse.error("Id is required");
            Optional<Users> checkDataDBUser = usersRepository.findById(request.getId());
            if (!checkDataDBUser.isPresent()) return templateResponse.error("Id is not Registered");
            if (!request.getUsername().isEmpty()) {
                if (usersRepository.getByUsername(request.getUsername()) > 0) return templateResponse.error("Username Exist, try other username");
                checkDataDBUser.get().setUsername(request.getUsername());
            }
            if (!request.getEmailAddress().isEmpty()) {
                if (usersRepository.getByEmailAddress(request.getEmailAddress()) > 0) return templateResponse.error("Email Address is already Registered");
                checkDataDBUser.get().setEmailAddress(request.getEmailAddress());
            }

            log.info("Update User Success");
            return templateResponse.success(usersRepository.save(checkDataDBUser.get()));
        } catch (Exception e) {
            log.error("Update User Error: " + e.getMessage());
            return templateResponse.error("Update User: " + e.getMessage());
        }
    }

    @Override
    public Map<Object, Object> delete(Users request) {
        try {
            log.info("Delete User");
            if (request.getId() == null) return templateResponse.error("Id is required");
            Optional<Users> checkDataDBUser = usersRepository.findById(request.getId());
            if (!checkDataDBUser.isPresent()) return templateResponse.error("User not Found");

            log.info("User Deleted");
            checkDataDBUser.get().setDeletedDate(new Date());
            return templateResponse.success(usersRepository.save(checkDataDBUser.get()));
        } catch (Exception e) {
            log.error("Delete User Error: " + e.getMessage());
            return templateResponse.error("Delete User : " + e.getMessage());
        }
    }

    @Override
    public Map<Object, Object> getById(UUID id) {
        try {
            log.info("Get User");
            if (id == null) return templateResponse.error("Id is required");
            Optional<Users> checkDataDBUser = usersRepository.findById(id);
            if (!checkDataDBUser.isPresent()) return templateResponse.error("User not Found");

            log.info("User Found");
            return templateResponse.success(checkDataDBUser.get());
        } catch (Exception e) {
            log.error("Get User Error: " + e.getMessage());
            return templateResponse.error("Get User: " + e.getMessage());
        }
    }
}
