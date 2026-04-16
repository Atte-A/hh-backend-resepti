package com.resepti.resepti.integration;

import com.resepti.resepti.entity.Ainesosa;
import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.Tag;
import com.resepti.resepti.repository.AinesosaRepo;
import com.resepti.resepti.repository.ReseptiRepo;
import com.resepti.resepti.repository.TagRepo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReseptiRepoIntegraatioTest {

  @Autowired
  private ReseptiRepo reseptiRepo;

  @Autowired
  private TagRepo tagRepo;

  @Autowired
  private AinesosaRepo ainesosaRepo;

  @Test
  void reseptiTallennusJaHakuToimii() {
    Resepti r = new Resepti();
    r.setNimi("Testiresepti");
    r.setOhje("Sekoita ja paista");
    r.setAnnosmaara(2);
    r.setValmistusaika(10);

    Resepti tallennettu = reseptiRepo.save(r);

    assertNotNull(tallennettu.getReseptiId());
    assertEquals("Testiresepti",
        reseptiRepo.findById(tallennettu.getReseptiId()).get().getNimi());
  }

  @Test
  void tagiTallennusJaHakuToimii() {
    Tag t = new Tag();
    t.setNimi("TestiTagi");

    Tag tallennettu = tagRepo.save(t);

    assertNotNull(tallennettu.getTagId());
    assertEquals("TestiTagi", tagRepo.findById(tallennettu.getTagId()).get().getNimi());
  }

    @Test
  void ainesosaTallennusJaHakuToimii() {
    Ainesosa a = new Ainesosa();
    a.setNimi("TestiAinesosa");

    Ainesosa tallennettu = ainesosaRepo.save(a);

    assertNotNull(tallennettu.getAinesosaId());
    assertEquals("TestiAinesosa", ainesosaRepo.findById(tallennettu.getAinesosaId()).get().getNimi());
  }
}