package com.aoede.modules.music.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.exceptions.GenericExceptionContainer;
import com.aoede.commons.base.exceptions.UnauthorizedException;
import com.aoede.commons.base.exceptions.ValidationException;
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.entity.SheetEntity;
import com.aoede.modules.music.repository.SheetRepository;
import com.aoede.modules.user.service.UserService;

@Service
public class SheetServiceImpl extends AbstractServiceDomainImpl <Long, Sheet, Long, SheetEntity, SheetRepository> implements SheetService {

	private UserService userService;

	public SheetServiceImpl(SheetRepository repository, EntityManagerFactory entityManagerFactory, UserService userService) {
		super(repository, entityManagerFactory);

		this.userService  = userService;
	}

	@Override
	public boolean supportsFreeTextSearch() {
		return false;
	}

	@Override
	public String domainName() {
		return "Sheet";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public List<Sheet> findAll() throws GenericException {
		return repository.findByUserId(
			userService.currentUserId()
		).stream().map(e -> createDomain(e)).collect(Collectors.toList());
	}

	@Override
	public SheetEntity createEntity(Sheet domain) {
		SheetEntity entity = new SheetEntity ();

		if (domain.getId() != null) {
			throw new GenericExceptionContainer(
				new ValidationException(
					"sheet",
					"id",
					domain.getId().toString(),
					"Sheet cannot provide an id during creation"
				)
			);
		}

		entity.setUserId(userService.currentUserId());
		entity.setName(domain.getName());
		entity.setTracks(domain.getTracks());

		return entity;
	}

	@Override
	public void updateEntity(Sheet domain, SheetEntity entity) {

		if (domain.getId() == null) {
			throw new GenericExceptionContainer(
				new ValidationException(
					"sheet",
					"id",
					null,
					"Sheet's id cannot be null"
				)
			);
		}

		if (!domain.getId().equals(entity.getId())) {
			throw new GenericExceptionContainer(
				new ValidationException(
					"sheet",
					"id",
					domain.getId().toString(),
					"Sheet's id does not match updating record"
				)
			);
		}

		if (!entity.getUserId().equals(userService.currentUserId())) {
			throw new GenericExceptionContainer(
				new UnauthorizedException("Cannot update sheets created by a different user")
			);
		}

		entity.setName(domain.getName());
		entity.setTracks(domain.getTracks());
	}

	@Override
	public Sheet createDomain(SheetEntity entity) {
		Sheet sheet = new Sheet ();

		if (!entity.getUserId().equals(userService.currentUserId())) {
			throw new GenericExceptionContainer(
				new UnauthorizedException("Cannot access sheets created by a different user")
			);
		}

		updateDomain (entity, sheet);

		return sheet;
	}

	@Override
	public void updateDomain(SheetEntity entity, Sheet domain) {
		domain.setId(entity.getId());
		domain.setName(entity.getName());
		domain.setTracks(entity.getTracks());
	}

	@Override
	public boolean verifyDelete(SheetEntity entity) {
		return entity.getUserId().equals(userService.currentUserId());
	}

	@Override
	public Long createEntityKey(Long key) {
		return key;
	}

}



