package br.com.delazeri.library.books.services;

import br.com.delazeri.library.books.dtos.BookDTO;
import br.com.delazeri.library.books.entities.Book;
import br.com.delazeri.library.books.repositories.BookRepository;
import br.com.delazeri.library.exceptions.ResourceNotFoundException;
import br.com.delazeri.library.mapper.config.DozerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public List<BookDTO> getAll() {
        return DozerMapper.parseListObjects(repository.findAll(), BookDTO.class);
    }

    public BookDTO getById(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        return DozerMapper.parseObject(book, BookDTO.class);
    }

    public BookDTO create(BookDTO bookDTO) {
        Book book = DozerMapper.parseObject(bookDTO, Book.class);
        System.out.println(book.toString());

        return DozerMapper.parseObject(repository.save(book), BookDTO.class);
    }

    public BookDTO update(BookDTO bookDTO) {
        Book book = repository.findById(bookDTO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setPrice(bookDTO.getPrice());
        book.setLaunchDate(bookDTO.getLaunchDate());

        return DozerMapper.parseObject(repository.save(book), BookDTO.class);
    }

    public void delete(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(book);
    }
}
