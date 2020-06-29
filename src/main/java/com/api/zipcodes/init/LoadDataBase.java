package com.api.zipcodes.init;

import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.api.zipcodes.models.FederalEntity;
import com.api.zipcodes.models.Municipality;
import com.api.zipcodes.models.Settlement;
import com.api.zipcodes.repository.ZipCodeRepository;

@Configuration
public class LoadDataBase {

	private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);

	@Bean
	CommandLineRunner initData(ZipCodeRepository zipCodeRepository) {

		return args -> {

			// parameters for service invocation
			Map<String, String> params = new HashMap<String, String>();
			params.put("__VIEWSTATE",
					"/wEPDwUINzcwOTQyOTgPZBYCAgEPZBYCAgEPZBYGAgMPDxYCHgRUZXh0BTjDmmx0aW1hIEFjdHVhbGl6YWNpw7NuIGRlIEluZm9ybWFjacOzbjogSnVuaW8gMjcgZGUgMjAyMGRkAgcPEA8WBh4NRGF0YVRleHRGaWVsZAUDRWRvHg5EYXRhVmFsdWVGaWVsZAUFSWRFZG8eC18hRGF0YUJvdW5kZ2QQFSEjLS0tLS0tLS0tLSBUICBvICBkICBvICBzIC0tLS0tLS0tLS0OQWd1YXNjYWxpZW50ZXMPQmFqYSBDYWxpZm9ybmlhE0JhamEgQ2FsaWZvcm5pYSBTdXIIQ2FtcGVjaGUUQ29haHVpbGEgZGUgWmFyYWdvemEGQ29saW1hB0NoaWFwYXMJQ2hpaHVhaHVhEUNpdWRhZCBkZSBNw6l4aWNvB0R1cmFuZ28KR3VhbmFqdWF0bwhHdWVycmVybwdIaWRhbGdvB0phbGlzY28HTcOpeGljbxRNaWNob2Fjw6FuIGRlIE9jYW1wbwdNb3JlbG9zB05heWFyaXQLTnVldm8gTGXDs24GT2F4YWNhBlB1ZWJsYQpRdWVyw6l0YXJvDFF1aW50YW5hIFJvbxBTYW4gTHVpcyBQb3Rvc8OtB1NpbmFsb2EGU29ub3JhB1RhYmFzY28KVGFtYXVsaXBhcwhUbGF4Y2FsYR9WZXJhY3J1eiBkZSBJZ25hY2lvIGRlIGxhIExsYXZlCFl1Y2F0w6FuCVphY2F0ZWNhcxUhAjAwAjAxAjAyAjAzAjA0AjA1AjA2AjA3AjA4AjA5AjEwAjExAjEyAjEzAjE0AjE1AjE2AjE3AjE4AjE5AjIwAjIxAjIyAjIzAjI0AjI1AjI2AjI3AjI4AjI5AjMwAjMxAjMyFCsDIWdnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2RkAh0PPCsACwBkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQtidG5EZXNjYXJnYf0cUJO59MPXEw9x3J+MtBqRxJAW");
			params.put("__VIEWSTATEGENERATOR", "BE1A6D2E");
			params.put("__EVENTVALIDATION",
					"/wEWKAL53rTeBgLG/OLvBgLWk4iCCgLWk4SCCgLWk4CCCgLWk7yCCgLWk7iCCgLWk7SCCgLWk7CCCgLWk6yCCgLWk+iBCgLWk+SBCgLJk4iCCgLJk4SCCgLJk4CCCgLJk7yCCgLJk7iCCgLJk7SCCgLJk7CCCgLJk6yCCgLJk+iBCgLJk+SBCgLIk4iCCgLIk4SCCgLIk4CCCgLIk7yCCgLIk7iCCgLIk7SCCgLIk7CCCgLIk6yCCgLIk+iBCgLIk+SBCgLLk4iCCgLLk4SCCgLLk4CCCgLL+uTWBALa4Za4AgK+qOyRAQLI56b6CwL1/KjtBfYSWbLssuB9p3z/0IcYlZjZOZSx");
			params.put("cboEdo", "00");
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

			log.info(
					"--- trying to connect to https://www.correosdemexico.gob.mx/SSLServicios/ConsultaCP/CodigoPostal_Exportar.aspx ---");

			log.info("http status response " + conn.getResponseCode());

			if (conn.getResponseCode() == 200) {

				log.info("writing zip file");
				InputStream is = conn.getInputStream();
				String filePath = "CPdescargaxml.zip";
				FileOutputStream fos = new FileOutputStream(new File(filePath));
				int inByte;
				while ((inByte = is.read()) != -1)
					fos.write(inByte);
				is.close();
				fos.close();

				log.info("Zip file created");

				ZipFile zf = new ZipFile("CPdescargaxml.zip");
				
				@SuppressWarnings("unchecked")
				Enumeration<ZipEntry> zipEnm = (Enumeration<ZipEntry>) zf.entries();
				StringBuilder sbuilder = new StringBuilder();
				while (zipEnm.hasMoreElements()) {
					ZipEntry ze = zipEnm.nextElement();
					BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
					String content;
					while ((content = br.readLine()) != null) {
						sbuilder.append(content);
					}
				}
				zf.close();

				//unmarsaller XML File to Root Object
				JAXBContext jc = JAXBContext.newInstance(Root.class, Table.class);
				Unmarshaller u = jc.createUnmarshaller();
				
				//Replace xmlns of tag table
				StringReader reader = new StringReader(sbuilder.toString().replaceAll("xmlns=\"NewDataSet\"", ""));
				Root rootZipCodes = (Root) u.unmarshal(reader);
				
				log.info("zipCodes size " + rootZipCodes.getTables().size());
			
				// get all distinct states
				Stream<Table> tablesSatate = rootZipCodes.getTables().stream()
						.filter(distinctByKey(Table::getD_estado));

				List<FederalEntity> entitys = tablesSatate
						.map(table -> new FederalEntity(table.getC_estado(), table.getD_estado()))
						.collect(Collectors.toList());

				// object that contain the entire universe of municipality
				List<Municipality> allMunicipalities = new ArrayList<>();

				log.info("loading data...");

				
				entitys.forEach(entity -> {

					List<Municipality> currentMunicipalities = rootZipCodes.getTables().stream().filter(t -> {
						return t.getC_estado().equals(entity.getEntity());
					}).filter(distinctByKey(Table::getC_mnpio)).map(table -> new Municipality(new ArrayList<>(),
							table.getD_mnpio(), table.getC_mnpio(), entity, new ArrayList<>(), table.getD_ciudad()))
							.collect(Collectors.toList());

					currentMunicipalities.forEach(municipality -> {

						// get settlements by municipality
						municipality.setSettlements(rootZipCodes.getTables().stream().filter(t -> {
							return t.getC_estado().equals(entity.getEntity())
									&& t.getC_mnpio().equals(municipality.getMnpio());
						}).map(table -> new Settlement(table.getId_asenta_cpcons(), table.getD_asenta(),
								table.getD_zona(), table.getD_tipo_asenta(),
								table.getD_codigo()))
								.collect(Collectors.toList()));

						// get zipcodes by municipality for fast search
						municipality.setZipCodes(rootZipCodes.getTables().stream().filter(t -> {
							return t.getC_estado().equals(entity.getEntity())
									&& t.getC_mnpio().equals(municipality.getMnpio());
						}).filter(distinctByKey(Table::getD_codigo))
								.map(table -> table.getD_codigo()).collect(Collectors.toList()));

					});

					allMunicipalities.addAll(currentMunicipalities);
				});
				zipCodeRepository.setMunicipalities(allMunicipalities);
				log.info("Finished load data");
				 
			} else {
				log.error("Error trying to conect to correos de México");
				throw new ConnectException();
			}
		};

	}

	/**
	 * Filter by key
	 * 
	 * @param <T>
	 * @param keyExtractor
	 * @return
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	/**
	 * Convert Map of params in string
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
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
