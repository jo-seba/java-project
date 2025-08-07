package com.concertticketing.sellerapi.apis.sellers.constant;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

/**
 * 판매자 역할을 정의하는 enum
 *
 * <p>
 * 해당 enum 값들은 {@code SecurityConfig} 클래스 내 {@code RoleHierarchyImpl} 설정에서 역할 계층(hierarchy) 구성에 사용된다.
 * dbValue 순서를 기준 내림차순으로 권한 처리된다.
 *
 * <p>
 * 예를 들어, {@code ROLE_OWNER}는 {@code ROLE_MANAGER}와 {@code ROLE_MEMBER} 권한을 포함한다.
 *
 * <p>
 * 계층 구조 변경 시(내림차순이 아닐 시), Security 내 계층 설정도 반드시 함께 수정해야 한다.
 */
@RequiredArgsConstructor
public enum SellerRole {
    MEMBER(1),
    MANAGER(11),
    OWNER(21);

    private final int dbValue;

    private static final Map<Integer, SellerRole> CACHE_MAP = new HashMap<>();

    static {
        for (SellerRole role : SellerRole.values()) {
            CACHE_MAP.put(role.dbValue, role);
        }
    }

    public int getDbValue() {
        return dbValue;
    }

    public static SellerRole from(int dbValue) {
        SellerRole result = CACHE_MAP.get(dbValue);
        if (result == null) {
            throw new IllegalArgumentException("Invalid sellerRole: " + dbValue);
        }
        return result;
    }

    public String getSecurityRoleName() {
        return "ROLE_" + name();
    }
}
