package com.example.online_shop_project.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.online_shop_project.domains.Product;
import com.example.online_shop_project.domains.Uploads;
import com.example.online_shop_project.dto.ProductCreateDTO;
import com.example.online_shop_project.enums.Currency;
import com.example.online_shop_project.enums.Gender;
import com.example.online_shop_project.enums.ProductType;
import com.example.online_shop_project.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository rep;
    private final UploadsService service;

    public void save(ProductCreateDTO dto) {
        Uploads cover= service.upload(dto.getCover());

        Product product = Product
                .builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .type(ProductType.findByName(dto.getType()))
                .gender(Gender.findByName(dto.getGender()))
                .currency(Currency.findByName(dto.getCurrency()))
                .cover(cover)
                .build();
        rep.save(product);
    }

    public List<Product> findAll() {
        return rep.findAll();
    }

    public void delete(Long id) {
     rep.deleteById(id);
    }

    public Optional<Product> get(Long id) {
        Optional<Product> product = rep.findById(id);
        return product;
    }

//    public List<Product> swatchAll(ProductType watch) {
//        int page=0;
//        int size=10;
//        Pageable pageable=PageRequest.of(page,size,Sort.by("id").descending());
//        return rep.swatchAll(watch);
//    }

    public List<Product> shoesAll(ProductType shoes) {
        int page=0;
        int size=4;
        Pageable pageable=PageRequest.of(page,size,Sort.by("id").descending());
        return rep.shoesAll(shoes,pageable);
    }

    public Page<Product> productAll(@NonNull String searchQuery, Optional<Integer> pageOptional, Optional<Integer> limitOptional) {
        int page = pageOptional.orElse(0);
        int size = limitOptional.orElse(8);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return rep.findAll(searchQuery.toLowerCase(),pageable);
    }

    public Optional<Product> finById(Long id) {
        return rep.findById(id);
    }

    public void update(Long id, ProductCreateDTO dto) {
        Uploads upload = service.upload(dto.getCover());
        Product product= Product
                .builder()
                .id(id)
                .name(dto.getName())
                .currency(Currency.findByName(dto.getCurrency()))
                .type(ProductType.findByName(dto.getType()))
                .gender(Gender.findByName(dto.getGender()))
                .price(dto.getPrice())
                .cover(upload)
                .build();
        rep.save(product);
    }
}
