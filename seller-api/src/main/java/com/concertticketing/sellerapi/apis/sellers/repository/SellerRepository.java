package com.concertticketing.sellerapi.apis.sellers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.concertticketing.sellerapi.apis.sellers.domain.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer>, SellerRepositoryCustom {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Seller s "
        + "SET s.role = :role, s.name = :name, s.phoneNumber = :phoneNumber, s.updatedAt = CURRENT_TIMESTAMP "
        + "WHERE s.id = :id and s.companyId = :companyId")
    int updateSeller(Integer id, Integer companyId, SellerRole role, String name, String phoneNumber);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Seller s SET s.deletedAt = CURRENT_TIMESTAMP WHERE s.id = :id and s.companyId = :companyId")
    void deleteSellerByIdAndCompanyId(Integer id, Integer companyId);
}
