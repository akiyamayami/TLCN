package com.tlcn.dao;

import org.springframework.data.repository.CrudRepository;

import com.tlcn.model.Proposal;
import com.tlcn.model.Right;

public interface ProposalRepository extends CrudRepository<Proposal, Integer>{

}
