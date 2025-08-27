package com.concertticketing.sellerapi.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.domainrdb.domain.seller.domain.Seller;
import com.concertticketing.domainrdb.domain.seller.repository.SellerRepository;
import com.concertticketing.sellerapi.apis.sellers.mapper.SellerMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final SellerMapper sellerMapper;

    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findById(Integer.parseInt(id))
            .orElseThrow(CommonNotFoundException::new);
        return sellerMapper.toSecurityUserDetails(seller);
    }
}
