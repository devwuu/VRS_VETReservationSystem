package com.web.vt.security.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepository extends CrudRepository<Blacklist, String> {

}
