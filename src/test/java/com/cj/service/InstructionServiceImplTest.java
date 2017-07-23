package com.cj.service;

import com.cj.dao.CategoryDao;
import com.cj.dao.InstructionDao;
import com.cj.model.Category;
import com.cj.model.Instruction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class InstructionServiceImplTest {
    @Mock
    private InstructionDao instructionDao;
    @InjectMocks
    private InstructionService instructionService = new InstructionServiceImpl();
    private Instruction instruction1 = new Instruction(1L, "step1");
    private Instruction instruction2 = new Instruction(2L, "step2");

    @Test
    public void findAll_returnsList() {
        Iterable instructions = Arrays.asList(instruction1, instruction2);

        when(instructionDao.findAll()).thenReturn(instructions);

        assertTrue(instructionService.findAll().equals(instructions));
    }

    @Test
    public void findById_returnsOne() {
        when(instructionDao.findOne(1L)).thenReturn(instruction1);

        assertTrue(instructionService.findById(1L).equals(instruction1));
    }

    @Test
    public void save_worksCorrectly() {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(instruction1);

        //categories list acts as in-memory persistence.
        //when save method is called, add Category to in-memory persistence instead of database.
        when(instructionDao.save(any(Instruction.class)))
                .thenAnswer(a -> instructions.add(instruction2));
        instructionService.save(instruction2);

        assertTrue(instructions.size() == 2);
    }

    @Test
    public void delete_worksCorrectly() {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(instruction1);

        //categories list acts as in-memory persistence.
        //when delete method is called, remove Category to in-memory persistence instead of database.
        doAnswer(a -> instructions.remove(instruction1))
                .when(instructionDao)
                .delete(instruction1);
        instructionService.delete(instruction1);

        assertTrue(instructions.size() == 0);
    }

}
