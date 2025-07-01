package com.concertticketing.sellerapi.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.sellers.domain.Seller;
import com.concertticketing.sellerapi.apis.sellers.mapper.SellerMapper;
import com.concertticketing.sellerapi.apis.sellers.repository.SellerRepository;
import com.concertticketing.sellerapi.common.exception.CommonErrorCode;
import com.concertticketing.sellerapi.common.exception.GlobalErrorException;

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
            .orElseThrow(() -> new GlobalErrorException(CommonErrorCode.NOT_FOUND));
        return sellerMapper.toSecurityUserDetails(seller);
    }
}
