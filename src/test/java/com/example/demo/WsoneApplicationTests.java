package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import com.api.zipcodes.bo.ZipCodeBO;
import com.api.zipcodes.init.NamespaceFilter;
import com.api.zipcodes.init.Root;
import com.api.zipcodes.init.Table;
import com.api.zipcodes.models.FederalEntity;
import com.api.zipcodes.models.Municipality;
import com.api.zipcodes.models.Settlement;
import com.api.zipcodes.models.ZipCode;
import com.api.zipcodes.repository.ZipCodeRepository;

@SpringBootTest(classes = {ZipCodeRepository.class, ZipCodeBO.class})
class WsoneApplicationTests {

	@Autowired
	private ZipCodeRepository zipCodeRepository;

	@Autowired
	private ZipCodeBO zipCodeBO;
	
	@Test
	void connectionEndPoint() throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("__VIEWSTATE",
				"/wEPDwUINzcwOTQyOTgPZBYCAgEPZBYCAgEPZBYGAgMPDxYCHgRUZXh0BTjDmmx0aW1hIEFjdHVhbGl6YWNpw7NuIGRlIEluZm9ybWFjacOzbjogSnVuaW8gMjcgZGUgMjAyMGRkAgcPEA8WBh4NRGF0YVRleHRGaWVsZAUDRWRvHg5EYXRhVmFsdWVGaWVsZAUFSWRFZG8eC18hRGF0YUJvdW5kZ2QQFSEjLS0tLS0tLS0tLSBUICBvICBkICBvICBzIC0tLS0tLS0tLS0OQWd1YXNjYWxpZW50ZXMPQmFqYSBDYWxpZm9ybmlhE0JhamEgQ2FsaWZvcm5pYSBTdXIIQ2FtcGVjaGUUQ29haHVpbGEgZGUgWmFyYWdvemEGQ29saW1hB0NoaWFwYXMJQ2hpaHVhaHVhEUNpdWRhZCBkZSBNw6l4aWNvB0R1cmFuZ28KR3VhbmFqdWF0bwhHdWVycmVybwdIaWRhbGdvB0phbGlzY28HTcOpeGljbxRNaWNob2Fjw6FuIGRlIE9jYW1wbwdNb3JlbG9zB05heWFyaXQLTnVldm8gTGXDs24GT2F4YWNhBlB1ZWJsYQpRdWVyw6l0YXJvDFF1aW50YW5hIFJvbxBTYW4gTHVpcyBQb3Rvc8OtB1NpbmFsb2EGU29ub3JhB1RhYmFzY28KVGFtYXVsaXBhcwhUbGF4Y2FsYR9WZXJhY3J1eiBkZSBJZ25hY2lvIGRlIGxhIExsYXZlCFl1Y2F0w6FuCVphY2F0ZWNhcxUhAjAwAjAxAjAyAjAzAjA0AjA1AjA2AjA3AjA4AjA5AjEwAjExAjEyAjEzAjE0AjE1AjE2AjE3AjE4AjE5AjIwAjIxAjIyAjIzAjI0AjI1AjI2AjI3AjI4AjI5AjMwAjMxAjMyFCsDIWdnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2RkAh0PPCsACwBkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQtidG5EZXNjYXJnYf0cUJO59MPXEw9x3J+MtBqRxJAW");
		params.put("__VIEWSTATEGENERATOR", "BE1A6D2E");
		params.put("__EVENTVALIDATION",
				"/wEWKAL53rTeBgLG/OLvBgLWk4iCCgLWk4SCCgLWk4CCCgLWk7yCCgLWk7iCCgLWk7SCCgLWk7CCCgLWk6yCCgLWk+iBCgLWk+SBCgLJk4iCCgLJk4SCCgLJk4CCCgLJk7yCCgLJk7iCCgLJk7SCCgLJk7CCCgLJk6yCCgLJk+iBCgLJk+SBCgLIk4iCCgLIk4SCCgLIk4CCCgLIk7yCCgLIk7iCCgLIk7SCCgLIk7CCCgLIk6yCCgLIk+iBCgLIk+SBCgLLk4iCCgLLk4SCCgLLk4CCCgLL+uTWBALa4Za4AgK+qOyRAQLI56b6CwL1/KjtBfYSWbLssuB9p3z/0IcYlZjZOZSx");
		params.put("cboEdo", "06");
		params.put("rblTipo", "xml");
		params.put("btnDescarga.x", "44");
		params.put("btnDescarga.y", "10");

		byte[] postData = this.getDataString(params).getBytes(StandardCharsets.UTF_8);
		URL url = new URL("https://www.correosdemexico.gob.mx/SSLServicios/ConsultaCP/CodigoPostal_Exportar.aspx");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
		conn.setUseCaches(false);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.write(postData);
		assertEquals(200, conn.getResponseCode());

	}
	
	
	@Test
	void unmarshal() throws JAXBException, SAXException, FileNotFoundException {
		JAXBContext jc = JAXBContext.newInstance(Root.class, Table.class);
		Unmarshaller u = jc.createUnmarshaller();

		// Create an XMLReader to use with our filter [ xmlns = "NewData"]
		XMLReader reader = XMLReaderFactory.createXMLReader();

		// Create the filter (to add namespace) and set the xmlReader as its parent.
		NamespaceFilter inFilter = new NamespaceFilter(null, false);
		inFilter.setParent(reader);

		// Prepare the input, in this case a java.io.File (output)
		InputSource is = new InputSource(new FileInputStream(new File("example.xml")));

		// Create a SAXSource specifying the filter
		SAXSource source = new SAXSource(inFilter, is);

		// unmarshalling
		Root rootZipCodes = (Root) u.unmarshal(source);

		assertEquals(1, rootZipCodes.getTables().size());
		
	}
	
	
	@Test
	void getAddress()  {
		
		Municipality mun = new Municipality();
		List<String> codes = new ArrayList<>();
		codes.add("06140");
		mun.setCestado(new FederalEntity("09", "CIUDAD DE MEXICO"));
		mun.setLocality("CIUDAD DE MEXICO");
		mun.setMunicipality("CUAUHTEMOC");
		mun.setMnpio("02");
		mun.setZipCodes(codes);
		List<Settlement> settles = new ArrayList<>();
		settles.add(new Settlement("092", "CONDESA", "Urbano", "Colonia", "06140"));
		mun.setSettlements(settles);
		List<Municipality> municipalities = new ArrayList<>();
		municipalities.add(mun);
		this.zipCodeRepository.setMunicipalities(municipalities);
		
		ZipCode zipcode = this.zipCodeBO.getAddressByZipCode("06140");
		assertEquals(zipcode.getSettlements().size(), 1);
		
	}
	
	
	private String getDataString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (first)
				first = false;
			else
				result.append("&");
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}
		return result.toString();
	}
	

}
