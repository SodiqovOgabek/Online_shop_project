package com.example.online_shop_project.validator;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.online_shop_project.domains.AuthUser;
import com.example.online_shop_project.repository.AuthRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private AuthRepository repository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<AuthUser> byUsername = repository.findByUsername(value);
        return byUsername.isEmpty();
    }

}
