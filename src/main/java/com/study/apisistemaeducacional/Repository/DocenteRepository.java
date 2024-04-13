package com.study.apisistemaeducacional.Repository;

import com.study.apisistemaeducacional.Entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {

}
