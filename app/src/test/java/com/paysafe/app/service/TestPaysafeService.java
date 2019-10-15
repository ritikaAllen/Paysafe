package com.paysafe.app.service;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.paysafe.app.entity.MonitoringData;
import com.paysafe.app.entity.Status;
import com.paysafe.app.repository.ConsolidatedMonitoringData;
import com.paysafe.app.repository.PaysafeMonitoringRepository;
import com.paysafe.app.repository.TestPaysafeRepository;

public class TestPaysafeService {

	@Autowired
	@Mock
	PaysafeMonitoringRepository repository;

	@Autowired
	@InjectMocks
	PaysafeService service = new PaysafeService();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestPaysafeRepository.testDataForMonitoringData();
	}

	@Test
	public void testFetchServerStatistics() {
		Mockito.when(repository.getAllServersData()).thenReturn(ConsolidatedMonitoringData.monitoringData);
		Map<String, MonitoringData> fetchServerStatistics = service.fetchServerStatistics();
		Assert.assertTrue(fetchServerStatistics.equals(ConsolidatedMonitoringData.monitoringData));
	}

	/*@Test
	public void testStartServerMonitoring() {
		ServerAttributes serverAttr = new ServerAttributes("url3", 200L);
		Mockito.when(repository.findIfMonitoringDataExistsForServer(anyString())).thenReturn(true);
		Mockito.when(repository.getAllServersData()).thenReturn(ConsolidatedMonitoringData.monitoringData);
		service.startServerMonitoring(serverAttr);
	}*/

	/*@Test
	public void testStopServerMonitoring() {
		service.stopServerMonitoring("url1");
		assertFalse(ConsolidatedMonitoringData.monitoringData.get("url1").isMonitoring());
		
	}*/

	public void testDataForMonitoringData() {
		String url1 = "url1";
		List<Status> statusData1 = new ArrayList<>();
		statusData1.add(new Status("READY", 1000L));
		statusData1.add(new Status("READY", 1000L));

		MonitoringData data1 = new MonitoringData(true, statusData1);

		String url2 = "url2";
		List<Status> statusData2 = new ArrayList<>();
		statusData2.add(new Status("READY", 3000L));
		statusData2.add(new Status("READY", 3000L));

		MonitoringData data2 = new MonitoringData(true, statusData2);

		ConsolidatedMonitoringData.monitoringData.put(url1, data1);
		ConsolidatedMonitoringData.monitoringData.put(url2, data2);

	}
}
