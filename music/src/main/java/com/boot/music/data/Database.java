package com.boot.music.data;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.boot.music.dto.SongDTO;
import com.boot.music.entity.Account;
import com.boot.music.entity.Artist;
import com.boot.music.entity.Category;
import com.boot.music.entity.Region;
import com.boot.music.entity.Role;
import com.boot.music.entity.Song;
import com.boot.music.entity.User;
import com.boot.music.repositories.AccountRepo;
import com.boot.music.repositories.ArtistRepo;
import com.boot.music.repositories.CategoryRepo;
import com.boot.music.repositories.RegionRepo;
import com.boot.music.repositories.RoleRepo;
import com.boot.music.repositories.SongRepo;
import com.boot.music.repositories.UserRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;



@Configuration
public class Database {
	private static final Logger logger= LoggerFactory.getLogger(Database.class);
	@Bean
	CommandLineRunner initDatabase(RegionRepo re, CategoryRepo cate, SongRepo songRe, ArtistRepo aRe, RoleRepo role, AccountRepo acRe, UserRepo userRe) {
		
		return new CommandLineRunner( ) {
		
		@Override
		public void run(String... args) throws Exception {
		
			Locale locale = new Locale("vi", "VN");      
			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
//			Category c1= new Category("Trinh cong son1");
//			cate.save(c1);
//			Category c= cate.findById(2).get();
//			Region r= re.findById(1).get();
//			Song s1= new Song("song1","urlimg","mp3","lyric", c,r);
//			Artist ar= aRe.findById(5).get();	
//			s1.getArtist().add(ar);
//			songRe.save(s1);
//			songRe.findAll().get(0).getId();
////			
//			System.out.println(songRe.findAll().get(0).getId());
			EntityManagerFactory em= Persistence.createEntityManagerFactory("music");
			EntityManager e=em.createEntityManager();
//			List<Song> l=songRe.findAll();
//			for(int i=0;i<180;i++) {
//				Set<Artist> set= new LinkedHashSet<Artist>();
//				set.add(aRe.findById(i+1).get());
//				Song s=l.get(i);
//						s.setArtist(set);
//				songRe.save(s);
//			}
//			for(int i=181;i<360;i++) {
//				Set<Artist> set= new LinkedHashSet<Artist>();
//				set.add(aRe.findById(i+1-180).get());
//				Song s=l.get(i);
//				s.setArtist(set);
//		songRe.save(s);
//				
//			}
//			for(int i=361;i<399;i++) {
//				Set<Artist> set= new LinkedHashSet<Artist>();
//				set.add(aRe.findById(i+1-180-180).get());
//				Song s=l.get(i);
//				s.setArtist(set);
//		songRe.save(s);
//			}
//			
//			Query q=	e.createQuery("Select  title from Song where id=?1");
//			q.setParameter(1, 15);
//		//	System.out.println(q.getSingleResult());
//			Query q1=e.createQuery("select id, categoryName from Category",Category[].class);
//			List<Category[]> l=q1.getResultList();
//			for (Object[] r : l) {
//				
//			}
//			Object[] o1=l.get(1);
//			
//			Song s=e.find(Song.class, 1);
//			System.out.println(o1[1]);
//			List<Song> l1=e.createQuery("Select s.id from Song s join s.artist a where s.id=1",Song.class).getResultList();
//			Category c3= l1.get(1);
		
//		
//			List<SongDTO> l3=e.createQuery("select a.id,a.name,s.id,s.title,s.img,s.mp3_link,s.lyrics,s.cate,s.reg"
//					+ "from Artist a,Song s  where s.id=p.song_id and s.id=1" ,SongDTO.class).getResultList();
//			List<SongDTO> l4=e.createQuery("Select new com.boot.music.dto.SongDTO("
//					+ "a.id as artistID, a.name as artistName,s.id as songID, s.title as title, s.img as songIMG, s.mp3_link as mp3_link,"
//					+ "s.lyrics as lyric, s.category as cateID, s.reg as regionID ) from  Song s join s.artist a where (s.title like '%nam%' or a.name like '%nam%') ",SongDTO.class).getResultList().subList(0, 10);
//			System.out.println(l4.size());	
//			for (SongDTO songDTO : l4) {
//				System.out.println(songDTO.getArtistID()+"  "+songDTO.getSongID()+"   "+ songDTO.getArtistName()+"   "+songDTO.getTitle() );
//			System.out.println();
			
			
			
//			}
//			Role r=e.find(Role.class, "1");
////			User c= e.find(User.class, 1);
//			User u= new User("á", "123", "asd", "", "a@gmail.com", r);
//			userRe.save(u);
//			acRe.save(c);
//			Query q=e.createQuery(" from Account",Account.class);
//			q.setParameter(1, "Holland");
//			q.setParameter(2, "ten@gmail.com");
//			q.setParameter(3, "hdasghjd");
			//User i=(User) q.getResultList().get(0);
			
			
		
		}
	};
	}
}
