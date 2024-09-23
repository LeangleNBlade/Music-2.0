package com.boot.music.controller;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.eclipse.persistence.internal.oxm.record.json.JSONReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.boot.music.dto.SongDTO;
import com.boot.music.entity.Artist;
import com.boot.music.entity.Song;
import com.boot.music.repositories.SongRepo;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/song")
public class SongController {
	@Autowired
	SongRepo songRe;

	@GetMapping("/song")
	public ModelAndView song(ModelAndView model) {
		
		return new ModelAndView("song");
		}
	@GetMapping(value = "/play")
	public @ResponseBody String data(@RequestParam("id") String id) throws JSONException {
		Integer i;
		try {
			i=Integer.parseInt(id);
		} catch (NumberFormatException e) {
			i=1;
			
		}
		
		EntityManagerFactory em= Persistence.createEntityManagerFactory("music");
		EntityManager e=em.createEntityManager();
//		Query q=	e.createQuery("Select id,title from Song where id=?1", Object[].class);
//		q.setParameter(1, 1);
//		List<Object[]> results = q.getResultList();
		JSONObject j= new JSONObject();
//		for (Object[] row : results) {
//			 j.put("id", row[0]);
//			 j.put("name", row[1]);
//		}
		Query q=e.createQuery("Select new com.boot.music.dto.SongDTO("
				+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
				+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join Artist a where s.id=?1 ",SongDTO.class);
		q.setParameter(1, i);
		List<SongDTO> s=q.getResultList();
		if(s.size()<=0) {
			q.setParameter(1, 1);
			s=q.getResultList();
			
		}
		
			 j.put("id", s.get(0).getSongID());
			 j.put("img", s.get(0).getSongIMG());
			 j.put("mp3", s.get(0).getMp3_link());
			 j.put("title", s.get(0).getTitle());
			 j.put("reg", s.get(0).getRegionID().getCountry());
			 j.put("category", s.get(0).getCateID().getCategoryName());
			 j.put("artist", s.get(0).getArtistName());
		
//		 Song res=	e.find(Song.class, i);
		
		 return j.toString();
	}
}
	