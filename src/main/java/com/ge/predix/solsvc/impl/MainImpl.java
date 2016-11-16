package com.ge.predix.solsvc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Coordinate;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.geotools.geojson.GeoJSON;
import org.geotools.geojson.geom.GeometryJSON;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.predix.entity.timeseries.datapoints.ingestionrequest.Body;
import com.ge.predix.entity.timeseries.datapoints.ingestionrequest.DatapointsIngestion;
import com.ge.predix.entity.timeseries.datapoints.queryrequest.DatapointsQuery;
import com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.DatapointsLatestQuery;
import com.ge.predix.entity.timeseries.datapoints.queryresponse.DatapointsResponse;
import com.ge.predix.solsvc.restclient.impl.RestClient;
import com.ge.predix.solsvc.spi.IServiceManagerService;
import com.ge.predix.solsvc.timeseries.bootstrap.config.TimeseriesRestConfig;
import com.ge.predix.solsvc.timeseries.bootstrap.config.TimeseriesWSConfig;
import com.ge.predix.solsvc.timeseries.bootstrap.factories.TimeseriesFactory;
import com.neovisionaries.ws.client.WebSocketAdapter;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
/**
 * 
 * @author predix -
 */
@Component
public class MainImpl implements com.ge.predix.solsvc.api.MainAPI {

	@Autowired
	private IServiceManagerService serviceManagerService;

	@Autowired
	private TimeseriesRestConfig timeseriesRestConfig;

	@Autowired
	private RestClient restClient;

	@Autowired
	private TimeseriesWSConfig tsInjectionWSConfig;


	@Autowired
	private TimeseriesFactory timeseriesFactory;


	private static Logger log = LoggerFactory.getLogger(MainImpl.class);

	/**
	 * -
	 */
	public MainImpl() {
		super();
	}

	/**
	 * -
	 */
	@PostConstruct
	public void init() {
		this.serviceManagerService.createRestWebService(this, null);
		List<Header> headers = generateHeaders();
		headers.add(new BasicHeader("Origin", "http://predix.io"));
		WebSocketAdapter nullListener = null;
        this.timeseriesFactory.createConnectionToTimeseriesWebsocket(nullListener);
		createMetrics(headers);
	}

	public void fa() throws IOException {

        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(1,2));

		GeoJSON gs = new GeoJSON();

        GeometryJSON g = new GeometryJSON();
		String gm =g.toString(point);
		Geometry point1 = new GeometryJSON().read(gm);
		log.debug(g.toString(point));

		//GeoJSONUtil gju = new GeoJSONUtil();

		//g.write(point,gju.toWriter(gs));



        //Point point2 = g.readPoint(gs);
        log.debug("OK");
		return;
//        JSONObject jo = new JSONObject();
//        int a = 10;
//        try {
//            jo.put("name", "somebody");
//        }
//        catch (JSONException e)  {
//            log.debug(e.getMessage());
//        }
//
//		GeometryJSON gjson = new GeometryJSON();
//        gjson.
//// be sure to strip whitespace
//		String json = "{\"type\":'Point','coordinates':[100.1,0.1]}";
//
//		Reader reader = new StringReader(json);
//		Point p = gjson.readPoint( reader );
	}

	public JSONObject testAsset() throws IOException,JSONException {
		GeometryFactory gf = new GeometryFactory();
		Point point = gf.createPoint(new Coordinate(1,2));
		GeometryJSON gjson = new GeometryJSON();

		JSONObject properties = new JSONObject();
		properties.put("AssetID","ASSET307");
		JSONObject jo = new JSONObject();
		jo.put("type","Feature");
		jo.put("properties",properties);
		jo.put("geometry", gjson.toString(point));

		return jo;
	}

	public JSONObject testAssets() throws IOException,JSONException {
		GeometryFactory gf = new GeometryFactory();
		GeometryJSON gjson = new GeometryJSON(16);
		JSONArray features = new JSONArray();

		double[] Lat ={ 				141.0136842727661,
				141.01381301879883,
				141.01372718811035,
				141.01364135742188,
				141.0164737701416,
				141.0164737701416,
				141.0163450241089,
				141.0163450241089,
				141.0183620452881,
				141.01806163787842,
				141.01874828338623,
				141.0195207595825,
				141.0190486907959,
				141.0114312171936,
				141.01104497909546,
				141.01070165634155,
				141.01149559020996,
				141.01123809814453,
				141.01102352142334,
				141.0114312171936,
				141.01113080978394,
				141.01080894470215,
				141.01044416427612,
				141.01046562194824,
				141.01499319076538,
				141.01499319076538,
				141.01495027542114,
				141.01492881774902,
				141.0149073600769,
				141.01387739181519,
				141.01383447647095,
				141.01381301879883,
				141.01572275161743,
				141.01574420928955,
				141.01574420928955};
		double[] Lon = {37.2361369986573,
				37.2395877850641,
				37.2383919858785,
				37.2371620012111,
				37.2395536196364,
				37.2384944836949,
				37.2371620012111,
				37.2360003305971,
				37.2403735856284,
				37.2394511232602,
				37.2395194541932,
				37.2399636037474,
				37.2390411363617,
				37.2304479811668,
				37.2304821507215,
				37.2304992354929,
				37.2302087938511,
				37.2302258786845,
				37.2302258786845,
				37.2299866906643,
				37.2296962470482,
				37.2297304169435,
				37.2300550301760,
				37.2297304169435,
				37.2341894554091,
				37.2333352539016,
				37.2325493799669,
				37.2318660046695,
				37.2310801154202,
				37.2339673639482,
				37.2330106547911,
				37.2318489202077,
				37.2342407071918,
				37.2333010856400,
				37.2324297897370};

		for (int i=0; i < Lat.length;i++) {
			Point point = gf.createPoint(new Coordinate(Lat[i], Lon[i]));
			JSONObject properties = new JSONObject();
			properties.put("AssetID", "ASSET" + Integer.toString(307+i));
			JSONObject jo = new JSONObject();
			jo.put("type", "Feature");
			jo.put("properties", properties);
			JSONObject geometry = new JSONObject(gjson.toString(point));
			jo.put("geometry", geometry);
			features.put(jo);
		}

		JSONObject featureCollection = new JSONObject();
		featureCollection.put("type", "FeatureCollection");
		featureCollection.put("features",features);

		return featureCollection;
	}

	@Override
	public Response facilityAssets() throws JSONException {

		ResponseBuilder responseBuilder = Response.status(Status.OK);

		responseBuilder.type(MediaType.APPLICATION_JSON);
		try {
			JSONObject jo = this.testAssets();
			responseBuilder.entity(jo.toString());
		} catch (IOException e) {
			log.debug("ERROR:" + e.getMessage());
		}

		return responseBuilder.build();
	}

	@Override
	public Response greetings() {
		return handleResult("Greetings from predix-engine : " + new Date()); //$NON-NLS-1$
	}

	@Override
	public Response getYearlyWindDataPoints(String id, String authorization,String starttime,String taglimit,String tagorder) {
		if (id == null) {
			return null;
		}
		
		List<Header> headers = generateHeaders();

		DatapointsQuery dpQuery = buildDatapointsQueryRequest(id, starttime,getInteger(taglimit),tagorder);
		DatapointsResponse response = this.timeseriesFactory
				.queryForDatapoints(this.timeseriesRestConfig.getBaseUrl(),
						dpQuery, headers);
		log.debug(response.toString());

		return handleResult(response);
	}

	/**
	 * 
	 * @param s -
	 * @return
	 */
	private int getInteger(String s) {
		int inValue = 25;
		try {
			inValue = Integer.parseInt(s);
			
		} catch (NumberFormatException ex) {
			// s is not an integer
		}
		return inValue;
	}

	@Override
	public Response getLatestWindDataPoints(String id, String authorization) {
		if (id == null) {
			return null;
		}
		List<Header> headers = generateHeaders();

		DatapointsLatestQuery dpQuery = buildLatestDatapointsQueryRequest(id);
		DatapointsResponse response = this.timeseriesFactory
				.queryForLatestDatapoint(
						this.timeseriesRestConfig.getBaseUrl(), dpQuery,
						headers);
		log.debug(response.toString());

		return handleResult(response);
	}

	@SuppressWarnings({ "unqualified-field-access", "nls" })
	private List<Header> generateHeaders()
    {
        List<Header> headers = this.restClient.getSecureTokenForClientId();
		this.restClient.addZoneToHeaders(headers,
				this.timeseriesRestConfig.getZoneId());
        return headers;
    }


	private DatapointsLatestQuery buildLatestDatapointsQueryRequest(String id) {
		DatapointsLatestQuery datapointsLatestQuery = new DatapointsLatestQuery();

		com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag tag = new com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag();
		tag.setName(id);
		List<com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag> tags = new ArrayList<com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag>();
		tags.add(tag);
		datapointsLatestQuery.setTags(tags);
		return datapointsLatestQuery;
	}

	/**
	 * 
	 * @param id
	 * @param startDuration
	 * @param tagorder 
	 * @return
	 */
	private DatapointsQuery buildDatapointsQueryRequest(String id,
			String startDuration, int taglimit, String tagorder) {
		DatapointsQuery datapointsQuery = new DatapointsQuery();
		List<com.ge.predix.entity.timeseries.datapoints.queryrequest.Tag> tags = new ArrayList<com.ge.predix.entity.timeseries.datapoints.queryrequest.Tag>();
		datapointsQuery.setStart(startDuration);
		//datapointsQuery.setStart("1y-ago"); //$NON-NLS-1$
		String[] tagArray = id.split(","); //$NON-NLS-1$
		List<String> entryTags = Arrays.asList(tagArray);

		for (String entryTag : entryTags) {
			com.ge.predix.entity.timeseries.datapoints.queryrequest.Tag tag = new com.ge.predix.entity.timeseries.datapoints.queryrequest.Tag();
			tag.setName(entryTag);
			tag.setLimit(taglimit);
			tag.setOrder(tagorder); 
			tags.add(tag);
		}
		datapointsQuery.setTags(tags);
		return datapointsQuery;
	}

	@SuppressWarnings({ "nls", "unchecked" })
	private void createMetrics(List<Header> headers) {
		for (int i = 0; i < 10; i++) {
			DatapointsIngestion dpIngestion = new DatapointsIngestion();
			dpIngestion
					.setMessageId(String.valueOf(System.currentTimeMillis()));

			Body body = new Body();
			body.setName("Compressor-2015:CompressionRatio"); //$NON-NLS-1$
			List<Object> datapoint1 = new ArrayList<Object>();
			datapoint1.add(generateTimestampsWithinYear(System
					.currentTimeMillis()));
			datapoint1.add(10);
			datapoint1.add(3); // quality

			List<Object> datapoint2 = new ArrayList<Object>();
			datapoint2.add(generateTimestampsWithinYear(System
					.currentTimeMillis()));
			datapoint2.add(9);
			datapoint2.add(1); // quality

			List<Object> datapoint3 = new ArrayList<Object>();
			datapoint3.add(generateTimestampsWithinYear(System
					.currentTimeMillis()));
			datapoint3.add(27);
			datapoint3.add(0); // quality

			List<Object> datapoint4 = new ArrayList<Object>();
			datapoint4.add(generateTimestampsWithinYear(System
					.currentTimeMillis()));
			datapoint4.add(78);
			datapoint4.add(2); // quality

			List<Object> datapoint5 = new ArrayList<Object>();
			datapoint5.add(generateTimestampsWithinYear(System
					.currentTimeMillis()));
			datapoint5.add(2);
			datapoint5.add(3); // quality

			List<Object> datapoint6 = new ArrayList<Object>();
			datapoint6.add(generateTimestampsWithinYear(System
					.currentTimeMillis()));
			datapoint6.add(98);
			datapoint6.add(1); // quality

			List<Object> datapoints = new ArrayList<Object>();
			datapoints.add(datapoint1);
			datapoints.add(datapoint2);
			datapoints.add(datapoint3);
			datapoints.add(datapoint4);
			datapoints.add(datapoint5);
			datapoints.add(datapoint6);

			body.setDatapoints(datapoints);

			com.ge.predix.entity.util.map.Map map = new com.ge.predix.entity.util.map.Map();
			map.put("host", "server1"); //$NON-NLS-2$
			map.put("customer", "Acme"); //$NON-NLS-2$

			body.setAttributes(map);

			List<Body> bodies = new ArrayList<Body>();
			bodies.add(body);

			dpIngestion.setBody(bodies);
			this.timeseriesFactory.postDataToTimeseriesWebsocket(dpIngestion);
		}
	}

	@SuppressWarnings("javadoc")
	protected Response handleResult(Object entity) {
		ResponseBuilder responseBuilder = Response.status(Status.OK);
		responseBuilder.type(MediaType.APPLICATION_JSON);
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	private Long generateTimestampsWithinYear(Long current) {
		long yearInMMS = Long.valueOf(31536000000L);
		return ThreadLocalRandom.current().nextLong(current - yearInMMS,
				current + 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ge.predix.solsvc.api.MainImpl#getWindDataTags()
	 */
	@Override
	public Response getWindDataTags(String authorization) {
		List<Header> headers = generateHeaders();
		CloseableHttpResponse httpResponse = null;
		String entity = null;
		try {
			httpResponse = this.restClient
					.get(this.timeseriesRestConfig.getBaseUrl() + "/v1/tags", headers, this.timeseriesRestConfig.getTimeseriesConnectionTimeout(), this.timeseriesRestConfig.getTimeseriesSocketTimeout()); //$NON-NLS-1$

			if (httpResponse.getEntity() != null) {
				try {
					entity = processHttpResponseEntity(httpResponse.getEntity());
					log.debug("HttpEntity returned from Tags" + httpResponse.getEntity().toString()); //$NON-NLS-1$
				} catch (IOException e) {
					throw new RuntimeException(
							"Error occured calling=" + this.timeseriesRestConfig.getBaseUrl() + "/v1/tags", e); //$NON-NLS-1$//$NON-NLS-2$
				}
			}
		} finally {
			if (httpResponse != null)
				try {
					httpResponse.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}

		return handleResult(entity);
	}

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("nls")
	private String processHttpResponseEntity(org.apache.http.HttpEntity entity)
			throws IOException {
		if (entity == null)
			return null;
		if (entity instanceof GzipDecompressingEntity) {
			return IOUtils.toString(
					((GzipDecompressingEntity) entity).getContent(), "UTF-8");
		}
		return EntityUtils.toString(entity);
	}


}
