package com.paysafe.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.paysafe.app.controller.PaysafeController;
import com.paysafe.app.dto.ServerAttributes;
import com.paysafe.app.entity.MonitoringData;
import com.paysafe.app.entity.Status;
import com.paysafe.app.service.PaysafeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PaysafeController.class)
@ContextConfiguration(classes={PaysafeService.class})
public class TestPaysafeController {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	@Autowired
	private PaysafeService service;

	Map<String, MonitoringData> mockStatusData = new HashMap<String, MonitoringData>();

	@MockBean
	private ServerAttributes serverRequestAttributes;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	//TODO
	@Test
	public void testMonitoringData() throws Exception {
		List<Status> statuslist = new ArrayList<>();
		statuslist.add(new Status("READY", 100L));
		mockStatusData.put("sampleurl", new MonitoringData(true, statuslist));
		//Mockito.when(service.fetchServerStatistics()).thenReturn(mockStatusData);
		String serverAtt = "{\"url\": \"sampleurl\",\"interval\": \"10000\"}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/paysafe-api/monitor/start")
				.contentType(MediaType.APPLICATION_JSON).content(serverAtt).characterEncoding("UTF-8").param("url", "sample").param("interval", "100");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());

		String expected = "";

		//JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

}
