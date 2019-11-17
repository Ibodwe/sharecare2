package com.vogella.android.retrofitgithub.common.user;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Role {
    CAREGIVER(Authority.MANAGE_TOKENS,
            Authority.MODIFY_ELDERLY),

    ELDERLY(Authority.SEE_SERVICES);

    private final List<Authority> authorities;

    Role(final Authority... authorities) {
        this.authorities = Arrays.asList(authorities);
    }

    public List<Authority> getAuthorities() {
        return Collections.unmodifiableList(authorities);
    }

}
