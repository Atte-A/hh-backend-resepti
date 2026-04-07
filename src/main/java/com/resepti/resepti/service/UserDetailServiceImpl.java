package com.resepti.resepti.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.resepti.resepti.entity.Kayttaja;
import com.resepti.resepti.repository.KayttajaRepo;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final KayttajaRepo kayttajaRepo;

    public UserDetailServiceImpl(KayttajaRepo kayttajaRepo) {
        this.kayttajaRepo = kayttajaRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String kayttajatunnus) throws UsernameNotFoundException {
      Kayttaja currUser = kayttajaRepo.findByKayttajatunnus(kayttajatunnus)
              .orElseThrow(() -> new UsernameNotFoundException("Käyttäjää " + kayttajatunnus + " ei löydy"));

      return new org.springframework.security.core.userdetails.User(
              currUser.getKayttajatunnus(),
              currUser.getSalasanaHashed(),
              AuthorityUtils.createAuthorityList("ROLE_" + currUser.getRooli())
      );
    }
}