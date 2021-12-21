package com.aoede.modules.music.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractResourceController;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.modules.music.domain.Sheet;
import com.aoede.modules.music.domain.Track;
import com.aoede.modules.music.service.ClefService;
import com.aoede.modules.music.service.TrackService;
import com.aoede.modules.music.transfer.track.CreateTrack;
import com.aoede.modules.music.transfer.track.DetailTrackResponse;
import com.aoede.modules.music.transfer.track.SimpleTrackResponse;
import com.aoede.modules.music.transfer.track.UpdateTrack;

@RestController
@RequestMapping ("/api/track")
public class TrackController extends AbstractResourceController<
	Long,
	Track,
	CreateTrack,
	UpdateTrack,
	SimpleTrackResponse,
	DetailTrackResponse,
	TrackService
> {
	ClefService clefService;

	public TrackController(TrackService service, ClefService clefService) {
		super(service);

		this.clefService = clefService;
	}

	@Override
	public SimpleTrackResponse simpleResponse(Track entity, boolean includeParent, boolean cascade) {
		SimpleTrackResponse response = new SimpleTrackResponse ();

		response.setTrackId(entity.getId());
		response.setClef(entity.getClef());

		return response;
	}

	@Override
	public DetailTrackResponse detailResponse(Track entity, boolean includeParent, boolean cascade) {
		DetailTrackResponse response = new DetailTrackResponse ();

		response.setTrackId(entity.getId());
		response.setClef(entity.getClef());

		if (includeParent)
			response.setSheetId(entity.getSheet().getId());

		return response;
	}

	@Override
	public Track createRequest(CreateTrack request) {
		Track track = updateRequest (request);
		Sheet sheet = new Sheet ();

		sheet.setId(request.getSheetId());
		track.setSheet(sheet);

		return track;
	}

	@Override
	public Track updateRequest(UpdateTrack request) {
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
		return service.findAllBySheet(id).stream().map(e -> simpleResponse(e, true, true)).collect(Collectors.toList());
	}

}



