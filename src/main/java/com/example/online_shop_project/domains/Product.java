package com.example.online_shop_project.domains;

import lombok.*;
import com.example.online_shop_project.enums.Currency;
import com.example.online_shop_project.enums.Gender;
import com.example.online_shop_project.enums.ProductType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String name;
    private String price;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    @OneToOne
    private Uploads cover;

}

