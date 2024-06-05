package com.example.Demo4.services;

import com.example.Demo4.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private List<Product> listProduct = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    public ProductService() {}

    public void add(Product newProduct) {
        newProduct.setId((int) counter.incrementAndGet());
        listProduct.add(newProduct);
    }

    public List<Product> getAll() {
        return listProduct;
    }

    public Product get(long id) {
        return listProduct.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void edit(long id, Product editProduct) {
        Product product = get(id);
        if (product != null) {
            product.setName(editProduct.getName());
            product.setImage(editProduct.getImage());
            product.setPrice(editProduct.getPrice());
        }
    }

    public void delete(long id) {
        listProduct.removeIf(p -> p.getId() == id);
    }
    public List<Product> searchByName(String name) {
        return listProduct.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
