package com.boot.music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.boot.music.dto.SongDTO;
import com.boot.music.entity.Category;
import com.boot.music.repositories.AccountRepo;
import com.boot.music.util.WFormater;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
@Validated
@Controller
@RequestMapping("/")
public class SearchController {
	@Autowired
	private AccountRepo accRepo;
	@Autowired
	WFormater w;
		EntityManagerFactory emf= Persistence.createEntityManagerFactory("music");
		EntityManager e=emf.createEntityManager();
		//search bar
	@GetMapping("/search")
	public @ResponseBody String autoFill(@RequestParam String s) {
		Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
				+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
				+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join Artist a where (s.title like ?1 or a.name like ?2) ",SongDTO.class);
		s="%"+s+"%";
		q.setParameter(1, s);
		q.setParameter(2, s);
		List<SongDTO> li=q.setMaxResults(12).getResultList();
		String res="<ul>\r\n";
	
		for (SongDTO songDTO : li) {
			String detail= songDTO.getTitle()+": "+songDTO.getArtistName(); 

			res+="              		<li><a class=\"res\" href=\"http://localhost:8080/song/song?id="+songDTO.getSongID()+"\">"+detail+"</a></li>";
			
		}
		res+=	 "              		</ul>";

		return res;
	}
	@GetMapping("/allSongs")
	public ModelAndView all(HttpSession session, ModelAndView model, @RequestParam String p) {
		model= new ModelAndView("list");
		Integer pi;
		try {
			pi=Integer.parseInt(p);
		} catch (Exception e) {
			
			pi=1;
		}
		if(pi<=0) {
			pi=1;
		}
		
		Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
				+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
				+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join s.artist a order by s.id ",SongDTO.class);
		List<SongDTO> l=q.getResultList();
		int size=l.size();
		

		int pageSplit=size/20;
		
		if(size%20!=0) {
			pageSplit++;
		}
		if(pi>pageSplit) {
			pi=pageSplit;
			
		}
		if(pi*20>=size) {
			l=l.subList((pi-1)*20, size);
			
		}
		if (pi*20<size){
			l=l.subList((pi-1)*20, pi*20);
			
		}
		model.addObject("totalPages",pageSplit);
		model.addObject("currentPage",pi);
		model.addObject("href","http://localhost:8080/allSongs");
		model.addObject("list",l);
		List<Category> cate=e.createQuery("from Category",Category.class).getResultList();
		model.addObject("cate",cate);
		model.addObject("searchValue","Tất cả bài hát");
		model.addObject("format", w);
	//	session.setAttribute("http", "/allSongs?p="+p);
		return model;
	}
	@GetMapping("/searchBy")
	public ModelAndView searchList(HttpSession session ,ModelAndView model, @RequestParam String s, @RequestParam String p) {
		model= new ModelAndView("list");
		
		Integer pi;
		try {
			pi=Integer.parseInt(p);
		} catch (NumberFormatException e) {
			
			pi=1;
		}
		if(pi<=0) {
			pi=1;
		}
		model.addObject("searchValue","Tác giả hoặc Tiêu đề chứa "+s);
		Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
				+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
				+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join Artist a where (s.title like ?1 or a.name like ?2) ",SongDTO.class);
		s="%"+s+"%";
		q.setParameter(1, s);
		q.setParameter(2, s);
		List<SongDTO> l=q.getResultList();
		
		if(l.size()>0) {
			int size=l.size();
			

			int pageSplit=size/20;
		
			if(size%20!=0) {
				pageSplit++;
			}
			if(pi>pageSplit) {
				pi=pageSplit;
				
			}
			if(pi*20>=size) {
				l=l.subList((pi-1)*20, size);
				
			}
			if (pi*20<size){
				l=l.subList((pi-1)*20, pi*20);
				
			}
		model.addObject("totalPages",pageSplit);
		
		}
		else {
			model=new ModelAndView("notfound");
			model.addObject("totalPages",0);
			s=s.substring(1,s.length()-1);
			model.addObject("searchValue"," từ khóa "+s);
			
		}
		model.addObject("format", w);
		model.addObject("currentPage",pi);
		model.addObject("list",l);
		List<Category> cate=e.createQuery("from Category",Category.class).getResultList();
		model.addObject("cate",cate);
		s=s.substring(1,s.length()-1);
		model.addObject("href","http://localhost:8080/searchBy?s="+s);
		return model;
	}
	@GetMapping("/byCategory")
	public ModelAndView list(HttpSession session, ModelAndView m, @RequestParam String c,@RequestParam String p) {
		m= new ModelAndView("list");
		Integer pi;
		Integer ci;
		try {
			pi=Integer.parseInt(p);
		} catch (NumberFormatException e) {
			
			pi=1;
		}
		try {
			ci=Integer.parseInt(c);
		} catch (NumberFormatException e) {
			
			ci=1;
		}
		
		if(pi<=0) {
			pi=1;
		}
		if(ci<=0) {
			ci=1;
		}
	
		Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
				+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
				+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join Artist a where s.category.id=?1 ",SongDTO.class);
		q.setParameter(1, ci);
		List<SongDTO> l=q.getResultList();
		if(l.size()>0) {
			int size=l.size();
			int pageSplit=size/20;
		
			if(size%20!=0) {
				pageSplit++;
			}
			if(pi>pageSplit) {
				pi=pageSplit;
				
			}
			if(pi*20>=size) {
				l=l.subList((pi-1)*20, size);
				
			}
			if (pi*20<size){
				l=l.subList((pi-1)*20, pi*20);
				
			}
			
			m.addObject("list",l);
			m.addObject("totalPages",pageSplit);
			m.addObject("currentPage",pi);
			List<Category> cate=e.createQuery("from Category",Category.class).getResultList();
			m.addObject("cate",cate);
			m.addObject("searchValue","Nhạc "+ l.get(0).getCateID().getCategoryName());
		}
		else {
			m= new ModelAndView("notfound");
			m.addObject("currentPage",pi);
		m.addObject("list",l);
		List<Category> cate=e.createQuery("from Category",Category.class).getResultList();
		m.addObject("cate",cate);
		}
		m.addObject("format", w);
		m.addObject("href","http://localhost:8080/byCategory?c="+c);
//		session.setAttribute("http", "/byCategory?c="+c+"&p="+p);
		return m;
	}
}
