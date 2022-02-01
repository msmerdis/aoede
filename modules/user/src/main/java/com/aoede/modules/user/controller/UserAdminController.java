package com.aoede.modules.user.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractResourceController;
import com.aoede.commons.base.exceptions.BadRequestException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.modules.user.domain.User;
import com.aoede.modules.user.service.UserService;
import com.aoede.modules.user.transfer.user.ChangePassword;
import com.aoede.modules.user.transfer.user.CreateUser;
import com.aoede.modules.user.transfer.user.DetailUserResponse;
import com.aoede.modules.user.transfer.user.SimpleUserResponse;
import com.aoede.modules.user.transfer.user.UpdateUser;
import com.aoede.modules.user.transfer.user.UserStatus;

@RestController
@RequestMapping ("/admin/user")
public class UserAdminController extends AbstractResourceController<
	Long,
	User,
	Long,
	CreateUser,
	UpdateUser,
	SimpleUserResponse,
	DetailUserResponse,
	UserService
> {

	public UserAdminController(UserService service) {
		super(service);
	}

	@PutMapping("/{id}/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePassword(@PathVariable("id") final Long id, @Valid @RequestBody final ChangePassword data) throws Exception {
		User user = new User();

		user.setPassword(data.getPassword());

		service.update(id, user);
	}

	@Override
	public SimpleUserResponse simpleResponse(User entity, boolean includeParent, boolean cascade) {
		return detailResponse(entity, includeParent, cascade);
	}

	@Override
	public DetailUserResponse detailResponse(User entity, boolean includeParent, boolean cascade) {
		DetailUserResponse response = new DetailUserResponse ();

		response.setId(entity.getId());
		response.setUsername(entity.getUsername());
		response.setStatus(entity.getStatus().toString());
		response.setRoles(
			entity.getRoles().stream()
				.map(r -> r.getAuthority())
				.collect(Collectors.toSet())
		);

		return response;
	}

	@Override
	public User createDomain(CreateUser data) {
		User domain = new User ();

		domain.setUsername(data.getUsername());
		domain.setPassword(data.getPassword());
		domain.setStatus(
			Stream.of(UserStatus.values())
				.filter(s -> s.toString().equals(data.getStatus()))
				.findFirst()
				.orElseThrow(() -> {
					return new GenericExceptionContainer (
						new BadRequestException("Invalid status: " + data.getStatus())
					);
				})
		);

		return domain;
	}

	@Override
	public User updateDomain(UpdateUser data) {
		User domain = new User ();

		domain.setStatus(
			Stream.of(UserStatus.values())
				.filter(s -> s.toString().equals(data.getStatus()))
				.findFirst()
				.orElseThrow(() -> {
					return new GenericExceptionContainer (
						new BadRequestException("Invalid status: " + data.getStatus())
					);
				})
		);
		domain.setUsername(data.getUsername());

		return domain;
	}

	@Override
	public Long createDomainKey(Long data) {
		return data;
	}

}



