package com.example.online_shop_project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.online_shop_project.configs.security.UserDetails;
import com.example.online_shop_project.domains.AuthUser;
import com.example.online_shop_project.dto.UserCreateDTO;
import com.example.online_shop_project.repository.AuthRepository;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return new UserDetails(authUser);
    }

    public void generate(UserCreateDTO dto) {

        AuthUser user = AuthUser
                .builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .repeatPassword(passwordEncoder.encode(dto.getRepeatPassword()))
                .active(true)
                .build();
        authRepository.save(user);
    }
}
