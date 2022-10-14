package com.example.online_shop_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.online_shop_project.domains.Uploads;

public interface UploadsRepository extends JpaRepository<Uploads,Long> {

}
