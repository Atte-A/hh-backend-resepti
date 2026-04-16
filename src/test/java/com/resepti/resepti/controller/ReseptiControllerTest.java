package com.resepti.resepti.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReseptiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void haeReseptit_palauttaaOk() throws Exception {
    mockMvc.perform(get("/api/reseptit")
        .with(csrf())
        .with(httpBasic("admin", "admin")))
        .andExpect(status().isOk());
  }

  @Test
  void haeTagi_palauttaaOk() throws Exception {
    mockMvc.perform(get("/api/tagit/1")
        .with(csrf())
        .with(httpBasic("admin", "admin")))
        .andExpect(status().isOk());
  }

  @Test
  void haeAinesosat_palauttaaOk() throws Exception {
    mockMvc.perform(get("/api/ainesosat")
        .with(csrf())
        .with(httpBasic("admin", "admin")))
        .andExpect(status().isOk());
  }
}