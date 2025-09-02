package com.concertticketing.sellerapi.apis.sellers.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.concertticketing.commonerror.exception.common.CommonBadRequestException;
import com.concertticketing.commonerror.exception.common.CommonConflictException;
import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.domainrdb.common.ReflectionField;
import com.concertticketing.domainrdb.domain.SellerFixture;
import com.concertticketing.domainrdb.domain.seller.domain.Seller;
import com.concertticketing.domainrdb.domain.seller.dto.SellerListDto;
import com.concertticketing.domainrdb.domain.seller.enums.SellerRole;
import com.concertticketing.domainrdb.domain.seller.enums.SellerSort;
import com.concertticketing.domainrdb.domain.seller.repository.SellerRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Service-UnitTest] SellerService")
class SellerServiceTest {
    @InjectMocks
    private SellerService sellerService;

    @Mock
    private SellerRepository sellerRepository;

    public final int DEFAULT_PAGE_SIZE = 10;

    @DisplayName("saveSeller")
    @Nested
    class SaveSeller {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("판매자 저장")
            @Test
            void saveSeller() {
                Integer id = Integer.MAX_VALUE;
                Seller seller = SellerFixture
                    .sellerBuilder()
                    .build();
                given(sellerRepository.save(seller))
                    .willAnswer(inv -> {
                        Seller s = inv.getArgument(0);
                        ReflectionField.setField(seller, "id", id);
                        return s;
                    });

                Seller result = sellerService.saveSeller(seller);

                assertThat(result.getId()).isEqualTo(id);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("email unique 제약조건 위반")
            @Test
            void uniqueError() {
                Seller seller = SellerFixture
                    .sellerBuilder()
                    .build();
                given(sellerRepository.save(seller))
                    .willThrow(new DataIntegrityViolationException("unique constraint violation"));

                assertThatThrownBy(() -> sellerService.saveSeller(seller))
                    .isInstanceOf(CommonConflictException.class);
            }

            @DisplayName("role이 MEMBER, MANAGER가 아님")
            @Test
            void invalidSellerRole() {
                Seller seller = SellerFixture
                    .sellerBuilder()
                    .role(SellerRole.OWNER)
                    .build();

                assertThatThrownBy(() -> sellerService.saveSeller(seller))
                    .isInstanceOf(CommonBadRequestException.class);
            }
        }
    }

    @DisplayName("findSeller")
    @Nested
    class FindSeller {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("판매자 조회")
            @Test
            void findSeller() {
                Integer id = 1;
                Seller seller = SellerFixture
                    .sellerBuilder()
                    .id(id)
                    .build();
                given(sellerRepository.findById(id))
                    .willReturn(Optional.of(seller));

                Seller result = sellerService.findSeller(id);

                assertThat(result).isNotNull();
                assertThat(result.getId()).isEqualTo(id);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("not found")
            @Test
            void notFound() {
                Integer id = 1;
                given(sellerRepository.findById(id))
                    .willReturn(Optional.empty());

                assertThatThrownBy(() -> sellerService.findSeller(id))
                    .isInstanceOf(CommonNotFoundException.class);
            }
        }
    }

    @DisplayName("findSellers")
    @Nested
    class FindSellers {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("판매자들 조회")
            @Test
            void getSellers() {
                Integer companyId = 1, sellerId = 1;
                SellerSort sort = SellerSort.NEWEST;
                Pageable pageable = PageRequest.of(1, DEFAULT_PAGE_SIZE);
                List<SellerListDto> items = List.of(
                    SellerFixture.sellerListDtoBuilder()
                        .id(sellerId++)
                        .build(),
                    SellerFixture.sellerListDtoBuilder()
                        .id(sellerId++)
                        .build()
                );
                Page<SellerListDto> page = new PageImpl<>(
                    items,
                    pageable,
                    items.size()
                );
                given(sellerRepository.findSellersDto(companyId, sort, pageable))
                    .willReturn(page);

                assertThat(sellerService.findSellers(companyId, sort, pageable).getSize())
                    .isEqualTo(DEFAULT_PAGE_SIZE);
                assertThat(sellerService.findSellers(companyId, sort, pageable).getContent().size())
                    .isEqualTo(items.size());
            }

            @DisplayName("빈 판매자")
            @Test
            void getEmptySellers() {
                Integer companyId = 1;
                SellerSort sort = SellerSort.NEWEST;
                List<SellerListDto> items = List.of();
                Pageable pageable = PageRequest.of(100, DEFAULT_PAGE_SIZE);
                Page<SellerListDto> sellers = new PageImpl<>(
                    items,
                    pageable,
                    items.size()
                );
                given(sellerRepository.findSellersDto(companyId, sort, pageable))
                    .willReturn(sellers);

                assertThat(sellerService.findSellers(companyId, sort, pageable).getContent().size())
                    .isEqualTo(0);
            }
        }
    }

    @DisplayName("updateSeller")
    @Nested
    class UpdateSeller {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("판매자 수정")
            @Test
            void updateSeller() {
                Integer id = 1;
                Integer companyId = 1;
                SellerRole role = SellerRole.MEMBER;
                String name = "name";
                String phoneNumber = "01012345678";
                given(sellerRepository.updateSeller(id, companyId, role, name, phoneNumber))
                    .willReturn(1);

                int affected = sellerService.updateSeller(id, companyId, role, name, phoneNumber);

                assertThat(affected).isEqualTo(1);
            }

            @DisplayName("판매자 수정(존재X)")
            @Test
            void updateSellerNotExist() {
                Integer id = 1;
                Integer companyId = 1;
                SellerRole role = SellerRole.MEMBER;
                String name = "name";
                String phoneNumber = "01012345678";
                given(sellerRepository.updateSeller(id, companyId, role, name, phoneNumber))
                    .willReturn(0);

                int affected = sellerService.updateSeller(id, companyId, role, name, phoneNumber);

                assertThat(affected).isEqualTo(0);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("role이 MEMBER, MANAGER가 아님")
            @Test
            void invalidSellerRole() {
                Integer id = 1;
                Integer companyId = 1;
                SellerRole role = SellerRole.OWNER;
                String name = "name";
                String phoneNumber = "01012345678";

                assertThatThrownBy(() -> sellerService.updateSeller(id, companyId, role, name, phoneNumber))
                    .isInstanceOf(CommonBadRequestException.class);
            }
        }
    }

    @DisplayName("deleteSeller")
    @Nested
    class DeleteSeller {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("판매자 삭제")
            @Test
            void deleteSeller() {
                Integer id = 1;
                Integer companyId = 1;

                sellerService.deleteSeller(id, companyId);
            }
        }
    }
}
