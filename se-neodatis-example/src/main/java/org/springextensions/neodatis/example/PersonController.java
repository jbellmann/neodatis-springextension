package org.springextensions.neodatis.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PersonController {

    @Autowired
    private PersonDao personDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listAll(Model model) {
        List<Person> personList = personDao.findAll();
        model.addAttribute(personList);
        return "listAll";
    }

    @RequestMapping(value = "/{firstname}")
    public String showByFirstname(@PathVariable("firstname") String firstname, Model model) {
        Person person = personDao.findByFirstname(firstname);
        model.addAttribute(person);
        return "showPerson";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createPersonForm(Model model) {
        PersonForm pForm = new PersonForm();
        model.addAttribute(pForm);
        return "createPerson";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Transactional
    public String createPersonForm(PersonForm personForm, Model model, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        Person p = personDao.findByFirstname(personForm.getFirstname());
        if (p != null) {
            throw new UsernameExistException("A user with the firstname " + personForm.getFirstname()
                    + " exists already.");
        } else {
            personDao.save(personForm.getPerson());
            redirectAttributes.addFlashAttribute("message", "Person created ...");
        }
        return "redirect:/";
    }
}
