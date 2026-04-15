package com.resepti.resepti.unit;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import com.resepti.resepti.entity.Resepti;
import com.resepti.resepti.entity.Tag;

@ActiveProfiles("test")
class ReseptiTest {

    @Test
    void resepti_luodaanOikein() {
        Resepti r = new Resepti();
        r.setNimi("Lihapullat");

        assertEquals("Lihapullat", r.getNimi());
    }

    @Test
    void tag_lisataanReseptiin() {
        Resepti r = new Resepti();
        Tag t = new Tag();
        t.setNimi("arkiruoka");

        r.getTags().add(t);

        assertEquals(1, r.getTags().size());
    }

    @Test
    void resepti_oletusarvotOikein() {
        Resepti r = new Resepti();

        assertNotNull(r.getTags());
        assertTrue(r.getTags().isEmpty());
    }
}