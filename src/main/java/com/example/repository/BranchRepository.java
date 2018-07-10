package com.example.repository;


import com.example.model.Branch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    void deleteBranchById(int id);
    Branch findBranchById(int id);
}
