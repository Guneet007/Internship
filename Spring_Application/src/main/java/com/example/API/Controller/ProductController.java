package com.example.API.Controller;


import com.example.API.Entities.Orders;
import com.example.API.Entities.Product;
import com.example.API.Services.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@RequestMapping("/rest")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value="Show all products")
    @RequestMapping("/products")
    public Iterable<Product>getProducts() {
        return productService.getProducts();
    }

    @ApiOperation(value="Show a particular product")
    @RequestMapping("/product/{productId}")
    public Product ProductgetProductById(@PathVariable BigInteger productId) {
        return productService.getProductById(productId);
    }

    @ApiOperation(value="Show all products of an order")
    @RequestMapping("/orders/{orderId}/products")
    public List<Product>getProductsByOrder(@PathVariable BigInteger orderId) {
        return productService.getProductsByOrder(orderId);
    }

    @ApiOperation(value="Show a product of an Order")
    @RequestMapping("/orders/{orderId}/products/{productId}")
    public  Product getProductByOrderId(@PathVariable BigInteger orderId,@PathVariable BigInteger productId) {
        return productService.getProductByOrderId(orderId,productId);
    }

    @ApiOperation(value="Show all products of an order made by a user")
    @RequestMapping("users/{userId}/orders/{id}/products")
    public List<Product> getAllProducts(@PathVariable BigInteger id) {
        return productService.getAllProducts(id);
    }

    @ApiOperation(value="Show a product of an order made by a user")
    @RequestMapping("/users/{userId}/orders/{orderId}/products/{id}")
    public Product getProduct(@PathVariable BigInteger id,@PathVariable BigInteger orderId){
        return productService.getProduct(orderId,id);
    }

    @ApiOperation(value="Add a new Product")
    @RequestMapping(method = RequestMethod.POST,value = "/product")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }


    @ApiOperation(value="Adds a List of new Products")
    @RequestMapping(method = RequestMethod.POST,value = "/products")
    public List<Product> addProduct(@RequestBody List<Product> product){
        for(int i=0;i<product.size();i++){}
        //product.get(i).setOrders(new Orders(orderId,new Date())); }
        return productService.addProduct(product);
    }
    @ApiOperation(value="Add a new Product to an order")
    @RequestMapping(method = RequestMethod.POST,value = "users/{userId}/orders/{orderId}/product")
    public Product addProduct(@RequestBody Product product,@PathVariable BigInteger orderId,@PathVariable BigInteger userId){
        product.setOrders(new Orders(orderId,new Date(),userId));
        return productService.addProduct(product);
    }
    @ApiOperation(value="Adds a List of new Products to an order")
    @RequestMapping(method = RequestMethod.POST,value = "users/{userId}/orders/{orderId}/products")
    public List<Product> addProduct(@RequestBody List<Product> product,@PathVariable BigInteger orderId,@PathVariable BigInteger userId){
        for(int i=0;i<product.size();i++)
        product.get(i).setOrders(new Orders(orderId,new Date(),userId));
        return productService.addProduct(product);
    }

    @ApiOperation(value="Alter a Product of an order")
    @RequestMapping(method = RequestMethod.PUT, value="users/{userId}/orders/{orderId}/products/{id}")
    public Product updateProduct(@RequestBody Product product,@PathVariable BigInteger id,@PathVariable BigInteger orderId,@PathVariable BigInteger userId)throws Exception {
        product.setOrders(new Orders(orderId,new Date(),userId));
        return productService.updateProduct(product, id,userId,orderId);
    }

    @ApiOperation(value="Alter a Product")
    @RequestMapping(method = RequestMethod.PUT, value="/products/{productId}")
    public Product updateProduct(@RequestBody Product product,@PathVariable BigInteger productId)throws  Exception {
        //product.setOrders(new Orders(orderId,new Date()));
        return productService.updateProduct(product, productId);
    }

    @ApiOperation(value="Delete a Product")
    @RequestMapping(method = RequestMethod.DELETE, value="/products/{id}")
    public void deleteProduct(@PathVariable BigInteger id){
        productService.deleteProduct(id);
    }
}
