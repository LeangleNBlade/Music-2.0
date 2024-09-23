package com.boot.music.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class SongRepoCustromImp implements SongRepoCustom{
	@PersistenceContext
	EntityManager eM;
}
