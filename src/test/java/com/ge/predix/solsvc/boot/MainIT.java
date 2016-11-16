package com.ge.predix.solsvc.boot;


import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.Collections;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.ge.predix.entity.timeseries.datapoints.queryresponse.DatapointsResponse;
import com.ge.predix.solsvc.restclient.config.OauthRestConfig;
import com.ge.predix.solsvc.restclient.impl.RestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author predix -
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ComponentScan("com.ge.predix.solsvc.restClient")
@ActiveProfiles("local")
@IntegrationTest({"server.port=0"})
public class MainIT {
    
    @Value("${local.server.port}")
    private int localServerPort;
	
	private RestTemplate template;

	private static Logger log = LoggerFactory.getLogger(MainIT.class);
	
	@Autowired
	private OauthRestConfig restConfig;

	@Autowired
	private RestClient restClient;
	
	/**
	 * @throws Exception -
	 */
	@Before
	public void setUp() throws Exception {
		this.template = new TestRestTemplate();
	}

	/**
	 * @throws Exception -
	 */
	@SuppressWarnings("nls")
	@Test
	public void pingTest()throws Exception {
		URL iimotDataURl = new URL("http://localhost:" + this.localServerPort + "/iimot/request/ping");
		ResponseEntity<String> response = this.template.getForEntity(iimotDataURl.toString(), String.class);
		assertThat(response.getBody(), startsWith("Greetings from predix-engine"));
	}

	/**
	 * @throws Exception -
	 */
	@SuppressWarnings("nls")
	@Test
	public void testDailyWindData() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.put("Authorization", Collections.singletonList("testHeader"));

		
		URL iimotDataURl = new URL("http://localhost:" + this.localServerPort + "/iimot/request/yearly_data/sensor_id/Compressor-2015:CompressionRatio");
		
		ResponseEntity<DatapointsResponse> response = this.template.exchange(iimotDataURl.toString(), HttpMethod.GET, new HttpEntity<byte[]>(headers), DatapointsResponse.class);
			
		assertNotNull(response);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	
	/**
	 * @throws Exception -
	 */
	@SuppressWarnings("nls")
	@Test
	public void testLatestWindData() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.put("Authorization", Collections.singletonList("testHeader"));

		
		URL iimotDataURl = new URL("http://localhost:" + this.localServerPort + "/iimot/request/latest_data/sensor_id/Compressor-2015:CompressionRatio");
		
		ResponseEntity<DatapointsResponse> response = this.template.exchange(iimotDataURl.toString(), HttpMethod.GET, new HttpEntity<byte[]>(headers), DatapointsResponse.class);
			
		assertNotNull(response);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	/**
	 * @throws Exception -
	 */
	@SuppressWarnings("nls")
	@Test
	public void testDailyWindDataWithMultipleTags() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.put("Authorization", Collections.singletonList("testHeader"));

		
		URL iimotDataURl = new URL("http://localhost:" + this.localServerPort + "/iimot/request/yearly_data/sensor_id/RMD_metric3,RMD_metric2");
		
		ResponseEntity<DatapointsResponse> response = this.template.exchange(iimotDataURl.toString(), HttpMethod.GET, new HttpEntity<byte[]>(headers), DatapointsResponse.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertNotNull(response.getBody().getTags());
		assertEquals(2,response.getBody().getTags().size());
	}
	
	/**
	 * @throws Exception -
	 */
	@SuppressWarnings("nls")
	@Test
	public void testTagsData() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.put("Authorization", Collections.singletonList("testHeader"));

		
		URL iimotDataURl = new URL("http://localhost:" + this.localServerPort + "/iimot/request/tags");
		
		ResponseEntity<String> response = this.template.exchange(iimotDataURl.toString(), HttpMethod.GET, new HttpEntity<byte[]>(headers), String.class);
			
		assertNotNull(response);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}


	/**
	 * @throws Exception -
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFacilityAssets() throws Exception  {
		HttpHeaders headers = new HttpHeaders();
		headers.put("Authorization", Collections.singletonList("testHeader"));


		URL iimotDataURl = new URL("http://localhost:" + this.localServerPort + "/iimot/request/facility_assets");
		ResponseEntity<String> response = this.template.exchange(iimotDataURl.toString(), HttpMethod.GET, new HttpEntity<byte[]>(headers), String.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK,response.getStatusCode());


		JSONObject bodyJson = new JSONObject(response.getBody());
		JSONArray features = bodyJson.getJSONArray("features");
		assertEquals(features.length(),35);
	}


}
