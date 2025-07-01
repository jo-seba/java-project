package com.concertticketing.sellerapi.apis.sellers.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.concertticketing.sellerapi.common.converter.jpa.SellerRoleConverter;
import com.concertticketing.sellerapi.common.entity.DateAuditable;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "seller")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE seller SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Seller extends DateAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer companyId;

    private String email;
    @Convert(converter = SellerRoleConverter.class)
    private SellerRole role;
    private String name;
    private String phoneNumber;

    @Getter(AccessLevel.NONE)
    private LocalDateTime deletedAt;

    public Seller(Integer companyId, String email, SellerRole role, String name, String phoneNumber) {
        validate(companyId, email, role, name, phoneNumber);

        this.companyId = companyId;
        this.email = email;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    private void validate(Integer companyId, String email, SellerRole role, String name, String phoneNumber) {
        Objects.requireNonNull(companyId, "companyId cannot be null");
        Objects.requireNonNull(email, "email cannot be null");
        Objects.requireNonNull(role, "role cannot be null");
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
    }
}
