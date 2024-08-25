package com.engineerpro.rest.example.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.repository.AppRepository;
import com.engineerpro.rest.example.service.AppServiceImpl;

// https://www.baeldung.com/mockito-annotations
@ExtendWith(MockitoExtension.class)
public class AppServiceTest {
  @Mock
  private AppRepository appRepository;

  @InjectMocks
  private AppServiceImpl appService;

  @Test
  public void testWithCaptor() {
    when(appRepository.findByApiClientKey(any())).thenReturn(new App("id", "client-key", "name", true, Instant.now()));

    App app = appService.getAppByClientKey("client-key");

    assertThat(app.getId()).isEqualTo("id");
  }
}
