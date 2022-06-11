package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.ThePersonInCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<ThePersonInCharge, ThePersonInCharge.Pk> {
}
