package org.springextensions.neodatis.example;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
        model.addAttribute("personList", personList);
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
        return "create";
    }

    @RequestMapping(value = "/delete/{firstname}", method = RequestMethod.GET)
    @Transactional
    public String deletePerson(@PathVariable("firstname") String firstname, RedirectAttributes redirectAttributes) {
        Person toDelete = this.personDao.findByFirstname(firstname);
        this.personDao.delete(toDelete);
        redirectAttributes.addFlashAttribute("deleteMessage", toDelete.getFirstname() + " " + toDelete.getLastname()
                + " deleted.");
        return "redirect:/";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Transactional
    public String createPersonForm(@Valid PersonForm personForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        Person p = personDao.findByFirstname(personForm.getFirstname());
        if (p != null) {
            bindingResult.rejectValue("firstname", "person.firstname.exist");
            return null;
        } else {
            personDao.save(personForm.getPerson());
            redirectAttributes.addFlashAttribute("createMessage",
                    personForm.getFirstname() + " " + personForm.getLastname() + " created ...");
        }
        return "redirect:/";
    }
}
