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
import com.aoede.commons.base.service.AbstractServiceDomainImpl;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.entity.SheetEntity;
import com.aoede.modules.music.entity.Sheetable;
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

		entity.setUserId(userService.currentUserId());
		entity.setName(domain.getName());

		return entity;
	}

	@Override
	public void updateEntity(Sheet domain, SheetEntity entity) {
		if (!entity.getUserId().equals(userService.currentUserId())) {
			throw new GenericExceptionContainer(
				new UnauthorizedException("Cannot update sheets created by a different user")
			);
		}
		entity.setName(domain.getName());
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
	}

	@Override
	public boolean verifyDelete(SheetEntity entity) {
		return entity.getUserId().equals(userService.currentUserId());
	}

	public void updateSheetableEntity(Sheetable entity, Long id) {
		entity.setSheet(repository.getById(id));
	}

	@Override
	public Long createEntityKey(Long key) {
		return key;
	}

}



