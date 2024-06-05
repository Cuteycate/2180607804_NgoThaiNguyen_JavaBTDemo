package com.example.Demo4.Controller;

import com.example.Demo4.models.Product;
import com.example.Demo4.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    private static final String UPLOADED_FOLDER = "src/main/resources/static/images/";

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "products/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("product") Product newProduct,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            return "products/create";
        }

        if (!imageFile.isEmpty()) {
            try {
                byte[] bytes = imageFile.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + imageFile.getOriginalFilename());
                Files.write(path, bytes);
                newProduct.setImage(imageFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productService.add(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        Product product = productService.get(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") long id,
                         @Valid @ModelAttribute("product") Product product,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", product);
            return "products/edit";
        }

        Product existingProduct = productService.get(id);

        if (existingProduct == null) {
            return "redirect:/products";
        }

        if (!imageFile.isEmpty()) {
            try {
                byte[] bytes = imageFile.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + imageFile.getOriginalFilename());
                Files.write(path, bytes);
                product.setImage(imageFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            product.setImage(existingProduct.getImage());
        }

        productService.edit(id, product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        productService.delete(id);
        return "redirect:/products";
    }
    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Product> searchResults = productService.searchByName(query);
        model.addAttribute("listproduct", searchResults);
        return "products/index";
    }
}
