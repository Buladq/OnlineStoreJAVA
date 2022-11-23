package ru.bul.springs.SecApp.services;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.bul.springs.SecApp.models.Image;
import ru.bul.springs.SecApp.models.Person;
import ru.bul.springs.SecApp.repository.ImageRepository;
import ru.bul.springs.SecApp.repository.PeopleRepository;
import ru.bul.springs.SecApp.secutiry.PersonDetails;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PeopleRepository peopleRepository;

    private final ImageRepository imageRepository;

    public PersonService(PeopleRepository peopleRepository, ImageRepository imageRepository) {
        this.peopleRepository = peopleRepository;
        this.imageRepository = imageRepository;
    }

    public Optional<Person> getUserByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    public String getUserByUsernameMy(String username) {
        List<Person> personlist=peopleRepository.findAll();
        for (var pp:
                personlist) {
            if(pp.getUsername().equals(username)){
                return pp.getUsername();
            }
        }
        return null;
    }


    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    @Transactional
    public void banUser(int id){
        Optional<Person> person=peopleRepository.findById(id);
        person.get().setId(id);
        person.get().setActivite(false);
    }

    public Optional<Person> getPersonById(int id){
        return peopleRepository.findById(id);
    }



    @Transactional
    public void editPerson(MultipartFile file, Person updperson,int id) throws IOException {
        Optional<Person> personBefore=peopleRepository.findById(id);
        if(file!=null) {
            Image image;
            if (file.getSize() != 0) {
                image = toImageEntity(file);
                image.setPreviewImage(true);
                imageRepository.save(image);
                updperson.setRole(personBefore.get().getRole());
                updperson.setPassword(personBefore.get().getPassword());
                updperson.setId(id);
                updperson.setActivite(true);
                updperson.setAvatar_id(image);
                peopleRepository.save(updperson);
            }
        }
            updperson.setRole(personBefore.get().getRole());
            updperson.setPassword(personBefore.get().getPassword());
            updperson.setId(id);
            updperson.setActivite(true);
            peopleRepository.save(updperson);
        }



    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    public Optional<Person> getPerson(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        Optional<Person> person=peopleRepository.findById(personDetails.getPerson().getId());
        return person;
    }


}
