/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Action;
import model.HomeModel;
import model.UserModel;
import view.HomeView;
import view.LoginView;
import view.RegisterView;

/**
 *
 * @author maqielhm
 */
public class LoginController {

    private LoginView view;
    private UserModel model;

    public LoginController(LoginView view, UserModel model) {
        this.view = view;
        this.model = model;

        view.setLoginEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = view.getUsername();
                String password = view.getPassword();
                doLogin(username, password);
                toHome(model);
            }
        });
        
        view.setRegisterEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toRegister();
            }
        });

    }

    public void showView() {
        view.show();
    }

    public void doLogin(String username, String password) {
        try {
            Koneksi kon = new Koneksi("localhost:3306", "db_sampleApps", "root", "");
            Statement state = kon.getConnection().createStatement();
            String query = "SELECT * FROM `t_user` WHERE `username` = '" + username + "' and `password` = SHA1('" + password + "')";
            System.out.println(query);
            ResultSet rs = state.executeQuery(query);
            while (rs.next()) {
                model.setId_user(rs.getString("id_user"));
                model.setUsername(rs.getString("username"));
                model.setNama(rs.getString("nama"));
            }
            System.out.println("LOGIN BERHASIL DENGAN NAMA : " + model.getNama());
            kon.closeConnection();
        } catch (SQLException ex) {
            System.err.println("GAGAL LOGIN :" + ex);
        }

    }

    public void toHome(UserModel user) {
        HomeController home = new HomeController(new HomeView(), new HomeModel(user.getNama()));
        home.updateView();
        view.dispose();
    }
    
    public void toRegister(){
        RegisterController register = new RegisterController(new UserModel(), new RegisterView());
        register.view.show();
    }

}
