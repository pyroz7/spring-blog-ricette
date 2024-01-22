package org.springblogricette.springricetta.Controller;

import jakarta.validation.Valid;
import org.springblogricette.springricetta.Model.Repice;
import org.springblogricette.springricetta.Repository.RepiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/repice")
public class repiceController {

    @Autowired
    private RepiceRepository repiceRepository;

    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String searchKeyword , Model model){
        List<Repice> repiceList;
        if (searchKeyword != null){
            repiceList = repiceRepository.findByTitleContainingOrIngredientsContaining(searchKeyword,searchKeyword);
        } else {
            repiceList = repiceRepository.findAll();
        }
        model.addAttribute("repiceList", repiceList);
        model.addAttribute("preloadSearch",searchKeyword);
        return "repice/list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model){
        Optional<Repice> result = repiceRepository.findById(id);
        if (result.isPresent()){
            Repice repice = result.get();
            model.addAttribute("repice",repice);
            return "repice/show";
        } else {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND),"Repice with id" + id + "not found");
        }
    }
    @GetMapping("/create")
    public String create(Model model){
        Repice repice = new Repice();
        model.addAttribute("repice",repice);
        return "repice/create";
    }

    @PostMapping("/create")
    public String store(@Valid@ModelAttribute("repice") Repice formRepice, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "repice/create";
        }
        Repice savedRepice = repiceRepository.save(formRepice);
        return "redirect:/repice/show/" + savedRepice.getId();
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable Integer id,Model model) {
        Optional<Repice> result = repiceRepository.findById(id);
        if (result.isPresent()){
            model.addAttribute("repice",result.get());
            return "repice/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Repice with id" + id + "not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,@Valid@ModelAttribute("repice")Repice formRepice, BindingResult bindingResult) {
        Optional<Repice> result = repiceRepository.findById(id);
        if (result.isPresent()) {
            Repice repiceEdit = result.get();
            if (bindingResult.hasErrors()) {
                return "repice/edit";

            }formRepice.setPhoto(repiceEdit.getPhoto());
            Repice savedRepice = repiceRepository.save(formRepice);
            return"redirect:/repice/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Repice with id" + id + "not found");
        }


    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Optional<Repice> result = repiceRepository.findById(id);
        if (result.isPresent()){
            repiceRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("redirectMessage", result.get().getTitle() + "was cancelled");
            return "redirect:/repice";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Repice with id" + id + "not found");
        }
    }


}