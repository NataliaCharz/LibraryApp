package com.bookcase.demo.ui.controller;

import com.bookcase.demo.controller.AuthorController;
import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.dto.AuthorSex;
import com.bookcase.demo.entity.Author;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    private AuthorController authorController;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("features", Arrays.asList(
                "Create Your own Bookcase and keep track with books You're about to read and You've already read",
                "Keep information about Authors and Their' books in one place",
                "Find more books to read written by Your favourite Author"
        ));
        return "home";
    }

    @GetMapping("/new-form")
    public String showNewBookForm(Model model){
        return "newForm" +
                "";
    }

//    @GetMapping("/authorsui")
//    public String showAuthors(Model model){
//        model.addAttribute("authors",authorController.getAuthors());
//        return "authors";
//    }
}
