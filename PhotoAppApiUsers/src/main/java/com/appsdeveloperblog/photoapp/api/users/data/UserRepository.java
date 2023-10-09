package com.appsdeveloperblog.photoapp.api.users.data;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	public UserEntity findByEmail(String username);

	public UserEntity findByUserId(String userId);

}
