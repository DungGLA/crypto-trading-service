package com.assignment.cryptotradingservice.common.helper;

import com.assignment.cryptotradingservice.common.response.PageResponse;
import org.springframework.data.domain.Page;

public class PageResponseMapper {
    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
