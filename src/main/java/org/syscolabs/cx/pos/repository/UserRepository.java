package org.syscolabs.cx.pos.repository;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.syscolabs.cx.pos.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    public UserEntity getUserDetails(String username) {
        UserEntity ue = new UserEntity();
        ue.setUsername("user");
        ue.setPassword("password");
        List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
        grantedAuthoritiesList.add(new SimpleGrantedAuthority("ROLE_SYSTEMADMIN"));
        ue.setGrantedAuthoritiesList(grantedAuthoritiesList);
        return ue;
    }
}