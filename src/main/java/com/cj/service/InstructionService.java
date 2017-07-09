package com.cj.service;


import com.cj.model.Instruction;

import java.util.List;

public interface InstructionService {
    Iterable<Instruction> findAll();
    Instruction findById(Long id);
    void save(Instruction instruction);
    void delete(Instruction instruction);
}