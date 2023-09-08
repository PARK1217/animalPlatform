package com.animalplatform.platform.adopt.repository;

import com.animalplatform.platform.adopt.entity.Adopt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptRepository extends JpaRepository<Adopt, Long> {


}
