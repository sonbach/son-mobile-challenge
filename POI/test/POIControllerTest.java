import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

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
}
