package com.Leapcode.LeapcodeShoppingCart.models;

import com.Leapcode.LeapcodeShoppingCart.models.data.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
