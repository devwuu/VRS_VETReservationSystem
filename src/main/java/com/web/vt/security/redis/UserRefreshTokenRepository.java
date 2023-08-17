package com.web.vt.security.redis;

import org.springframework.data.repository.CrudRepository;

public interface UserRefreshTokenRepository extends CrudRepository<UserRefreshToken, String> {
}
