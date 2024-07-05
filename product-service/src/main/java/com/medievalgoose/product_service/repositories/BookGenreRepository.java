package com.medievalgoose.product_service.repositories;

import com.medievalgoose.product_service.models.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, String> {
}
