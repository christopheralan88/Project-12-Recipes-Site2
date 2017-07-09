package com.cj.service;

import com.cj.dao.InstructionDao;
import com.cj.model.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InstructionServiceImpl implements InstructionService {
    @Autowired
    private InstructionDao instructionDao;

    @Override
    public Iterable<Instruction> findAll() {
        return instructionDao.findAll();
    }

    @Override
    public Instruction findById(Long id) {
        return instructionDao.findOne(id);
    }

    @Override
    public void save(Instruction instruction) {
        instructionDao.save(instruction);
    }

    @Override
    public void delete(Instruction instruction) {
        instructionDao.delete(instruction);
    }
}