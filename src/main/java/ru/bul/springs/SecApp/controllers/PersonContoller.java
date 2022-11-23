package ru.bul.springs.SecApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ru.bul.springs.SecApp.models.Person;
import ru.bul.springs.SecApp.secutiry.PersonDetails;
import ru.bul.springs.SecApp.services.ImageService;
import ru.bul.springs.SecApp.services.PersonService;
import ru.bul.springs.SecApp.util.PersonValidator;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/person")//действия покупателя
public class PersonContoller {
    private final PersonService personService;

    private final PersonValidator personValidator;

    private final ImageService imageService;

    public PersonContoller(PersonService personService, PersonValidator personValidator, ImageService imageService) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.imageService = imageService;
    }

    @GetMapping("/info")
    public String info(Model model) {
        if(personService.getPerson().get().getAvatar_id() != null){
            model.addAttribute("image","image");
        }

        model.addAttribute("person",personService.getPerson().get());

        return "person/info";
    }

    @GetMapping("/change")
    public String editPerson(Model model,  @RequestParam(value = "file2",required = false) MultipartFile file2) {
     model.addAttribute("person",personService.getPerson().get());
     return "person/change";
    }

    @PatchMapping("/change")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @RequestParam(value = "file2",required = false) MultipartFile file2) throws IOException {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        System.out.println(file2.getSize());

        int idNow=personDetails.getPerson().getId();
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors()){
            return "person/change";
        }


        personService.editPerson( file2,person,idNow);
        return "redirect:/person/info";
    }




}
