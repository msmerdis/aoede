package com.aoede.modules.music.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractCompositeResourceController;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.domain.TrackKey;
import com.aoede.modules.music.service.ClefService;
import com.aoede.modules.music.service.TrackService;
import com.aoede.modules.music.transfer.track.AccessTrack;
import com.aoede.modules.music.transfer.track.CreateTrack;
import com.aoede.modules.music.transfer.track.DetailTrackResponse;
import com.aoede.modules.music.transfer.track.SimpleTrackResponse;
import com.aoede.modules.music.transfer.track.UpdateTrack;

@RestController
@RequestMapping ("/api/track")
public class TrackController extends AbstractCompositeResourceController<
	TrackKey,
	Track,
	AccessTrack,
	CreateTrack,
	UpdateTrack,
	SimpleTrackResponse,
	DetailTrackResponse,
	TrackService
> {
	private ClefService clefService;
	private SectionController sectionController;

	public TrackController(TrackService service, ClefService clefService, SectionController sectionController) {
		super(service);

		this.clefService = clefService;
		this.sectionController = sectionController;
		this.sectionController.updateTrackController(this);
	}

	@Override
	public SimpleTrackResponse simpleResponse(Track entity, boolean includeParent, boolean cascade) {
		SimpleTrackResponse response = new SimpleTrackResponse ();
		TrackKey key = entity.getId();

		response.setId(new AccessTrack(key.getSheetId(), key.getTrackId()));
		response.setClef(entity.getClef());

		return response;
	}

	@Override
	public DetailTrackResponse detailResponse(Track entity, boolean includeParent, boolean cascade) {
		DetailTrackResponse response = new DetailTrackResponse ();
		TrackKey key = entity.getId();

		response.setId(new AccessTrack(key.getSheetId(), key.getTrackId()));
		response.setClef(entity.getClef());

		if (includeParent) {
			response.setSheetId(entity.getId().getSheetId());
		}

		if (cascade) {
			response.setSections (
				entity.getSections().stream().map(d -> sectionController.simpleResponse(d, false, true)).collect(Collectors.toList())
			);
		}

		return response;
	}

	@Override
	public Track createDomain(CreateTrack request) {
		Track track = updateDomain(request);
		Sheet sheet = new Sheet ();

		sheet.setId(request.getSheetId());
		track.setSheet(sheet);

		return track;
	}

	@Override
	public Track updateDomain(UpdateTrack request) {
		Track track = new Track ();

		try {
			track.setClef(clefService.find(request.getClef()));
		} catch (GenericException e) {
			throw new RuntimeException(e.getMessage());
		}

		return track;
	}

	@GetMapping("/sheet/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<SimpleTrackResponse> findAllBySheet(@PathVariable("id") final Long id) throws Exception {
		return service.findBySheetId(id).stream().map(e -> simpleResponse(e, true, true)).collect(Collectors.toList());
	}

	@Override
	public TrackKey createDomainKey(AccessTrack data) {
		TrackKey key = new TrackKey ();

		key.setSheetId(data.getSheetId());
		key.setTrackId(data.getTrackId());

		return key;
	}
}



