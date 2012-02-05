package org.springextensions.neodatis.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {
	
	@Autowired
	private PersonDao personDao;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String listAll(Model model) {
		model.addAttribute(personDao.findAll());
		return "listAll";
	}
	
	@RequestMapping(value="/{firstname}")
	public String showByFirstname(@PathVariable("firstname") String firstname, Model model){
		Person person = personDao.findByFirstname(firstname);
		model.addAttribute(person);
		return "showPerson";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String createPersonForm(Model model) {
		PersonForm pForm = new PersonForm();
		model.addAttribute(pForm);
		return "createPerson";
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	@Transactional
	public String createPersonForm(PersonForm personForm, Model model, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return null;
		}
		Person p = personDao.findByFirstname(personForm.getFirstname());
		if(p != null){
			throw new RuntimeException();
		}else{
			personDao.save(p);
		}
		return "createPerson";
	}
}
