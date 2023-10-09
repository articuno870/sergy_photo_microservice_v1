package com.appsdeveloperblog.photoapp.api.users.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.appsdeveloperblog.photoapp.api.users.data.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserResponseModel;

public class UserUtils {

	public static UserEntity mapUserDtoToEntity(UserDto userDto) {
		UserEntity userEntity = getMapper().map(userDto, UserEntity.class);
		return userEntity;
	}

	public static UserDto mapCreateUserRequestModelToUserDto(CreateUserRequestModel userDetails) {
		UserDto userDto = getMapper().map(userDetails, UserDto.class);
		return userDto;
	}

	private static ModelMapper getMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
	}

	public static UserDto mapUserEntityToUserDto(UserEntity savedUserEntity) {

		return getMapper().map(savedUserEntity, UserDto.class);
	}

	public static CreateUserResponseModel mapUserDtoToCreateUserResponseModel(UserDto savedUserDto) {
		return getMapper().map(savedUserDto, CreateUserResponseModel.class);
	}

}
