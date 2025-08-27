package com.concertticketing.domainrdb.domain.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.domainrdb.domain.seller.domain.Seller;
import com.concertticketing.domainrdb.domain.seller.enums.SellerRole;

public interface SellerRepository extends JpaRepository<Seller, Integer>, SellerRepositoryCustom {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Seller s "
        + "SET s.role = :role, s.name = :name, s.phoneNumber = :phoneNumber, s.updatedAt = CURRENT_TIMESTAMP "
        + "WHERE s.id = :id and s.company.id = :companyId")
    int updateSeller(Integer id, Integer companyId, SellerRole role, String name, String phoneNumber);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Seller s SET s.deletedAt = CURRENT_TIMESTAMP WHERE s.id = :id and s.company.id = :companyId")
    void deleteSellerByIdAndCompanyId(Integer id, Integer companyId);
}
