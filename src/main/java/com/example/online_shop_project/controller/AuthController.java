package com.example.online_shop_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.online_shop_project.configs.security.UserDetails;
import com.example.online_shop_project.domains.TrolleyProduct;
import com.example.online_shop_project.dto.UserCreateDTO;
import com.example.online_shop_project.services.AuthService;
import com.example.online_shop_project.services.TrolleyService;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TrolleyService trolleyService;
    private final AuthService service;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }


    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute ( "dto", new UserCreateDTO () );
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSave(@Valid @ModelAttribute("dto") UserCreateDTO dto
            , BindingResult result) {
        if (result.hasErrors ()) {
            return "auth/register";
        }
        service.generate ( dto );
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }


    @GetMapping(value = "/history")
    public String historyPage(@RequestParam Optional<String> search,
                              @RequestParam(name = "page") Optional<Integer> page,
                              @RequestParam(name = "limit") Optional<Integer> limit,
                              Model model
            , @AuthenticationPrincipal UserDetails userDetails) {
        String searchQuery = search.orElse ( "" );
        Page<TrolleyProduct> products = trolleyService.userProductAll ( searchQuery, page, limit, userDetails.getUser ().getId () );

        model.addAttribute ( "search", searchQuery );
        model.addAttribute ( "page", products );
        model.addAttribute ( "pageNumbers", IntStream.range ( 0, products.getTotalPages () ).toArray () );

        return "auth/auth_history";
    }

}