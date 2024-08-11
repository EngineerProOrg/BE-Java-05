package com.engineerpro.rest.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineerpro.rest.example.model.App;

@Repository
public interface AppRepository extends JpaRepository<App, String> {
  App findByApiClientKey(String apiClientKey);
}
