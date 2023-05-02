package br.com.delazeri.library.unittests.mockito.tests.services;

import br.com.delazeri.library.books.dtos.BookDTO;
import br.com.delazeri.library.books.entities.Book;
import br.com.delazeri.library.books.repositories.BookRepository;
import br.com.delazeri.library.books.services.BookService;
import br.com.delazeri.library.unittests.mockito.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    void basicAssertions(BookDTO result) {
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/v1/books/" + result.getKey() + ">;rel=\"self\"]"));
        assertEquals("Some Author"+ result.getKey(), result.getAuthor());
        assertEquals("Some Title"+ result.getKey(), result.getTitle());
        assertEquals(25D, result.getPrice());
    }

    @Test
    void testFindAll() {
        List<Book> bookList = input.mockEntityList();

        when(repository.findAll()).thenReturn(bookList);

        List<BookDTO> bookDTOList = service.findAll();

        assertNotNull(bookDTOList);
        assertEquals(14, bookDTOList.size());

        basicAssertions(bookDTOList.get(1));
        basicAssertions(bookDTOList.get(3));
    }

    @Test
    void testGetById() {
        Book book = input.mockEntity(1);
        book.setId(1L);

        when(repository.findById(book.getId())).thenReturn(Optional.of(book));
        basicAssertions(service.findById(book.getId()));
    }

    @Test
    void testCreate() {
        Book book = input.mockEntity(1);

        Book persistedBook = book;
        persistedBook.setId(1L);

        BookDTO bookDTO = input.mockDTO(1);
        bookDTO.setKey(1L);

        when(repository.save(book))
                .thenReturn(persistedBook);

        basicAssertions(service.create(bookDTO));
    }

    @Test
    void testUpdate() {
        Book book = input.mockEntity(1);
        book.setId(1L);

        BookDTO bookDTO = input.mockDTO(1);
        bookDTO.setKey(1L);

        when(repository.findById(book.getId())).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(book);

        basicAssertions(service.update(bookDTO));
    }
}
