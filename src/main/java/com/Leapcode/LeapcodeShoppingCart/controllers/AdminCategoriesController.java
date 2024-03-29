package com.Leapcode.LeapcodeShoppingCart.controllers;

import com.Leapcode.LeapcodeShoppingCart.models.CategoryRepository;
import com.Leapcode.LeapcodeShoppingCart.models.data.Category;
import com.Leapcode.LeapcodeShoppingCart.models.data.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping
    public String index(Model model) {
        List<Category> categories = categoryRepo.findByOrderBySortingAsc();
        model.addAttribute("categories", categories);
        return "/admin/categories/index";
    }

    @GetMapping("/add")
    public String add(Category category) {
        return "/admin/categories/add";
    }

    @PostMapping("/add")
    public String add(@Valid Category category,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model) {
        if(bindingResult.hasErrors()) {
            return "admin/categories/add";
        }

        redirectAttributes.addFlashAttribute("message", "Category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String casing = category.getName().toLowerCase().replace(" ", "-");

        Category categoryExists = categoryRepo.findByName(casing);
        if(categoryExists != null) {
            redirectAttributes.addFlashAttribute("message", "Casing exists, choose another name");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }else {
            category.setCasing(casing);
            //page.setSorting(100);
            categoryRepo.save(category);
        }
        return "redirect:/admin/categories/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Category category = categoryRepo.getOne(id);
        model.addAttribute("category", category);
        return "admin/categories/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Category category,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        if(bindingResult.hasErrors()) {
            return "admin/categories/edit";
        }

        redirectAttributes.addFlashAttribute("message", "Category edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String casing = category.getName().toLowerCase().replace(" ", "-");
        Category categoryExists = categoryRepo.findByName(category.getName());

        if(categoryExists != null) {
            redirectAttributes.addFlashAttribute("message", "Casing exists, choose another name");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            //redirectAttributes.addFlashAttribute("category", category);
        }else {
            category.setCasing(casing);
            categoryRepo.save(category);
        }
        return "redirect:/admin/categories/edit/" + category.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        categoryRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/admin/categories";
    }

    @PostMapping("/reorder")
    public @ResponseBody
    String reorder(@RequestParam("id[]") int[] id) {
        int count = 1;
        Category category;
        for(int pageId: id) {
            category = categoryRepo.getOne(pageId);
            category.setSorting(count);
            categoryRepo.save(category);
            count++;
        }
        return "ok";
    }
}
