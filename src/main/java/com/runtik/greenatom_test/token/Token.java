package com.runtik.greenatom_test.token;


import com.runtik.greenatom_test.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {
    private Integer id;
    private String tokenValue;
    private boolean revoked;
    private boolean expired;
    private User user;
}
