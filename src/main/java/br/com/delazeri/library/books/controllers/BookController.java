package br.com.delazeri.library.books.controllers;

import br.com.delazeri.library.books.dtos.BookDTO;
import br.com.delazeri.library.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    BookService service;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok().body(service.create(bookDTO));
    }

    @PutMapping
    public ResponseEntity<BookDTO> update(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok().body(service.update(bookDTO));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
