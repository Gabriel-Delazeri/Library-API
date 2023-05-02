package br.com.delazeri.library.unittests.mockito.mocks;

import br.com.delazeri.library.books.dtos.BookDTO;
import br.com.delazeri.library.books.entities.Book;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }

        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<BookDTO>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }

        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        book.setTitle("Some Title" + number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setKey(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        book.setTitle("Some Title" + number);
        return book;
    }
}
