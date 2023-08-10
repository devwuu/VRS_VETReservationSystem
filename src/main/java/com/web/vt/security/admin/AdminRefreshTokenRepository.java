package com.web.vt.security.admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRefreshTokenRepository extends CrudRepository<AdminRefreshToken, String> {
}
