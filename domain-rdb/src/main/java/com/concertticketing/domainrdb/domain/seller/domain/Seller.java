package com.concertticketing.domainrdb.domain.seller.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.concertticketing.domainrdb.common.converter.SellerRoleConverter;
import com.concertticketing.domainrdb.common.entity.DateAuditable;
import com.concertticketing.domainrdb.domain.company.domain.Company;
import com.concertticketing.domainrdb.domain.seller.enums.SellerRole;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(name = "company_id", insertable = false, updatable = false)
    private Integer companyId;

    private String email;
    @Convert(converter = SellerRoleConverter.class)
    private SellerRole role;
    private String name;
    private String phoneNumber;

    @Getter(AccessLevel.NONE)
    private LocalDateTime deletedAt;

    public Seller(Company company, String email, SellerRole role, String name, String phoneNumber) {
        validate(company, email, role, name, phoneNumber);

        this.company = company;
        this.companyId = company.getId();
        this.email = email;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    private void validate(Company company, String email, SellerRole role, String name, String phoneNumber) {
        Objects.requireNonNull(company, "company cannot be null");
        Objects.requireNonNull(email, "email cannot be null");
        Objects.requireNonNull(role, "role cannot be null");
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
    }
}
