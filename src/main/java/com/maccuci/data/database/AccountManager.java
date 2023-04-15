package com.maccuci.data.database;

import com.maccuci.data.database.account.Account;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.File;
import java.util.List;

@Getter
public class AccountManager {

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("HibernateData");
    private final EntityManager entityManager;

    public AccountManager() {
        entityManager = FACTORY.createEntityManager();
    }

    public void create(String name, String lastname, String role, double salary) {
        Account account = new Account(name, lastname, role, salary);

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(account);
            entityManager.getTransaction().commit();
            System.out.println("Sucesso ao criar uma conta!");
        } catch (Exception e) {
            System.out.printf("Erro ao criar uma conta %s", e.getMessage());
        }
    }

    public Account find(Object obj) {
        Account account = null;
        try {
            account = entityManager.find(Account.class, obj);
        } catch (Exception e) {
            System.out.println("Erro ao procurar conta:");
            e.printStackTrace();
        }
        return account;
    }

    public void delete(int id) {
        Account account = find(id);

        if (account == null) return;

        entityManager.getTransaction().begin();
        try {
            entityManager.remove(account);
            entityManager.getTransaction().commit();
            System.out.println("Sucesso ao remover uma conta!");
        } catch (Exception e) {
            System.out.printf("Erro ao remover uma conta %s", e.getMessage());
        }
    }

    public void list() {
        try {
            Query query = entityManager.createQuery("from Account");
            List<Account> accounts = query.getResultList();

            for (Account account : accounts) {
                System.out.printf("ID: %d\nNome: %s\nSobrenome: %s\nCargo: %s\nSalário: %f\n", account.getId(), account.getName(), account.getLastName(), account.getRole(), account.getSalary());
            }
            entityManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display(Account account) {
        System.out.printf("Nome %s\nSobrenome: %s\nCargo: %s\nSalário: %f", account.getName(), account.getLastName(), account.getRole(), account.getSalary());
    }
}
