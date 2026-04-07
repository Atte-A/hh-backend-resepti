package com.resepti.resepti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resepti.resepti.entity.Tag;

public interface TagRepo extends JpaRepository<Tag, Long> {

  Optional<Tag> findByNimi(String nimi);
}
