package com.aoede.commons.base.client;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.aoede.commons.base.component.BaseComponent;
import com.aoede.commons.base.exceptions.GenericException;
import com.aoede.commons.base.service.GenericAPIDefinition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractClient<
	AccessData,
	CreateData,
	UpdateData,
	SimpleResponse,
	DetailResponse
> extends BaseComponent implements GenericAPIDefinition<AccessData, CreateData, UpdateData, SimpleResponse, DetailResponse> {

	private RestTemplate restTemplate;
	private ObjectMapper objectMapper;

	private final Class<AccessData> accessDataType;
	private final Class<CreateData> createDataType;
	private final Class<UpdateData> updateDataType;

	private final Class<List<SimpleResponse>> simpleResponseType;
	private final Class<DetailResponse> detailResponseType;

	public AbstractClient (
		Class<AccessData> accessDataType, Class<CreateData> createDataType, Class<UpdateData> updateDataType,
		Class<List<SimpleResponse>> simpleResponseType, Class<DetailResponse> detailResponseType
	) {
		this.accessDataType = accessDataType;
		this.createDataType = createDataType;
		this.updateDataType = updateDataType;

		this.simpleResponseType = simpleResponseType;
		this.detailResponseType = detailResponseType;
	}

	@PostConstruct
	private void initialize () {
		restTemplate = new RestTemplate ();
		restTemplate.setErrorHandler(new AbstractClientResponseErrorHandler());

		objectMapper = new ObjectMapper();
	}

	@Override
	public List<SimpleResponse> search(String keyword) throws Exception {
		HttpHeaders headers = defaultHeaders();

		headers.add("X-Search-Terms", keyword);

		AbstractClientResponseResults result =
			send ("", HttpMethod.POST, new AbstractClientRequestCallback(headers), HttpStatus.OK);

		return objectMapper.readerFor(detailResponseType).readValue(result.body);
	}

	@Override
	public DetailResponse get(AccessData id) throws Exception {
		String path = objectMapper.writerFor(accessDataType).writeValueAsString(id);

		AbstractClientResponseResults result =
			send ("/" + path, HttpMethod.GET, new AbstractClientRequestCallback(defaultHeaders()), HttpStatus.OK);

		return objectMapper.readerFor(detailResponseType).readValue(result.body);
	}

	@Override
	public List<SimpleResponse> findAll() throws Exception {
		AbstractClientResponseResults result =
			send ("", HttpMethod.GET, new AbstractClientRequestCallback(defaultHeaders()), HttpStatus.OK);

		return objectMapper.readerFor(simpleResponseType).readValue(result.body);
	}

	@Override
	public DetailResponse create(CreateData data) throws Exception {
		String body = objectMapper.writerFor(createDataType).writeValueAsString(data);

		AbstractClientResponseResults result =
			send ("", HttpMethod.POST, new AbstractClientRequestCallback(defaultHeaders(), body), HttpStatus.CREATED);

		return objectMapper.readerFor(detailResponseType).readValue(result.body);
	}

	@Override
	public void update(AccessData id, @Valid UpdateData data) throws Exception {
		String body = objectMapper.writerFor(updateDataType).writeValueAsString(data);
		String path = objectMapper.writerFor(accessDataType).writeValueAsString(id);

		send ("/" + path, HttpMethod.DELETE, new AbstractClientRequestCallback(defaultHeaders(), body), HttpStatus.NO_CONTENT);
	}

	@Override
	public void delete(AccessData id) throws Exception {
		String path = objectMapper.writerFor(accessDataType).writeValueAsString(id);

		send ("/" + path, HttpMethod.DELETE, new AbstractClientRequestCallback(defaultHeaders()), HttpStatus.NO_CONTENT);
	}

	protected AbstractClientResponseResults send (String path, HttpMethod method, AbstractClientRequestCallback parameters, HttpStatus expectedStatus) throws GenericException, JsonMappingException, JsonProcessingException {

		AbstractClientResponseResults result = restTemplate.execute(url() + path, method, parameters, response -> {
			AbstractClientResponseResults results = new AbstractClientResponseResults ();

			results.status  = response.getStatusCode();
			results.headers = response.getHeaders();
			results.body    = new String (response.getBody().readAllBytes());

			return results;
		});

		if (result.status.value() != expectedStatus.value()) {
			throw (GenericException)objectMapper.readerFor(GenericException.class).readValue(result.body);
		}

		return result;
	}

	protected HttpHeaders defaultHeaders () {
		HttpHeaders headers = new HttpHeaders ();

		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}

	abstract public String url ();
}



