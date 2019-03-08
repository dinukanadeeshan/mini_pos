package org.syscolabs.cx.pos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    private String clientid = "tutorialspoint";
    private String clientSecret = "my-secret-key";

    private String privateKey =
            "-----BEGIN RSA PRIVATE KEY-----\n" +
                    "MIIEpQIBAAKCAQEAyZ/5g84g+8hbmTWF/zxllrFm3FID6KO+599+wER3F4oZSyI/" +
                    "oT3qDfW7URHYsik5JZFl/yQerdnsX2txuyJkn/HF15mYVKNeZmmDI3ZaNL2Mnyx8" +
                    "xYc0uKLR5Htul2WlOdYoiV/Gz2kx9EfTOVzwXgdECqB2maw+LQguCOLHmDhfailw" +
                    "0UjKnxQrbXDqElYz33ZMCmd6MNFOG9mEVfoPSVKr69ZFXF20vMnunAEo59HVe0SC" +
                    "0wHTDwkvEtJuiwGb914p2g4BWejUjdSKvULIhWA8cCZZ9rdD+8Iu1jJEUdw0UzdZ" +
                    "lH3IVYeD5L33GqEEE9amlw++YFn4cMWnZn5t+wIDAQABAoIBAQCm41l+ddHrVm4r" +
                    "JJhjKbTUxMZlOyWtHExaVS3vKzyAKXjCLfkJF9xx7aI8ek8pOOHqbK1GQLl8IVN7" +
                    "r82VneQ4V8DwslvaQtdSYaeAZOhJm1OYNRSZ004aPfsJ6fkiK6pVcyRegP5ok6Qb" +
                    "lJALfRggXFc/jI5eqKlWUNz50exv21DBdP0aZFBu2GdOJcAlsP0Y+6xzWx7ha5bS" +
                    "Mkp1Z0iAuad1vwrl9844GwOIEO83soCAej/sYUp8OauDRk9Kq9gm5q0gT9/8lfVy" +
                    "7euOnXUrIzacGGSPwxUssHZpq4g7FgYjhkk++um36f9xWpkkMqD+7LarkGsbqmr8" +
                    "y3AXKehxAoGBAPASFZMJQRpKhaeMPXLP08ORGfL2jHNM6qkhXz03IFwLyKX+XLh2" +
                    "WtCKRkt13OIHUJ0zg7eeikpnqccueFOWXhy6DaBWZIj09Pp5HjOpPWDOZj13yAqz" +
                    "1RdGZ5ur8jjrpMBCwSqYWWZHjqN69VANbrHEV85ztHPXCHvO1oEy378dAoGBANcA" +
                    "1sJKPJZs9e6R2MK5HhqYaVQbqckEt2KDfanDd9x8jLsrEWcyR8XHCrQm7ENJkNvE" +
                    "4o2nU9otH+x/jL/hCfxTABVZp/CZx6366RHYKrnWEKsczj83EXT/s9RT5J1JeaiX" +
                    "cuedVPDTnYi0VpOiATnxRcC4TNjb4QPJMj5EVd33AoGASy6eqwl+5ejoqmv04nvL" +
                    "abeLVNd5bLJt2sGkvdsBu1HFaJacGviVwDFNilxC7FY2HMUBPwPbcym7tbEL5cF1" +
                    "f7GUit4DZhCwl7BOkQczPZnl0uIsN43R8hQOZmYW2VSSp9NAA0Lq3GkUdoYPRR6v" +
                    "x+QhS3pYzN5OvdcsvhFcmNECgYEAvbTNyYz7PLdToHcrzVTNjSXh+gaWnvn8XX4s" +
                    "CPih7/gtrJBpwKaK0aW4ehDF8vEcUe29AMQdpqqeOmyo4j1MNEmAD1aWaiMmHwr/" +
                    "T3Fu8Z5CAtld2kVar+NW5whG9cl5ARrwlhEqe3wcyhxlL8qITnnm1A9KLyaDunVy" +
                    "UaK2KS0CgYEAtRWh2LodWehii3RC3HanbhaDUCYp3HBmOIgVBvNICTAQHvJfJAc7" +
                    "fHcVq9BA1v+5wGAEocs9C5d0VLWpY3sNHbuYgKTWAC+y3rLEUTYf7utfAn599GGx" +
                    "YzLoAc1kBtKHbvaXVeQfnhr0YxBqr4fwpKCLoSqALZjEHkWR8Uc29X0=\n" +
                    "-----END RSA PRIVATE KEY-----";

    private String publicKey =
            "-----BEGIN PUBLIC KEY-----\n" +
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyZ/5g84g+8hbmTWF/zxl" +
                    "lrFm3FID6KO+599+wER3F4oZSyI/oT3qDfW7URHYsik5JZFl/yQerdnsX2txuyJk" +
                    "n/HF15mYVKNeZmmDI3ZaNL2Mnyx8xYc0uKLR5Htul2WlOdYoiV/Gz2kx9EfTOVzw" +
                    "XgdECqB2maw+LQguCOLHmDhfailw0UjKnxQrbXDqElYz33ZMCmd6MNFOG9mEVfoP" +
                    "SVKr69ZFXF20vMnunAEo59HVe0SC0wHTDwkvEtJuiwGb914p2g4BWejUjdSKvULI" +
                    "hWA8cCZZ9rdD+8Iu1jJEUdw0UzdZlH3IVYeD5L33GqEEE9amlw++YFn4cMWnZn5t" +
                    "+wIDAQAB\n" +
                    "-----END PUBLIC KEY-----";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientid).secret(clientSecret).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);

    }
}
