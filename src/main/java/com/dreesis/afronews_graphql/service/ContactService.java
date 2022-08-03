package com.dreesis.afronews_graphql.service;

import com.dreesis.afronews_graphql.entities.Contact;
import com.dreesis.afronews_graphql.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact save(Contact contact) {
        var conts = this.contactRepository.save(contact);
        return conts;
    }
}
