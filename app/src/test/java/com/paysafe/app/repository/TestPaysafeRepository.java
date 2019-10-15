package com.paysafe.app.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.paysafe.app.entity.MonitoringData;
import com.paysafe.app.entity.Status;

public class TestPaysafeRepository {

	@Autowired
	PaysafeMonitoringRepository repository = new PaysafeMonitoringRepository();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		testDataForMonitoringData();
	}

	@Test
	public void testFIND_IF_MONITORING_DATA_EXISTS_FOR_SERVER() {
		assertTrue(repository.findIfMonitoringDataExistsForServer("url1"));
		assertFalse(repository.findIfMonitoringDataExistsForServer("abc"));
	}

	@Test
	public void testGET_MONITORING_DATA_FOR_SERVER() {
		assertTrue(repository.getMonitoringDataForServer("url1")
				.equals(ConsolidatedMonitoringData.monitoringData.get("url1")));
		assertNull(repository.getMonitoringDataForServer("abcd"));
	}

	@Test
	public void testSET_MONITORING_FOR_SERVER() {
		repository.setMonitoringForServer("url1", false);
		assertFalse(repository.getMonitoringDataForServer("url1").isMonitoring());
	}

	@Test
	public void testADD_NEW_SERVICE_FOR_MONITORING() {
		repository.addNewServiceForMonitoring("url3");
		assertTrue(repository.findIfMonitoringDataExistsForServer("url3"));
		assertFalse(repository.getMonitoringDataForServer("url3").isMonitoring());

		repository.addNewServiceForMonitoring("url4", true);
		assertTrue(repository.findIfMonitoringDataExistsForServer("url4"));
		assertTrue(repository.getMonitoringDataForServer("url4").isMonitoring());
	}

	@Test
	public void testIF_SERVER_IS_BEING_MONITORED() {
		assertFalse(repository.findIfServerIsBeingMonitored("url3"));
		assertTrue(repository.findIfServerIsBeingMonitored("url4"));

	}

	public static void testDataForMonitoringData() {
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
