package com.appsdeveloperblog.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.photoapp.api.users.data.AlbumServiceClient;
import com.appsdeveloperblog.photoapp.api.users.data.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.data.UserRepository;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.model.AlbumResponseModel;
import com.appsdeveloperblog.photoapp.api.users.utils.UserUtils;

import feign.FeignException;

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	UserRepository userRepository;

	BCryptPasswordEncoder encoder;

	AlbumServiceClient albumServiceClient;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder,
			AlbumServiceClient albumServiceClient) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.albumServiceClient = albumServiceClient;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		UserEntity userEntity = UserUtils.mapUserDtoToEntity(userDetails);
		//userEntity.setUserId(UUID.randomUUID().toString());
		userEntity.setUserId("abcd");
		userEntity.setEncryptedPassword(encoder.encode(userDetails.getPassword()));
		UserEntity savedUserEntity = userRepository.save(userEntity);
		UserDto savedUserDto = UserUtils.mapUserEntityToUserDto(savedUserEntity);
		return savedUserDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		if (userEntity == null)
			throw new RuntimeException();
		User user = new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
		return user;
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new RuntimeException();
		return UserUtils.mapUserEntityToUserDto(userEntity);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException("user ot found");
		UserDto userDto = UserUtils.mapUserEntityToUserDto(userEntity);
		List<AlbumResponseModel> albumListResponse = null;
		//try {
			albumListResponse = albumServiceClient.getAlbums(userId);
	//	} catch (FeignException e) {
		//	logger.error(e.getLocalizedMessage());
		//}
		userDto.setAlbumsList(albumListResponse);
		return userDto;
	}

}
