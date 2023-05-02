package br.com.delazeri.library.books.repositories;

import br.com.delazeri.library.books.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
