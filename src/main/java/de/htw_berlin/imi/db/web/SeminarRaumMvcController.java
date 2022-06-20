package de.htw_berlin.imi.db.web;

import de.htw_berlin.imi.db.services.SeminarRaumEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping(path = "/ui/seminarraeume")
public class SeminarRaumMvcController {

    @Autowired
    SeminarRaumEntityService seminarRaumEntityService;

    @GetMapping
    String findAll(final Model model) {
        model.addAttribute("seminarraeume", seminarRaumEntityService.findAll());
        model.addAttribute("seminarTemplate", new SeminarraumDto());
        return "seminarraeume";
    }

    @GetMapping("/{id}")
    String find(final Model model,
                @PathVariable("id") final long id) {
        model.addAttribute("seminarraeume",
                seminarRaumEntityService
                        .findById(id)
                        .orElseThrow(IllegalArgumentException::new));
        return "seminarraum-details";
    }

    @PostMapping("")
    String createSeminarraum(@ModelAttribute("seminarTemplate") final SeminarraumDto seminarTemplate) {
        seminarRaumEntityService.createFrom(seminarTemplate);
        // causes a page reload
        return "redirect:/ui/bueros";
    }

}

