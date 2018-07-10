package com.example.service;

import com.example.model.Branch;

import java.util.List;

public interface BranchService {
    List<Branch> listAllBranches();
    void deleteBranchByID(int id);
    void saveBranch(Branch branch);
    Branch findBranchByID(int id);
}
