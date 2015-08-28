import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.son.poi.POI;
import com.son.poi.POIController;
import com.son.poi.POIModel;
import com.son.poi.POIView;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class POIControllerTest extends TestCase {
	private POIView poiView;
	private POIModel poiModel;
	private POIController poiController;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		poiView = mock(POIView.class);
		poiModel = mock(POIModel.class);
		poiController = new POIController(poiView, poiModel);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInitialize() {
		List<POI> pois = new ArrayList<POI>();
		when(poiModel.getPOIs()).thenReturn(pois);
		poiController.initialize();
		assertNotNull(poiController);
		verify(poiView).initializeAdapter(anyList());
		verify(poiModel).getPOIs();
	}

	public void testSortPOIs() {
		List<POI> pois = new ArrayList<POI>();
		pois.add(createPOI("Biz 1", "Address 1", 3));
		pois.add(createPOI("Biz 2", "Address 2", 1));
		pois.add(createPOI("Biz 3", "Address 3", 6));

		List<POI> actualPOIs = poiController.sortPOIs(pois);

		assertEquals(actualPOIs.get(0).getVisits(), 6);
		assertEquals(actualPOIs.get(1).getVisits(), 3);
		assertEquals(actualPOIs.get(2).getVisits(), 1);
	}

	private POI createPOI(String businessName, String address, int visits) {

		POI poi = new POI();
		poi.setAddress(address);
		poi.setBusinessName(businessName);
		poi.setVisits(visits);

		return poi;
	}
}
