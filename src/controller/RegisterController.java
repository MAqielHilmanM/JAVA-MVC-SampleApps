/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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

//    Constructor for Register
    public RegisterController(UserModel model, RegisterView view) {
        this.model = model;
        this.view = view;

        view.setBackEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        view.setRegisterEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getTFPasswordText().equals(view.getTFRePasswordText())) {
                    model.setNama(view.getTFNamaText());
                    model.setUsername(view.getTFUsernameText());
                    model.setPassword(view.getTFPasswordText());
                    doRegister(model);
                    dispose();
                } else {
                    view.showMessage("Password Not Match");
                    System.err.println("ERROR PASSWORD NOT MATCH");
                }

            }
        });
    }

//    Constructor For Update
    public RegisterController(UserModel model, RegisterView view, boolean isUpdate) {
        this.model = model;
        this.view = view;

        if (isUpdate && model != null) {

            view.setLblTitle("UPDATE");
            view.getBtnRegister().setText("Update");
            view.setTFNamaText(model.getNama());
            view.setTFUsernameText(model.getUsername());

            view.setBackEvent(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            view.setRegisterEvent(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (view.getTFPasswordText().equals(view.getTFRePasswordText())) {
                        model.setNama(view.getTFNamaText());
                        model.setUsername(view.getTFUsernameText());
                        model.setPassword(view.getTFPasswordText());
                        doUpdate(model);
                        dispose();
                    } else {
                        view.showMessage("Password Not Match");
                        System.err.println("ERROR PASSWORD NOT MATCH");
                    }

                }
            });
        } else {
            view.setBackEvent(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            view.setRegisterEvent(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (view.getTFPasswordText().equals(view.getTFRePasswordText())) {
                        model.setNama(view.getTFNamaText());
                        model.setUsername(view.getTFUsernameText());
                        model.setPassword(view.getTFPasswordText());
                        doRegister(model);
                        dispose();
                    } else {
                        view.showMessage("Password Not Match");
                        System.err.println("ERROR PASSWORD NOT MATCH");
                    }

                }
            });
        }

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
            } else {
                System.out.println("Berhasil menambahkan User");
            }
        } catch (Exception e) {
            System.err.println("ERROR : " + e);
        }
    }

    public void doUpdate(UserModel model) {
        try {
            Koneksi kon = new Koneksi("localhost:3306", "db_sampleApps", "root", "");
            Statement state = kon.getConnection().createStatement();
            String query = "UPDATE t_user SET "
                    + " nama = '"
                    + model.getNama()
                    + "', username = '"
                    + model.getUsername()
                    + "', password = SHA1('"
                    + model.getPassword()
                    + "') WHERE id_user = '" + model.getId_user() + "'";
            int results = state.executeUpdate(query);
            if (results < 0) {
                System.err.println("Gagal Merubah User");
            } else {
                System.out.println("Berhasil Merubah User");
            }
        } catch (Exception e) {
            System.err.println("ERROR : " + e);
        }
    }

    public void dispose() {
        view.dispose();
    }

    public void show(){
        view.show();
    }
}
