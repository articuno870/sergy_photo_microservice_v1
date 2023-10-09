package com.appsdeveloperblog.photoapp.api.users.ui.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.appsdeveloperblog.photoapp.api.users.data.AlbumServiceClient;
import com.appsdeveloperblog.photoapp.api.users.service.UserService;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.model.AlbumResponseModel;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserResponseModel;
import com.appsdeveloperblog.photoapp.api.users.ui.model.UserResponseModel;
import com.appsdeveloperblog.photoapp.api.users.utils.UserUtils;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment environment;

	@Autowired
	UserService userService;

	@Autowired
	RestTemplate restTemplate;
	
	
	@GetMapping("/status/check")
	public String status() {
		return "working" + environment.getProperty("local.server.port");
	}

	@PostMapping("/save")
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		UserDto userDtoRequest = UserUtils.mapCreateUserRequestModelToUserDto(userDetails);
		UserDto savedUserDto = userService.createUser(userDtoRequest);
		CreateUserResponseModel createUserResponseModel = UserUtils.mapUserDtoToCreateUserResponseModel(savedUserDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);
	}

	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
		UserDto userDto = userService.getUserByUserId(userId);
		UserResponseModel userResponseModel = new ModelMapper().map(userDto, UserResponseModel.class);
//		String url = String.format("http://ALBUMS-WS/users/%s/albums", userId);
//		ResponseEntity<List<AlbumResponseModel>> albumListResponse =restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//		});
//		userResponseModel.setAlbums(albumListResponse.getBody());
		
		//List<AlbumResponseModel> albumListResponse =  albumServiceClient.getAlbum(userId);
		//userResponseModel.setAlbums(albumListResponse);
		return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
	}

}
