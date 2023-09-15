package com.animalplatform.platform.adopt.repository;

import com.animalplatform.platform.adopt.entity.Adopt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdoptRepository extends JpaRepository<Adopt, Long> {

    @Query("SELECT a FROM Adopt a WHERE a.adoptNo = ?1 AND a.delYn = 'N'")
    Optional<Adopt> findByAdoptNo(Long adoptNo);

    @Query("SELECT a FROM Adopt a WHERE a.delYn = 'N'")
    List<Adopt> findAdoptAll();


}
