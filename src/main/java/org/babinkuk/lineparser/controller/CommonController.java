package org.babinkuk.lineparser.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.lineparser.common.ApiResponse;
import org.babinkuk.lineparser.common.Echo;
import org.babinkuk.lineparser.common.ProducesJson;
import org.babinkuk.lineparser.model.RequestData;
import org.babinkuk.lineparser.service.LineParserService;
import org.babinkuk.parser.generic.staticformat.ParseException;
import org.babinkuk.parser.generic.staticformat.RecordUnrecognizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
///import static org.babinkuk.config.Api.ROOT;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CommonController {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	// services
	private LineParserService lineParserService;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	public CommonController(LineParserService lineParserService) {
		this.lineParserService = lineParserService;
	}
	
	// expose GET "/echo"
	@GetMapping("/echo")
	@ProducesJson
	public Echo echo() {
		return new Echo();
	}
	
	// expose GET "/config"
	@GetMapping("/config")
	//@ProducesJson
	public ResponseEntity<ApiResponse> getAppConfig() {
		
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		propertyMap.put("application name", environment.getProperty("spring.application.name", "Unknown"));
		propertyMap.put("version", environment.getProperty("spring.application.version", "Unknown"));
		propertyMap.put("author", environment.getProperty("spring.application.author", "Unknown"));
		
		return new ApiResponse(HttpStatus.OK, null, propertyMap).toEntity();
	}
	
	/**
	 * expose POST "/courses"
	 * add new course
	 * 
	 * @param courseVO
	 * @param validationRole
	 * @return
	 * @throws JsonProcessingException
	 * @throws ParseException 
	 * @throws RecordUnrecognizedException 
	 */
	@PostMapping("")
	public ResponseEntity<ApiResponse> parseLine(
			@RequestBody RequestData requestData) throws JsonProcessingException, RecordUnrecognizedException, ParseException {
		
		return ResponseEntity.of(Optional.ofNullable(lineParserService.parseLine(requestData)));
	}
	

	
}
