package com.api.zipcodes.bo;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.api.zipcodes.models.ZipCode;
import com.api.zipcodes.repository.ZipCodeRepository;

@Component
public class ZipCodeBO {

	@Autowired 
	ZipCodeRepository zipCodeRepository;
	
	/**
	 * Method gets address by zipcode
	 * 
	 * @param zipCode
	 * @return
	 */
	public ZipCode getAddressByZipCode(String zipCode) {
		return zipCodeRepository.getMunicipalitys().stream().filter( m -> {
			return m.getZipCodes().contains(zipCode); }
		).findAny().map(municipality -> 
			new ZipCode(
					zipCode, 
					municipality.getLocality(), 
					municipality.getCestado().getName(), 
					municipality.getSettlements().stream().filter(settlement -> 
						settlement.getZipCode().equals(zipCode)
					).collect(Collectors.toList()), 
					municipality.getMunicipality())
		).orElse(new ZipCode());
		
	}
	
}
 