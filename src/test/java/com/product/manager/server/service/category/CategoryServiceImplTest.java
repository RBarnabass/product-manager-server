package com.product.manager.server.service.category;

import com.product.manager.server.entity.Category;
import com.product.manager.server.entity.Product;
import com.product.manager.server.exception.ConsistencyException;
import com.product.manager.server.exception.DataNotFoundException;
import com.product.manager.server.exception.DuplicationDataException;
import com.product.manager.server.repository.CategoryRepository;
import com.product.manager.server.service.category.impl.CategoryServiceImpl;
import com.product.manager.server.service.validator.CategoryValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryValidator categoryValidator;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;
    private final Long id = 10L;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setProducts(List.of());
        reset(categoryValidator, categoryRepository);
    }

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(categoryValidator, categoryRepository);
    }

    @Test
    public void whenSaveNewCategoryShouldValidateFirstTest() {
        given(categoryRepository.save(category)).willReturn(category);

        final Category result = categoryService.add(category);

        assertNotNull(result);
        verify(categoryValidator).validate(category);
        verify(categoryRepository).save(category);
    }

    @Test
    public void whenSaveExistedCategoryShouldThrowExceptionTest() {
        doThrow(DuplicationDataException.class).when(categoryValidator).validate(category);

        final Throwable thrown = catchThrowable(() -> categoryService.add(category));

        assertThat(thrown).isInstanceOf(DuplicationDataException.class);
        verify(categoryValidator).validate(category);
        verifyNoInteractions(categoryRepository);
    }

    @Test
    public void whenGetCategoryByIdShouldReturnOneTest() {
        given(categoryRepository.findById(id)).willReturn(Optional.of(category));

        final Category result = categoryService.get(id);

        assertNotNull(result);
        verify(categoryRepository).findById(id);
        verifyNoInteractions(categoryValidator);
    }

    @Test
    public void whenGetCategoryByIdDoesNotExistShouldThrownExceptionTest() {
        doThrow(DataNotFoundException.class).when(categoryRepository).findById(id);

        final Throwable thrown = catchThrowable(() -> categoryService.get(id));

        assertThat(thrown).isInstanceOf(DataNotFoundException.class);
        verify(categoryRepository).findById(id);
        verifyNoInteractions(categoryValidator);
    }

    @Test
    public void whenGetAllCategoriesShouldNotCallValidatorTest() {
        final List<Category> categories = List.of(category);
        given(categoryRepository.findAll()).willReturn(categories);

        final List<Category> result = categoryService.getAll();

        assertNotNull(result);
        verify(categoryRepository).findAll();
        verifyNoInteractions(categoryValidator);
    }

    @Test
    public void whenUpdateCategoryShouldCallValidatorAndSaveWithIdAsInParamsTest() {
        given(categoryRepository.existsById(id)).willReturn(true);
        given(categoryRepository.save(category)).willReturn(category);

        final Category result = categoryService.update(category, id);

        assertNotNull(result);
        verify(categoryValidator).validate(category);
        verify(categoryRepository).existsById(id);
        verify(categoryRepository).save(argThat(argument -> argument.getId().equals(id)));
    }

    @Test
    public void whenUpdateInvalidCategoryShouldNotSaveItTest() {
        doThrow(DuplicationDataException.class).when(categoryValidator).validate(category);

        final Throwable thrown = catchThrowable(() -> categoryService.update(category, id));

        assertThat(thrown).isInstanceOf(DuplicationDataException.class);
        verify(categoryValidator).validate(category);
        verifyNoInteractions(categoryRepository);
    }

    @Test
    public void whenUpdateCategoryWithNotExistedIdShouldNotSaveItTest() {
        given(categoryRepository.existsById(id)).willReturn(false);

        final Throwable thrown = catchThrowable(() -> categoryService.update(category, id));

        assertThat(thrown).isInstanceOf(DataNotFoundException.class);
        verify(categoryValidator).validate(category);
        verify(categoryRepository).existsById(id);
    }

    @Test
    public void whenDeleteCategoryShouldCheckItForExistenceTest() {
        given(categoryRepository.findById(id)).willReturn(Optional.of(category));

        categoryService.delete(id);

        verify(categoryRepository).findById(id);
        verify(categoryRepository).deleteById(id);
    }

    @Test
    public void whenDeleteNotExistenceCategoryShouldThrowExceptionTest() {
        given(categoryRepository.findById(id)).willReturn(Optional.empty());

        final Throwable thrown = catchThrowable(() -> categoryService.delete(id));

        assertThat(thrown).isInstanceOf(DataNotFoundException.class);
        verify(categoryRepository).findById(id);
    }

    @Test
    public void whenDeleteCategoryIsRelatedByProductsShouldThrowExceptionTest() {
        category.setProducts(List.of(new Product()));
        given(categoryRepository.findById(id)).willReturn(Optional.of(category));

        final Throwable thrown = catchThrowable(() -> categoryService.delete(id));

        assertThat(thrown).isInstanceOf(ConsistencyException.class);
        verify(categoryRepository).findById(id);
    }

    @Test
    public void whenGetByNamesShouldFindThemInStorageTest() {
        final String categoryName = "Sport";
        category.setName(categoryName);
        given(categoryRepository.findByNameIn(List.of(categoryName))).willReturn(List.of(category));

        final List<Category> result = categoryService.getByName(List.of(category));

        assertNotNull(result);
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        verify(categoryRepository).findByNameIn(argThat(argument -> argument.size() == 1));
        verifyNoInteractions(categoryValidator);
    }
}
