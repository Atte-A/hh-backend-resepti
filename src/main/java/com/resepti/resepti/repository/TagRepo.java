package com.resepti.resepti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resepti.resepti.entity.Tag;

public interface TagRepo extends JpaRepository<Tag, Long> {

}
