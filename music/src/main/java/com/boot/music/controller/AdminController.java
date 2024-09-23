package com.boot.music.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boot.music.dto.SongDTO;
import com.boot.music.entity.Account;
import com.boot.music.entity.Artist;
import com.boot.music.entity.Category;
import com.boot.music.entity.Region;
import com.boot.music.entity.Song;
import com.boot.music.entity.User;
import com.boot.music.repositories.AccountRepo;
import com.boot.music.repositories.CategoryRepo;
import com.boot.music.repositories.SongRepo;
import com.boot.music.util.WFormater;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
@Validated
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	CategoryRepo cateRe;
	@Autowired
	SongRepo songRe;

	@Autowired
	WFormater w;
	@Autowired
	private AccountRepo accRepo;
		EntityManagerFactory emf= Persistence.createEntityManagerFactory("music");
		EntityManager e=emf.createEntityManager();
	@GetMapping("/song")
	public ModelAndView admin(RedirectAttributes redirect,ModelAndView model, HttpSession session, @RequestParam String p) {

		//Check role = security r >> bỏ buớc này

//		Account u=(Account) session.getAttribute("Acc");
//		if(u!=null) {
//			if(u.getRole().getId()==1) {
				model= new ModelAndView("tables");

				Integer pi;
				try {
					pi=Integer.parseInt(p);
				} catch (NumberFormatException e) {

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
				model.addObject("list",l);
				model.addObject("href","http://localhost:8080/admin/song");
				List<Category> cate=e.createQuery("from Category",Category.class).getResultList();
				model.addObject("cate",cate);
//			}

//		}
//		if(u==null||u.getRole().getId()!=1) {
//			model= new ModelAndView("home");
//
//			List<Category> l=e.createQuery("from Category",Category.class).getResultList();
//
//			model.addObject("cate_list",l);
//
//			List<Song> l2=e.createQuery("from Song where category=1 ",Song.class).getResultList();
//
//			Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
//					+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
//					+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join s.artist a where s.category=?1 ",SongDTO.class);
//			q.setParameter(1, l.get(1));
//			List<SongDTO> l4=q.setMaxResults(5).getResultList();
//			q.setParameter(1, l.get(2));
//			List<SongDTO> l5=q.setMaxResults(5).getResultList();
//			q.setParameter(1, l.get(3));
//
//			List<SongDTO> l6=q.setMaxResults(5).getResultList();
//			model.addObject("format", w);
//			model.addObject("cate1",l4);
//
//			model.addObject("c1_name",l.get(0).getCategoryName());
//			model.addObject("cate2",l5);
//			model.addObject("c2_name",l.get(2).getCategoryName());
//			model.addObject("cate3",l6);
//			model.addObject("c3_name",l.get(4).getCategoryName());
//			model.addObject("t",l);
//			session.setAttribute("http", "/home");
//		}


		return model;
	}
	@PostMapping("/edit")
	public  ModelAndView edit(ModelAndView m, @RequestParam String id ,HttpSession session) {

		//Bỏ check do security check r

//		Account u=(Account) session.getAttribute("Acc");
//		if(u!=null) {
//			if(u.getRole().getId()==1) {
				m= new ModelAndView("edit");
				Integer sid;
				try {
					sid=Integer.parseInt(id);
				} catch (NumberFormatException e) {
					
					sid=1;
				}
				if(sid<=0) {
					sid=1;
				}
				
				Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
						+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
						+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join s.artist a where s.id=?1 ",SongDTO.class);
				List<SongDTO> l=q.setParameter(1, id).getResultList();
				m.addObject("list",l);
			List<Category> c=	cateRe.findAll();
			for (int i=0;i<c.size();i++) {
				if(c.get(i).getId()==l.get(0).getCateID().getId()) {
					c.remove(i);
				}
			}
				//m.addObject("currentPage", page);
			m.addObject("cate",c);
				System.out.println(l.get(0).getTitle());

//			}
//
//		}
//		if(u==null||u.getRole().getId()!=1) {
//			m= new ModelAndView("home");
//
//			List<Category> l=e.createQuery("from Category",Category.class).getResultList();
//
//			m.addObject("cate_list",l);
//
//			List<Song> l2=e.createQuery("from Song where category=1 ",Song.class).getResultList();
//
//			Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
//					+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
//					+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join Artist a where s.category=?1 ",SongDTO.class);
//			q.setParameter(1, l.get(1));
//			List<SongDTO> l4=q.setMaxResults(5).getResultList();
//			q.setParameter(1, l.get(2));
//			List<SongDTO> l5=q.setMaxResults(5).getResultList();
//			q.setParameter(1, l.get(3));
//
//			List<SongDTO> l6=q.setMaxResults(5).getResultList();
//			m.addObject("cate1",l4);
//			m.addObject("cate2",l5);
//			m.addObject("cate3",l6);
//		}

		
		return m;
	}

	@PostMapping("/updateSong")
	public  String updateSong(
			@RequestParam String id,
			@RequestParam String title,
			@RequestParam String artist,
			@RequestParam String img,
			@RequestParam String mp3,
			@RequestParam String cate,
			@RequestParam String reg,
			HttpSession session,
			RedirectAttributes redirect) {
		e.getTransaction().begin();
		Song s=e.find(Song.class, id);
		e.merge(s);
		List<Category> catel=e.createQuery("from Category where categoryName=?1",Category.class).setParameter(1, cate).getResultList();
		
		List<Artist> artistL=e.createQuery("from Artist where name=?1",Artist.class).setParameter(1, artist).getResultList();
		List<Region> regl=e.createQuery("from Region where country=?1",Region.class).setParameter(1, reg).getResultList();
		
		
		java.util.Set<Artist> lar= new LinkedHashSet<>();
		lar.add(artistL.get(0));
		EntityTransaction t= e.getTransaction();
		s.setTitle(title);
		s.setArtist(lar);
		
		if(catel.size()!=0) {
			s.setCategory(catel.get(0));
		}
		else {
			Category c= new Category(cate);
			e.persist(c);
			s.setCategory(c);
		}
		if(regl.size()!=0) {
			s.setReg(regl.get(0));
		}
		else {
			Region r= new Region(reg);
			e.persist(r);
			s.setReg(r);
		}
		s.setImg(img);
		s.setMp3_link(mp3);
		
		
		t.commit();
//		//int page=(Integer)session.getAttribute("currentPage");
		redirect.addAttribute("p",1);
		return"redirect:song";
	}
	@GetMapping("/add")
	public ModelAndView add(ModelAndView m) {
		List<Category> l=cateRe.findAll();
	
		System.out.println(l.size());
		m= new ModelAndView("add-song");
		m.addObject("cate",l);
		return m;
	}
	@PostMapping("/addSong")
	public String addNew(ModelAndView m,
	
			@RequestParam String title,
			@RequestParam String artist,
			@RequestParam String img,
			@RequestParam String mp3,
			@RequestParam String cate,
			@RequestParam String reg,
			HttpSession session,
			RedirectAttributes redirect) {
		
	
		
		String des="redirect:admin";

		//Bỏ check do check security r

//		Account u= (Account)session.getAttribute("Acc");
//		if(u!=null) {
//
//		if(u.getRole().getId()==1) {

		EntityTransaction t= e.getTransaction();
		t.begin();
		Song s= new Song(title, img, mp3, reg, null, null);
		List<Category> catel=e.createQuery("from Category where categoryName=?1",Category.class).setParameter(1, cate).getResultList();
		
		List<Artist> artistL=e.createQuery("from Artist where name=?1",Artist.class).setParameter(1, artist).getResultList();
		List<Region> regl=e.createQuery("from Region where country=?1",Region.class).setParameter(1, reg).getResultList();
		if(catel.size()!=0) {
			s.setCategory(catel.get(0));
		}
		else {
			Category c= new Category(cate);
			e.persist(c);
			s.setCategory(c);
		}
		if(regl.size()!=0) {
			s.setReg(regl.get(0));
		}
		else {
			Region r= new Region(reg);
			
			e.persist(r);
			
			s.setReg(r);
		}
		if(artistL.size()!=0) {
			java.util.Set<Artist> lar= new LinkedHashSet<>();
			lar.add(artistL.get(0));
			s.setArtist(lar);
		}
		else {
			Artist a= new Artist(artist);
			java.util.Set<Artist> lar= new LinkedHashSet<>();
			lar.add(a);
			s.setArtist(lar);
			e.persist(a);
			
		}
		
		e.persist(s);
		t.commit();
		redirect.addAttribute("p",1);
		des="redirect:/admin/song";

//		}}

		return des;
	}
	
	@PostMapping("/delete")
	public  String delete(HttpSession session,ModelAndView m,@RequestParam String id,RedirectAttributes redirect) {
		m= new ModelAndView("admin");
		String des="redirect:song";

		//Bỏ check do check security r

//		Account u= (Account)session.getAttribute("Acc");
//		if(u!=null) {
//
//		if(u.getRole().getId()==1) {

		EntityTransaction t= e.getTransaction();
		t.begin();
		Song s=e.find(Song.class, id);
		Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
				+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
				+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join s.artist a where s.id=?1 ",SongDTO.class);
		q.setParameter(1, id);
		List<SongDTO> l4=q.getResultList();
		System.out.println(l4.size());
		SongDTO dt=l4.get(0);
		int aID=dt.getArtistID();
		Artist a=e.find(Artist.class, aID);
		s.removeArtist(a);
		Category c= e.find(Category.class, dt.getCateID().getId());
		s.setCategory(null);
		e.remove(s);
		t.commit();
		redirect.addAttribute("p",1);
		des="redirect:song";

//		}
//		}

		return des;
	
}

	@PostMapping("/addCate")
	public String addCate(RedirectAttributes redirect,
			@RequestParam String name) {
		Optional<Category> l= cateRe.findByCategoryNameIgnoreCase(name);
		if(l.isEmpty()) {
			Category c= new Category(name);
			
			cateRe.save(c);
			cateRe.flush();
		}
		redirect.addAttribute("p",1);
		return"redirect:song";
	}
	@GetMapping("/addCatePage")
	public String addCatePage(ModelAndView m) {
		 m= new ModelAndView("home");
		
		return "addCate";
	}
	@PostMapping("/deleteCate")
	public String deleteCate(@RequestParam String id,RedirectAttributes redirect) {
		Integer i= Integer.parseInt(id);
		cateRe.deleteById(i);
		return "redirect:viewCatePage";
	}
	@PostMapping("/editCate")
	public ModelAndView editCatePage(ModelAndView m,
			@RequestParam String id,RedirectAttributes redirect) {
		m= new ModelAndView("edit-cate");
		Integer i= Integer.parseInt(id);
		Category c= cateRe.findById(i).get();
		m.addObject("cate",c);
		return m;
	}
	@PostMapping("/editAcc")
	public ModelAndView editAccPage(ModelAndView m,
			@RequestParam String id,RedirectAttributes redirect) {
		m= new ModelAndView("edit-acc");
		Integer i= Integer.parseInt(id);
		Account a= accRepo.findById(i).get();
		
		m.addObject("acc",a);
		return m;
	}
	@GetMapping("/viewCatePage")
	public ModelAndView viewCatePage(ModelAndView m) {
		m= new ModelAndView("cate_list");
		List<Category> l=cateRe.findAll();
		m.addObject("list",l);
		
		return m;
	}
	@GetMapping("/viewAccPage")
	public ModelAndView viewAccPage(ModelAndView m) {
		m= new ModelAndView("acc-list");
		List<Account> l=accRepo.findAll();
		m.addObject("list",l);
		
		return m;
	}
	
	@PostMapping("/updateCategory")
	public String updateCate(@RequestParam String id
			,@RequestParam String name
			,RedirectAttributes redirect) {
		Integer i= Integer.parseInt(id);
		Category c= cateRe.findById(i).get();
		c.setCategoryName(name);
		cateRe.save(c);
		return "redirect:viewCatePage";
	}
	@PostMapping("/updateAcc")
	public String updateAcc(@RequestParam String id
			,@RequestParam String username
			,@RequestParam String fullname
			,@RequestParam String addr
			,@RequestParam String mail
			,RedirectAttributes redirect) {
		Integer i= Integer.parseInt(id);
		Account a= accRepo.findById(i).get();
		a.setAddress(addr);
		a.setFullname(fullname);
		a.setMail(mail);
		a.setUsername(username);
		
		accRepo.save(a);
		accRepo.flush();
		return "redirect:viewAccPage";
	}
	@PostMapping("/deleteAcc")
	public String deleteAcc(@RequestParam String id,RedirectAttributes redirect) {
		Integer i= Integer.parseInt(id);
		Account a= accRepo.findById(i).get();
		a.setRole(null);
		accRepo.deleteById(i);
		return "redirect:viewAccPage";
	}
}
