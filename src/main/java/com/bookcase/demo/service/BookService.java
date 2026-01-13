package com.bookcase.demo.service;

import com.bookcase.demo.controller.NotificationController;
import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.entity.Author;
import com.bookcase.demo.entity.Book;
import com.bookcase.demo.exception.AuthorNotFoundException;
import com.bookcase.demo.exception.BookNotFoundException;
import com.bookcase.demo.mapper.AuthorMapperMapStruct;
import com.bookcase.demo.dto.BookCategory;
import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.repository.AuthorRepository;
import com.bookcase.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    @Qualifier("authorMapperMapStruct")
    private final AuthorMapperMapStruct authorMapper;
    private final BookMapper bookMapper;
    private final NotificationController notificationController;
    private final MqttService mqttService;

    public List<Book> getBooksService() {
        return this.bookRepository.findAll();
    }

    public Book getBookByIdService(Long id) {
        Optional<Book> bookFoundById = this.bookRepository.findById(id);
        if (bookFoundById.isEmpty()) {
            log.info("There is no book with id: {}", id);
            throw new BookNotFoundException();
        } else {
            return bookFoundById.get();
        }
    }

    public List<Book> getBooksContainingCharactersService(String character) {
        log.info("Received character: {}", character);
        return bookRepository.findAll()
                .stream()
                .filter(b -> b.getTitle().toLowerCase().contains(character))
                .collect(Collectors.toList());
    }

    public void createNewBookService(Book bookToSave, Long authorId) {
        Optional<Author> author = this.authorRepository.findById(authorId);
        if (author.isPresent()) {
            bookToSave.setAuthor(author.get());
            this.bookRepository.save(bookToSave);
            notificationController.sendNotification("New book added: " + bookToSave.getTitle());
            mqttService.publish("New book added: " + bookToSave.getTitle());
            log.info("Book's been successfully saved");
        } else {
            throw new AuthorNotFoundException("Author not found with id: " + authorId);
        }
    }

    public void deleteBookByIdService(Long id) {
        Optional<Book> bookById = this.bookRepository.findById(id);
        if (bookById.isEmpty()) {
            log.info("There is no book with id: {}", id);
            throw new BookNotFoundException();
        }
        Book bookToDelete = bookById.get();
        this.bookRepository.delete(bookToDelete);
        notificationController.sendNotification("Book removed: " + bookToDelete.getTitle());
        mqttService.publish("Book removed: " + bookToDelete.getTitle());
        log.info("Book's been successfully removed");
    }

    public BookDTO updateBookDTOService(Long id, BookDTO bookDTO) {
        Optional<Book> bookToUpdateOptional = this.bookRepository.findById(id);
        if (bookToUpdateOptional.isEmpty()) {
            log.info("Book with this id: {} do not exist.", id);
            throw new BookNotFoundException();
        }
        Book bookToUpdate = bookToUpdateOptional.get();
        if(bookDTO.getTitle() != null) {
            bookToUpdate.setTitle(bookDTO.getTitle());
        }
        if(bookDTO.getPages() != null) {
            bookToUpdate.setPages(bookDTO.getPages());
        }
        if(bookDTO.getCategory() != null) {
            bookToUpdate.setCategory(bookDTO.getCategory());
        }
        this.bookRepository.save(bookToUpdate);
        notificationController.sendNotification("Book updated: " + bookToUpdate.getTitle());
        mqttService.publish("Book updated: " + bookToUpdate.getTitle());
        log.info("Book's been successfully updated");
        return bookMapper.mapBookToDto(bookToUpdate);
    }

    public List<Book> getBookByCategoryService(BookCategory category) {
        return this.bookRepository.findByCategory(category);
    }


    public List<AuthorDTO> getBookAuthorService(String title) {
        List<Book> bookList = this.bookRepository.findAllBooksByTitle(title);
        List<Author> authorList = bookList.stream()
                .map(Book::getAuthor)
                .collect(Collectors.toList());
        return authorMapper.mapAuthorToDTOList(authorList);
    }
}
