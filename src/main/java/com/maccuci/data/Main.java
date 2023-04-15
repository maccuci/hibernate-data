package com.maccuci.data;

import com.maccuci.data.database.AccountManager;
import com.maccuci.data.database.account.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountManager accountManager = new AccountManager();
        System.out.println("==========[PAINEL]==========");
        System.out.println("1 - Criar uma conta.");
        System.out.println("2 - Procurar uma conta.");
        System.out.println("3 - Editar uma conta.");
        System.out.println("4 - Listar contas.");
        System.out.println("5 - Excluir conta.");
        System.out.println("0 - Sair.");
        System.out.println("==========[PAINEL]==========");

        switch (scanner.nextInt()) {
            case 1:
                Scanner data =  new Scanner(System.in);
                int id = 0;
                System.out.println("Insira um nome: ");
                String name = data.nextLine();
                System.out.println("Insira um ultimo nome: ");
                String lastname = data.nextLine();
                System.out.println("Insira um cargo: ");
                String role = data.nextLine();
                System.out.println("Insira um salario: ");
                double salary = data.nextDouble();

                accountManager.create(name, lastname, role, salary);
                main(args);
                break;
            case 2:
                data = new Scanner(System.in);
                System.out.println("Insira um ID: ");
                id = data.nextInt();

                Account account = accountManager.find(id);

                if(account != null)
                    accountManager.display(account);
                else
                    System.out.println("Conta não existente! Tente novamente.");
                main(args);
                break;
            case 3:
                data = new Scanner(System.in);
                System.out.println("Insira um ID: ");
                id = data.nextInt();
                Account find = accountManager.find(id);

                if(find != null) {
                    System.out.println("Insira um nome: ");
                    String findName = data.next();
                    System.out.println("Insira um ultimo nome: ");
                    String findLastname = data.next();
                    System.out.println("Insira um cargo: ");
                    String findRole = data.next();
                    System.out.println("Insira um salario: ");
                    double findSalary = data.nextDouble();

                    accountManager.edit(id, "name", findName);
                    accountManager.edit(id, "lastname", findLastname);
                    accountManager.edit(id, "role", findRole);
                    accountManager.edit(id, "salary", findSalary);
                }
                break;
            case 4:
                System.out.println("Listando todas as contas encontradas!");
                accountManager.list();
                break;
            case 5:
                System.out.println("Insira um ID: ");
                id = scanner.nextInt();

                accountManager.delete(id);
                break;
            default:
                main(args);
                break;
        }
    }
}