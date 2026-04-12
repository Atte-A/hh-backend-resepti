package com.resepti.resepti.integration;

import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.repository.ReseptiRepo;
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

    @Test
    void reseptiTallennusJaHakuToimii() {
        Resepti r = new Resepti();
        r.setNimi("Testiresepti");
        r.setOhje("Sekoita ja paista");
        r.setAnnosmaara(2);
        r.setValmistusaika(10);

        Resepti saved = reseptiRepo.save(r);

        assertNotNull(saved.getReseptiId());
        assertEquals("Testiresepti",
                reseptiRepo.findById(saved.getReseptiId()).get().getNimi());
    }
}