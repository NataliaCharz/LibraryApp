package com.bookcase.demo.repository;

import com.bookcase.demo.entity.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findUserByUserName(String login);

    Optional<AppUser> findAppUserById(Long id);

    boolean existsByUserName(String username);

    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.userBooks WHERE u.id = :userId")
    Optional<AppUser> findByIdWithBooks(@Param("userId") Long userId);

    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.favoriteBooks WHERE u.id = :userId")
    Optional<AppUser> findByIdWithFavoriteBooks(@Param("userId") Long userId);

    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.wishlistBooks WHERE u.id = :userId")
    Optional<AppUser> findByIdWithWishlistBooks(@Param("userId") Long userId);

    @Query(value = "SELECT READ_STATUS FROM USER_WISHLIST_BOOKS WHERE USER_ID = :userId AND BOOK_ID = :bookId", nativeQuery = true)
    Boolean getReadStatus(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USER_WISHLIST_BOOKS SET READ_STATUS = :status WHERE USER_ID = :userId AND BOOK_ID = :bookId", nativeQuery = true)
    void updateReadStatus(@Param("userId") Long userId, @Param("bookId") Long bookId, @Param("status") boolean status);
}
