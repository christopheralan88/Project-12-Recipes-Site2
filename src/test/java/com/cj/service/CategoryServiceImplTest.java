package com.cj.service;

import com.cj.dao.CategoryDao;
import com.cj.model.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryDao categoryDao;
    @InjectMocks
    private CategoryService categoryService = new CategoryServiceImpl();
    private Category categoryLunch = new Category("Lunch");
    private Category categoryDinner = new Category("Dinner");

    @Test
    public void findAll_returnsList() {
        Iterable categories = Arrays.asList(categoryLunch, categoryDinner);

        when(categoryDao.findAll()).thenReturn(categories);

        assertTrue(categoryService.findAll().equals(categories));
    }

    @Test
    public void findById_returnsOne() {
        when(categoryDao.findOne(1L)).thenReturn(categoryLunch);

        assertTrue(categoryService.findById(1L).equals(categoryLunch));
    }

    @Test
    public void save_worksCorrectly() {
        List<Category> categories = new ArrayList<>();
        categories.add(categoryLunch);

        //categories list acts as in-memory persistence.
        //when save method is called, add Category to in-memory persistence instead of database.
        when(categoryDao.save(any(Category.class)))
                .thenAnswer(a -> categories.add(categoryDinner));
        categoryService.save(categoryDinner);

        assertTrue(categories.size() == 2);
    }

    @Test
    public void delete_worksCorrectly() {
        List<Category> categories = new ArrayList<>();
        categories.add(categoryLunch);

        //categories list acts as in-memory persistence.
        //when delete method is called, remove Category to in-memory persistence instead of database.
        doAnswer(a -> categories.remove(categoryLunch))
                .when(categoryDao)
                .delete(categoryLunch);
        categoryService.delete(categoryLunch);

        assertTrue(categories.size() == 0);
    }

}
