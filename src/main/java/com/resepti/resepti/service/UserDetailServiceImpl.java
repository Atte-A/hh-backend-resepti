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

      // Tarkistetaan, että käyttäjä on olemassa
      Kayttaja kayttaja = kayttajaRepo.findByKayttajatunnus(kayttajatunnus)
              .orElseThrow(() -> new UsernameNotFoundException("Käyttäjää " + kayttajatunnus + " ei löydy"));

      // Muunnetaan käyttäjä Spring Security User-objektiksi
      return new org.springframework.security.core.userdetails.User(
              kayttaja.getKayttajatunnus(),
              kayttaja.getSalasanaHashed(),
              AuthorityUtils.createAuthorityList("ROLE_" + kayttaja.getRooli())
      );
    }
}