package de.htw_berlin.imi.db.web;

import de.htw_berlin.imi.db.services.StudierendenEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping(path = "/ui/studierenden")
public class StudierendenMvcController {

    @Autowired
    StudierendenEntityService studierendenEntityService;

    @GetMapping
    String findAll(final Model model) {
        model.addAttribute("studierenden", studierendenEntityService.findAll());
        model.addAttribute("studierendeTemplate", new StudierendenDto());
        return "studierenden";
    }

    @GetMapping("/{id}")
    String find(final Model model,
                @PathVariable("id") final long id) {
        model.addAttribute("studierenden",
                studierendenEntityService
                        .findById(id)
                        .orElseThrow(IllegalArgumentException::new));
        return "studierenden-details";
    }

    @PostMapping("")
    String createStudierende(@ModelAttribute("studierendeTemplate") final StudierendenDto studierendeTemplate) {
        studierendenEntityService.createFrom(studierendeTemplate);
        // causes a page reload
        return "redirect:/ui/studierenden";
    }

}

