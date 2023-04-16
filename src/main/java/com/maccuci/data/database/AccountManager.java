package com.maccuci.data.database;

import com.maccuci.data.database.account.Account;
import lombok.Getter;

import javax.persistence.*;
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

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(account);
            entityManager.getTransaction().commit();
            System.out.println("Sucesso ao criar uma conta!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.printf("Erro ao criar uma conta %s", e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public Account find(Object obj) {
        if (obj == null) {
            return null;
        }
        return entityManager.find(Account.class, obj);
    }


    public void edit(int id, String parameter, Object obj) {
        Account account = find(id);

        if (account == null) return;

        switch (parameter) {
            case "name":
                String name = String.valueOf(obj);
                if (name != null && !name.isEmpty()) {
                    account.setName(name);
                }
                break;
            case "lastname":
                String lastname = String.valueOf(obj);
                if (lastname != null && !lastname.isEmpty()) {
                    account.setLastName(lastname);
                }
            case "role":
                String role = String.valueOf(obj);
                if (role != null && !role.isEmpty()) {
                    account.setRole(role);
                }
                break;
            case "salary":
                double salary = Double.parseDouble(obj.toString());
                if (salary != 0.0) {
                    account.setSalary(salary);
                }
                break;
            default:
                throw new IllegalArgumentException("Parâmetro inválido");
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(account);
            entityManager.getTransaction().commit();
            System.out.println("Sucesso ao editar a conta!");
        } catch (Exception e) {
            System.out.printf("Erro ao editar uma conta %s", e.getMessage());
        }
    }

    public void delete(int id) {
        Account account = find(id);

        if (account == null) return;

        entityManager.getTransaction().begin();
        try {
            entityManager.remove(account);
            entityManager.getTransaction().commit();
            System.out.println("Conta removida com sucesso!");
        } catch (Exception e) {
            System.out.printf("Erro ao remover conta: %s", e.getMessage());
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void list() {
        try {
            TypedQuery<Account> query = entityManager.createQuery("from Account", Account.class);
            List<Account> accounts = query.getResultList();

            accounts.forEach(this::display);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display(Account account) {
        System.out.printf("Nome %s\nSobrenome: %s\nCargo: %s\nSalário: %f", account.getName(), account.getLastName(), account.getRole(), account.getSalary());
    }
}
