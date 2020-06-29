package com.api.zipcodes.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.api.zipcodes.models.Municipality;

/**
 * 
 * @author cesar
 *
 */
@Component
public class ZipCodeRepository {

	private List<Municipality> allMunicipalities = new ArrayList<>();
	
	public List<Municipality> getMunicipalitys() {
	        return this.allMunicipalities;
	}	
	
	public void setMunicipalities(List<Municipality> allMunicipalities) {
		this.allMunicipalities = allMunicipalities;
	}
	
}
