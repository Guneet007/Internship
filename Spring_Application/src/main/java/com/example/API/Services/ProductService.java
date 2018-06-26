package com.example.API.Services;

import com.example.API.Entities.Product;
import com.example.API.Exceptions.ProductException;
import com.example.API.Repository.ProductRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product>getProductsByOrder(BigInteger orderId) {
        if(productRepository.findAllByOrdersId(orderId).isEmpty())
            throw new ProductException("No product Found for order id="+orderId+"Or the order Id does not exist");
        return productRepository.findAllByOrdersId(orderId);
    }

    public Product getProductById(BigInteger id) {
        Optional<Product> product= productRepository.findById(id);
        if(!product.isPresent())
            throw new ProductException("No product found with id="+id);
        return product.get();
    }

    public  Product getProductByOrderId(BigInteger orderId,BigInteger productId) {
        return productRepository.findByIdAndOrdersId(productId,orderId);
    }

    public List<Product> getAllProducts(BigInteger id){
        List<Product> products=new ArrayList<>();
        productRepository.findByOrdersId(id)
                .forEach(products::add);
        if(products.isEmpty())
            throw new ProductException("No product Found for order id="+id);
        return products;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product>addProduct(List<Product>product){
        for(int i=0;i<product.size();i++)
            productRepository.save(product.get(i));
        return product;
    }

    public Product getProduct(BigInteger orderId,BigInteger id){
        return productRepository.findByIdAndOrdersId(id,orderId);
    }

    public Product updateProduct(Product product, BigInteger id,BigInteger userId,BigInteger orderId) throws Exception {
        Product productFromDB = getProduct(orderId,id);
        if(product !=null) {
            BeanUtils.copyProperties(product,productFromDB);
            productFromDB.setId(id);
        } else {
            throw new NotFoundException("not found");
        }
        return productRepository.save(productFromDB);
    }
    public Product updateProduct(Product product, BigInteger id) throws Exception {
        Product productFromDB = getProduct(id);
        if(product !=null) {
            BeanUtils.copyProperties(product,productFromDB);
            productFromDB.setId(id);
        } else {
            throw new NotFoundException("not found");
        }
        return productRepository.save(productFromDB);
    }

    public Product getProduct(BigInteger productId) {
        Optional<Product> product= productRepository.findById(productId);
        if(!product.isPresent())
            throw new ProductException("No product found with id="+productId);
        return product.get();
    }
    public void deleteProduct(BigInteger id) {
        productRepository.deleteById(id);
    }
}
