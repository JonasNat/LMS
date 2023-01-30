package com.switchfully.lms.repositories;

import com.switchfully.lms.domain.CodeLab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeLabRepository extends JpaRepository<CodeLab, Long> {
}
