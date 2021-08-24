/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hibernate.demo;

import com.hibernate.pojo.Category;
import com.hibernate.pojo.Product;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class DemoCriterial {
    
    public static void queryDemo(){
        try(Session session = HibernateUtil.getSessionFactory().openSession() ){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root);
            
            ///Select name, description
            Predicate p1 = builder.like(root.get("name").as(String.class),"%Iphone%");
            Predicate p2 = builder.like(root.get("description").as(String.class),"%Iphone%");
            Predicate p3 = builder.between(root.get("price").as(BigDecimal.class),new BigDecimal(20000000),new BigDecimal(30000000));
            
            query.where(builder.and(builder.or(p1,p2), p3));
            
 
            Query<Product> q = session.createQuery(query);
            List<Product> products = q.getResultList();
            
            products.forEach(p -> System.out.printf("%s: %.2f\n",p.getName(),p.getPrice()));
        }
    }
    public static void statsDemo(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<Product> root = query.from(Product.class);
            query.multiselect(builder.count(root.get("id")),
                              builder.max(root.get("price").as(BigDecimal.class)),
                              builder.avg(root.get("price").as(BigDecimal.class)));
            Query<Object[]> q = session.createQuery(query);
            Object[] r = q.getSingleResult();
            System.out.println("So luong san pham: "+ r[0]);
            System.out.println("Gia cao nhat: "+r[1]);
            System.out.println("Trung binh gia: "+r[2]);       
        }
    }
    public static void groupDemo(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            
            Root<Product> pRoot = query.from(Product.class);
            Root<Category> cRoot = query.from(Category.class);
            
            query = query.where(builder.equal(pRoot.get("category"), cRoot.get("id")));
            
            query.multiselect(cRoot.get("name").as(String.class),
                               builder.count(pRoot.get("id")),
                               builder.max(pRoot.get("price").as(BigDecimal.class)),
                               builder.avg(pRoot.get("price").as(BigDecimal.class)));
        
            query.groupBy(cRoot.get("name").as(String.class), pRoot.get("id"));
            query.orderBy(builder.asc(cRoot.get("name").as(String.class)));
            
            Query<Object[]> q = session.createQuery(query);
            List<Object[]> obj = q.getResultList();
            
            obj.forEach(o -> {
                System.out.printf("%s: %d - %.2f\n",o[0],o[1],o[2]);
            });
        }
    }
    public static void main(String[] args) {
//        statsDemo();
          groupDemo();
    }
}
