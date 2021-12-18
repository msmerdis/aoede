package com.aoede.modules.music.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoede.commons.base.controller.AbstractResourceController;
import com.aoede.commons.base.exceptions.GenericException;
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
	protected SimpleTrackResponse simpleResponse(Track entity) {
		SimpleTrackResponse response = new SimpleTrackResponse ();

		response.setId(entity.getId());
		response.setClef(entity.getClef());

		return response;
	}

	@Override
	protected DetailTrackResponse detailResponse(Track entity) {
		DetailTrackResponse response = new DetailTrackResponse ();

		response.setId(entity.getId());
		response.setClef(entity.getClef());

		return response;
	}

	@Override
	protected Track createRequest(CreateTrack request) {
		return updateRequest (request);
	}

	@Override
	protected Track updateRequest(UpdateTrack request) {
		Track track = new Track ();

		try {
			track.setClef(clefService.find(request.getClef()));
		} catch (GenericException e) {
			throw new RuntimeException(e.getMessage());
		}

		return track;
	}

}



