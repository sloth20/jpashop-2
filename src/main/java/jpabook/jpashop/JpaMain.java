package jpabook.jpashop;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("hello");
            member.setHomeAddress(new Address("seongnam", "pangyoro", "40404"));

            member.getFavoriteFoods().add("chicken");
            member.getFavoriteFoods().add("pizza");
            member.getFavoriteFoods().add("sushi");

            member.getAddressHistory().add(new Address("seoul", "yeonheero", "30303"));
            member.getAddressHistory().add(new Address("seoul", "cheongparo", "20202"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=========================================");
            Member findMember = em.find(Member.class, member.getId());
            Address homeAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", homeAddress.getStreet(), homeAddress.getZipcode()));

            findMember.getFavoriteFoods().remove("chicken");
            findMember.getFavoriteFoods().add("pasta");

            findMember.getAddressHistory().remove(new Address("seoul", "yeonheero", "30303"));
            findMember.getAddressHistory().add(new Address("seoul", "hangangro", "30303"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
