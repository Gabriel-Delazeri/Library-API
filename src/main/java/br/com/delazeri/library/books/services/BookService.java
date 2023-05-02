package br.com.delazeri.library.books.services;

import br.com.delazeri.library.books.controllers.BookController;
import br.com.delazeri.library.books.dtos.BookDTO;
import br.com.delazeri.library.books.entities.Book;
import br.com.delazeri.library.books.repositories.BookRepository;
import br.com.delazeri.library.exceptions.RequiredObjectIsNullException;
import br.com.delazeri.library.exceptions.ResourceNotFoundException;
import br.com.delazeri.library.mapper.config.DozerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public List<BookDTO> findAll() {
        List<BookDTO> bookDTOList = DozerMapper.parseListObjects(repository.findAll(), BookDTO.class);

        bookDTOList.forEach(
                bookDTO -> bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getKey())).withSelfRel())
        );

        return bookDTOList;
    }

    public BookDTO findById(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        BookDTO bookDTO = DozerMapper.parseObject(book, BookDTO.class);
        bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getKey())).withSelfRel());
        return bookDTO;
    }

    public BookDTO create(BookDTO bookDTO) {

        if (bookDTO == null) throw new RequiredObjectIsNullException();

        Book book = DozerMapper.parseObject(bookDTO, Book.class);

        bookDTO = DozerMapper.parseObject(repository.save(book), BookDTO.class);
        bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getKey())).withSelfRel());
        return bookDTO;
    }

    public BookDTO update(BookDTO bookDTO) {

        if (bookDTO == null) throw new RequiredObjectIsNullException();

        Book book = repository.findById(bookDTO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setPrice(bookDTO.getPrice());
        book.setLaunchDate(bookDTO.getLaunchDate());

        bookDTO = DozerMapper.parseObject(repository.save(book), BookDTO.class);
        bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getKey())).withSelfRel());
        return bookDTO;
    }

    public void delete(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(book);
    }
}
