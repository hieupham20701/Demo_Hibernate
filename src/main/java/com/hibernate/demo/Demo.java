/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hibernate.demo;

import com.hibernate.pojo.Category;
import com.hibernate.pojo.Manufacturer;
import com.hibernate.pojo.Product;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.query.Query;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
public class Demo {
    public static void main(String[] args) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<Category> query = session.createQuery("FROM Category");
//            List<Category> categories = query.list();
//            
//            categories.forEach(c -> System.out.println(c.getName()));
//        }
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
//            session.getTransaction().begin();
//            Product product =  new Product();
            Category category = session.get(Category.class,1);
            category.getProduct().forEach(product -> System.out.println(product.getName()));
//            Manufacturer m1 = session.get(Manufacturer.class, 1);
            Manufacturer m2 = session.get(Manufacturer.class, 2);
            m2.getProduct().forEach(product -> System.out.println(product.getName()));
//            
//            product.setName("Iphone 20");
//            product.setDescription("Apple, 128GB");
//            product.setPrice(new BigDecimal(30000000));
//            product.setCategory(category);
//            
//            Set<Manufacturer> manufacturers = new HashSet<>();
//            manufacturers.add(m1);
//            manufacturers.add(m2);
//            
//            product.setManufacturer(manufacturers);           
//            session.save(product);
//            session.getTransaction().commit();
        }
    }
}
