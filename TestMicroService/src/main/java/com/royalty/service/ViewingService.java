package com.royalty.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.royalty.model.Episode;
import com.royalty.model.Viewing;
import com.royalty.repository.RoyaltyRepository;
import com.royalty.service.interfaces.ViewingInterface;


@Component
public class ViewingService implements ViewingInterface {
	
	@Autowired
	RoyaltyRepository royaltyRepository;
		
	public synchronized  List<Viewing> createViewing(String idEpisode, String idCustomer) throws ServiceException {		
		try {
			return getAllViewing().stream()
						.filter(elem -> elem.hasEpisodeAndId())
						.filter(elem -> elem.getEpisode().getId().equals(idEpisode)).map(elem -> incrementViewings(elem)).collect(Collectors.toList());			
		}catch(Exception ex) {
			throw new ServiceException();
		}
	}
	
	private Viewing incrementViewings(Viewing viewing) {
	   viewing.setViewings(viewing.getViewings()+1);
	   return viewing;
	}
	
	public synchronized List<Viewing> resetViewing() throws ServiceException {
		try {			
			return getAllViewing().stream().map(elem -> resetViewings(elem)).collect(Collectors.toList());
		} catch(Exception e) {
			throw new ServiceException();
		}

	}
	
	private Viewing resetViewings(Viewing viewing){
		viewing.setViewings(0);
		return viewing;
	}
	
	public List<Viewing> getAllViewing() throws ServiceException {		
		List<Viewing> result = null;
		try {
			List<Episode> episodes = royaltyRepository.getAllEpisodes();
			result =  episodes.stream().map(elem -> new Viewing(elem)).collect(Collectors.toList());				
		} catch (Exception e) {
			throw new ServiceException();
		}
		return result;		
	}	

}
