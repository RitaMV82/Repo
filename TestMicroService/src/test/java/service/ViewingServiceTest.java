package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.royalty.model.Episode;
import com.royalty.model.Viewing;
import com.royalty.repository.RoyaltyRepository;
import com.royalty.service.ServiceException;
import com.royalty.service.ViewingService;

@RunWith(SpringJUnit4ClassRunner.class)
public class ViewingServiceTest {
	
	@InjectMocks
	ViewingService viewingService;
	
	@Mock
	RoyaltyRepository royaltyRepository;
	
	@Test
	public void getAllViewingsNoEpisodes() throws ServiceException {
		when(royaltyRepository.getAllEpisodes()).thenReturn(emptyListEpisode());
		List<Viewing> result = viewingService.getAllViewing();

        assertNotNull(result);
        assertEquals(result.size(), 0);
	}
	
	@Test
	public void getAllViewingsWithEpisodes() throws ServiceException {
		when(royaltyRepository.getAllEpisodes()).thenReturn(createEpisodeList());
		List<Viewing> result = viewingService.getAllViewing();

		 assertNotNull(result);
	     assertEquals(result.size(), 2);
	     assertEquals(result.get(0).getViewings(), new Integer(1));	        
	}
	
	@Test
	public void getIncrementViewing() throws ServiceException {
		when(royaltyRepository.getAllEpisodes()).thenReturn(createEpisodeList());
		List<Viewing> result = viewingService.createViewing("episode1", "testCustomer");
		
		 assertNotNull(result);
	     assertEquals(result.size(), 1);
	     assertEquals(result.get(0).getViewings(), new Integer(2));	  
	}
	
	@Test
	public void getResetViewing() throws ServiceException {
		when(royaltyRepository.getAllEpisodes()).thenReturn(createEpisodeList());
		List<Viewing> result = viewingService.resetViewing();
		
		 assertNotNull(result);
	     assertEquals(result.size(), 2);
	     assertEquals(result.get(0).getViewings(), new Integer(0));	  
	}
	
	@Test(expected = ServiceException.class)
    public void testAllViewingsWithException() throws ServiceException {
        doThrow(new ArrayIndexOutOfBoundsException()).when(royaltyRepository).getAllEpisodes();
        viewingService.getAllViewing();
    }	
	
	
   private List<Episode> createEpisodeList() {
        Episode episode1 = createEpisode("episode1", "Game of Thrones S1:E1", "studio1");
        Episode episode2 = createEpisode("episode2", "Billions S1:E2", "studio3");

        return Arrays.asList(episode1, episode2);
    }

    private Episode createEpisode(String id, String name, String rightsowner) {
        Episode episode = new Episode();
        episode.setId(id);
        episode.setName(name);
        episode.setRightsowner(rightsowner);
        return episode;
    }

	private List<Episode> emptyListEpisode() {
		return new ArrayList<Episode>();
	}
}
