package com.codegym.customerversionx.controller;

import com.codegym.customerversionx.model.Customer;
import com.codegym.customerversionx.model.Province;
import com.codegym.customerversionx.service.CustomerService;
import com.codegym.customerversionx.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces() {
        return provinceService.findAll();
    }

    @GetMapping("/customers")
    public ModelAndView showList(@PageableDefault(5) Pageable pageable, @RequestParam("s")Optional<String> s) {
        String search = s.toString().substring(9, s.toString().length()-1);
        if (search.equals("empt")) search="";
        Page<Customer> customers;
        if (!s.isPresent()){
            customers = customerService.findAll(pageable);
        }else {
            customers = customerService.findAllByName(search, pageable);
        }
        ModelAndView modelAndView = new ModelAndView("customer/list");
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("search", search);
        return modelAndView;
    }

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView createCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        if (!bindingResult.hasFieldErrors()) {
            customerService.save(customer);
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("mess", "Created!");
        }else {
            modelAndView.addObject("customer" , new Customer());
            modelAndView.addAllObjects(bindingResult.getModel());
        }
        return modelAndView;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("customer/edit");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit-customer/{id}")
    public ModelAndView editCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("customer/edit");
        if (!bindingResult.hasFieldErrors()) {
            customerService.save(customer);
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("mess", "Edited!");
        }else {
            modelAndView.addObject("customer" , new Customer());
            modelAndView.addAllObjects(bindingResult.getModel());
        }
        return modelAndView;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("customer/delete");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

    @PostMapping("/delete-customer")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.remove(customer.getId());
        return "redirect:customers";
    }
}
