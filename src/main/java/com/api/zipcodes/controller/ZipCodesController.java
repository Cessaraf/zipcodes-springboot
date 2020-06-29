package com.api.zipcodes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.api.zipcodes.bo.ZipCodeBO;
import com.api.zipcodes.models.ZipCode;

@RestController
@RequestMapping(path = "zip-codes")
public class ZipCodesController {
	
	@Autowired
	private ZipCodeBO zipCodeBO;
	
	private static final Logger log = LoggerFactory.getLogger(ZipCodesController.class);
	
	@RequestMapping(path = "/{zipcode}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<Object> getZipCode(@PathVariable("zipcode") String zipcode ) {

		ZipCode zip;
		log.info("Searching zipcode " + zipcode);
		if (zipcode.length() != 5 || !zipcode.matches("[0-9]+") 
				|| (zip = zipCodeBO.getAddressByZipCode(zipcode)).getFederal_entity() == null) {
			log.info("Zipcode not found " + zipcode);
			return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
		}
		log.info("Zipcode found " + zipcode);		
		return new ResponseEntity<>(zip, HttpStatus.OK);
	}

}
