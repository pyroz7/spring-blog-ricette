package org.springblogricette.springricetta.Controller;

import jakarta.validation.Valid;
import org.springblogricette.springricetta.Model.Category;
import org.springblogricette.springricetta.Repository.CategoryRepository;
import org.springblogricette.springricetta.Repository.RepiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/category")
public class categoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RepiceRepository repiceRepository;


    @GetMapping
    public String index(Model model){
        List<Category> categoryList;
        categoryList = categoryRepository.findAll();
        model.addAttribute("category", categoryList);
        return "category/list";
    }

    @GetMapping("/create")
    public String create(Model model){
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/create";
    }

    @PostMapping("/create")
    public String category(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "category/create";
        }
        Category savedCategory = categoryRepository.save(formCategory);
        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,Model model){
        Optional<Category> result = categoryRepository.findById(id);
        if (result.isPresent()){
            model.addAttribute("category",result.get());
            return "category/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"category with id" + id + "not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("category") Category formCategory,BindingResult bindingResult){
        Optional<Category> result = categoryRepository.findById(id);
        if (result.isPresent()){
            Category categoryToEdit = result.get();
            if (bindingResult.hasErrors()){
                return "category/edit";
            }
            Category savedCategory = categoryRepository.save(formCategory);
            return"redirect:/category";
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category with id" + id + "not found");
        }
    }

    @PostMapping("/delete/{id}")
    public String delete (@PathVariable Integer id){
        Optional<Category> result = categoryRepository.findById(id);
        if(result.isPresent()){
            Category categoryToDelete = result.get();
            categoryRepository.delete(categoryToDelete);
            return "redirect:/category";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id" + id + "not found");
        }
    }
}
