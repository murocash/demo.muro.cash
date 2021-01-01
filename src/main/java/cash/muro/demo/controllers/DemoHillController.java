package cash.muro.demo.controllers;

import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import cash.muro.MuroPaging;
import cash.muro.annotations.MuroRequest;
import cash.muro.demo.repos.PeakRepository;
import cash.muro.entities.MuroSettings;

@Controller
public class DemoHillController {
	
	private static final String BASE_PATH = "/hills-within-50-miles-nyc";
	private static final String TITLE_ATTR = "title";

	@Autowired
	private PeakRepository peakRepository;
	
	@Autowired
	private MuroSettings muroSettings;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/")
	public String root() {
		return "redirect:" + BASE_PATH;
	}
	
	@GetMapping(BASE_PATH)
	@MuroRequest
	public ModelAndView listHills(ModelAndView mv, final Principal principal) throws UnknownHostException, URISyntaxException {
		mv.addObject(MuroSettings.MURO_LIST, peakRepository.findAllByOrderByElevationDescDistanceAscNameAsc(muroSettings.pageRequest(0)));
		mv.addObject(MuroSettings.MURO_PAGING, muroPaging(1));
		mv.addObject(TITLE_ATTR, title());
		mv.setViewName("main");
		return mv;
	}
	
	@MuroRequest
	@GetMapping(BASE_PATH + "/{page}")
	public ModelAndView listHills(ModelAndView mv, final Principal principal, @PathVariable int page) {
		mv.addObject(MuroSettings.MURO_LIST, peakRepository.findAllByOrderByElevationDescDistanceAscNameAsc(muroSettings.pageRequest(page - 1)));
		MuroPaging paging = muroPaging(page);
		mv.addObject(MuroSettings.MURO_PAGING, paging);
		mv.addObject(TITLE_ATTR, title() + " - " 
				+ messageSource.getMessage("muro-rows", new Long[] {paging.currentFirstRow(), paging.currentLastRow()}, LocaleContextHolder.getLocale()));
		mv.setViewName("main");
		return mv;
	}
	
	private MuroPaging muroPaging(int page) {
		return new MuroPaging(page, muroSettings.getRowsPerPage(), peakRepository.count(), BASE_PATH);
	}
	
	private String title() {
		return messageSource.getMessage("hills-title", null, LocaleContextHolder.getLocale());
	}
}
