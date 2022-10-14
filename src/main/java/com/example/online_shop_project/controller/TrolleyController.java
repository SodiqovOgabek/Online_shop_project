package com.example.online_shop_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.online_shop_project.configs.security.UserDetails;
import com.example.online_shop_project.domains.TrolleyProduct;
import com.example.online_shop_project.dto.TrolleyProductCreateDTO;
import com.example.online_shop_project.services.ProductService;
import com.example.online_shop_project.services.TrolleyService;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class TrolleyController {
    private final ProductService service;
    private final TrolleyService trolleyService;





    @GetMapping("/trolley")
    public String trolleyPage(@AuthenticationPrincipal UserDetails userDetails,Model model) {
        List<TrolleyProduct> productList = trolleyService.findById(userDetails.getUser().getId());
        Integer summa=0;
        for (TrolleyProduct trolleyProduct : productList) {
            summa+=trolleyProduct.getTheNumber()*Integer.parseInt(trolleyProduct.getProduct().getPrice());
        }
        model.addAttribute("summa",summa);
        model.addAttribute("products",productList);
        return "trolley/trolley";
    }
    @PostMapping("/trolley")
    public String trolleyAdd(@AuthenticationPrincipal UserDetails  userDetails){
        trolleyService.update(userDetails);
        return "redirect:/trolley";
    }




    @RequestMapping(value = "trolley/delete/{id}", method = RequestMethod.GET)
    public String deletePage(@PathVariable Long id, Model model) {
        model.addAttribute("trolley", trolleyService.get(id));
        return "trolley/trolley_delete";
    }

    @RequestMapping(value = "trolley/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id,@ModelAttribute TrolleyProductCreateDTO dto) throws InterruptedException {
        trolleyService.delete(id,dto.getTheNumber());
//        TimeUnit.SECONDS.sleep();
        return "redirect:/trolley";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/history")
    public String historyPage(@RequestParam Optional<String> search,
                              @RequestParam(name = "page") Optional<Integer> page,
                              @RequestParam(name = "limit") Optional<Integer> limit,
                              Model model){
        String searchQuery = search.orElse("");
        Page<TrolleyProduct> products = trolleyService.productAll(searchQuery,page, limit);

        model.addAttribute("search", searchQuery);
        model.addAttribute("page", products);
        model.addAttribute("pageNumbers", IntStream.range(0, products.getTotalPages()).toArray());

        return "admin/history";
    }




}
