package com.Demo.entity.OmniCarSystemAccess;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AvanserOmniRewardsPrograms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean vf17;
    private boolean e2e;
    private boolean webPromotions;

}
