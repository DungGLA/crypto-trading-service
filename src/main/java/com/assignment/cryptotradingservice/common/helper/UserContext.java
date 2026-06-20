package com.assignment.cryptotradingservice.common.helper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
public class UserContext {
    private Long userId;
}
