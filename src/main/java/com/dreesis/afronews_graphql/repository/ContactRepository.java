package com.dreesis.afronews_graphql.repository;

import com.dreesis.afronews_graphql.entities.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}
