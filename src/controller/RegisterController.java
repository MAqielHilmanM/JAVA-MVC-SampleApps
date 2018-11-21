/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import model.UserModel;
import view.RegisterView;

/**
 *
 * @author maqielhm
 */
public class RegisterController {

    UserModel model;
    RegisterView view;

    public RegisterController(UserModel model, RegisterView view) {
        this.model = model;
        this.view = view;

        view.setBackEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToLogin();  
            }
        });

        view.setRegisterEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getTFPassword().equals(view.getTFRePassword())) {
                    model.setNama(view.getTFNama());
                    model.setUsername(view.getTFUsername());
                    model.setPassword(view.getTFPassword());
                    doRegister(model);
                    backToLogin();
                }else {
                    view.showMessage("Password Not Match");
                    System.err.println("ERROR PASSWORD NOT MATCH");
                }

            }
        });
    }

    public void doRegister(UserModel model) {
        try {
            Koneksi kon = new Koneksi("localhost:3306", "db_sampleApps", "root", "");
            Statement state = kon.getConnection().createStatement();
            String query = "INSERT INTO `t_user` (`id_user`, `nama`, `username`, `password`)"
                    + " VALUES (NULL, '"
                    + model.getNama()
                    + "', '"
                    + model.getUsername()
                    + "', SHA1('"
                    + model.getPassword()
                    + "'));";
            int results = state.executeUpdate(query);
            if (results < 0) {
                System.err.println("Failed Add User");
            }else{
                System.out.println("Berhasil menambahkan User");
            }
        } catch (Exception e) {
            System.err.println("ERROR : " + e);
        }
    }
    
    public void backToLogin(){
        view.dispose();
    }

}
