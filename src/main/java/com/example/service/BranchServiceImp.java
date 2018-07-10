package com.example.service;

import com.example.model.Branch;
import com.example.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class BranchServiceImp implements BranchService {

    @Autowired
    @Qualifier("branchRepository")
    private BranchRepository branchRepository;

    @Override
    public List<Branch> listAllBranches() {
        List<Branch> branches = new ArrayList<>();
        branchRepository.findAll().forEach(branches::add);
        return branches;
    }

    @Override
    public void deleteBranchByID(int id) {
        branchRepository.deleteBranchById(id);
    }

    @Override
    public void saveBranch(Branch branch) {
        branchRepository.save(branch);
    }

    @Override
    public Branch findBranchByID(int id) {
        return branchRepository.findBranchById(id);
    }
}
