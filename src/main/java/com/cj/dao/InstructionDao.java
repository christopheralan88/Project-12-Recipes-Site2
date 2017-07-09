package com.cj.dao;


import com.cj.model.Instruction;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InstructionDao extends PagingAndSortingRepository<Instruction, Long> {
}
