package com.boot.music.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.View;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boot.music.dto.SongDTO;
import com.boot.music.entity.Account;
import com.boot.music.entity.Admin;
import com.boot.music.entity.Artist;
import com.boot.music.entity.Category;
import com.boot.music.entity.Region;
import com.boot.music.entity.Role;
import com.boot.music.entity.Song;
import com.boot.music.entity.User;
import com.boot.music.repositories.AccountRepo;
import com.boot.music.util.WFormater;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transaction;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
@Validated
@Controller

public class HomeController {
	@Autowired
	private AccountRepo accRepo;
	@Autowired
	WFormater w;
		EntityManagerFactory emf= Persistence.createEntityManagerFactory("music");
		EntityManager e=emf.createEntityManager();
//	@GetMapping("/home")
//	public ModelAndView home(ModelAndView model , HttpSession session) {
//		model= new ModelAndView("home");
//	
//		List<Category> l=e.createQuery("from Category",Category.class).getResultList();
//
//		model.addObject("cate_list",l);
//		
//		List<Song> l2=e.createQuery("from Song where category=1 ",Song.class).getResultList();
//
//		Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
//				+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
//				+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join Artist a where s.category=?1 ",SongDTO.class);
//		q.setParameter(1, l.get(0));
//		List<SongDTO> l4=q.setMaxResults(5).getResultList();
//		q.setParameter(1, l.get(2));
//		List<SongDTO> l5=q.setMaxResults(5).getResultList();
//		q.setParameter(1, l.get(4));
//	
//		List<SongDTO> l6=q.setMaxResults(5).getResultList();
//		model.addObject("cate1",l4);
//		model.addObject("cate2",l5);
//		model.addObject("cate3",l6);
//		return model;
//
//		}
	
	//test for thymeleaf
	@GetMapping("/home")
	public ModelAndView home(HttpSession session) {
		ModelAndView model= new ModelAndView("home");
		List<Category> l=e.createQuery("from Category",Category.class).getResultList();
		model.addObject("cate_list",l);
		List<Song> l2=e.createQuery("from Song where category=1 ",Song.class).getResultList();

		Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
				+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
				+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join s.artist a where  s.category=?1 order by s.id desc limit 5",SongDTO.class);
		q.setParameter(1, l.get(0));
		List<SongDTO> l4=q.setMaxResults(5).getResultList();
		q.setParameter(1, l.get(2));
		List<SongDTO> l5=q.setMaxResults(5).getResultList();
		q.setParameter(1, l.get(4));
		List<SongDTO> l6=q.setMaxResults(5).getResultList();
		
		model.addObject("format", w);
		model.addObject("cate1",l4);

		model.addObject("c1_name",l.get(0).getCategoryName());
		model.addObject("cate2",l5);
		model.addObject("c2_name",l.get(2).getCategoryName());
		model.addObject("cate3",l6);
		model.addObject("c3_name",l.get(4).getCategoryName());
		model.addObject("t",l);
		session.setAttribute("http", "/home");
//		Authentication a= SecurityContextHolder.getContext().getAuthentication();
//		boolean has=a.getAuthorities().stream().anyMatch(r->r.getAuthority().equals("1"));
//		System.out.println(a.getAuthorities());
//		System.out.println(has);
		return model;
	}
	
	}
