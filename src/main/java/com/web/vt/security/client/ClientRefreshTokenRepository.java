package com.web.vt.security.client;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRefreshTokenRepository extends CrudRepository<ClientRefreshToken, String> {
}
