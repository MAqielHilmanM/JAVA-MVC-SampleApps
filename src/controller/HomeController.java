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
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.HomeModel;
import model.UserModel;
import view.HomeView;
import view.LoginView;
import view.RegisterView;

/**
 *
 * @author maqielhm
 */
public class HomeController {

    public HomeView view;
    public HomeModel model;
    public List<UserModel> lists;

    public HomeController(HomeView view, HomeModel model) {
        this.view = view;
        this.model = model;
        this.lists = new ArrayList<>();
        
        loadAllUser();
        view.setWelcome(model.getUser());
        
        view.setBtnDeleteEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDelete(lists.get(view.getTable().getSelectedRow()).getId_user());
            }
        });
        
        view.setBtnLogoutEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClickLogout();
            }
        });
        
        view.setBtnUpdateEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = view.getTable().getSelectedRow();
                String id = lists.get(index).getId_user();
                onUpdate(id);
            }
        });
        
        view.setBtnRefreshEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAllUser();
            }
        });
                ;
    }

    public void updateView() {
        view.show();
    }

    public void loadAllUser() {
        try {
            lists.clear();
            Koneksi kon = new Koneksi("localhost:3306", "db_sampleApps", "root", "");
            Statement state = kon.getConnection().createStatement();
            String query = "SELECT * FROM `t_user` ";
            System.out.println(query);
            ResultSet rs = state.executeQuery(query);
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setId_user(rs.getString("id_user"));
                user.setUsername(rs.getString("username"));
                user.setNama(rs.getString("nama"));
                lists.add(user);
            }
            kon.closeConnection();
        } catch (SQLException ex) {
            System.err.println("GAGAL LOGIN :" + ex);
        }
        loadToTable();
    }

    public void loadToTable() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnCount(3);
        dtm.setColumnIdentifiers(new String[]{"id", "nama", "username"});
        for (int i = 0; i < lists.size(); i++) {
            dtm.addRow(new String[]{lists.get(i).getId_user(), lists.get(i).getNama(), lists.get(i).getUsername()});
        }
        view.getTable().setModel(dtm);
    }
    
    public void onClickLogout(){
        LoginController loginController = new LoginController(new LoginView(), new UserModel());
        loginController.showView();
        this.view.dispose();
    }
    
    public void onDelete(String id){
        try {
            lists.clear();
            Koneksi kon = new Koneksi("localhost:3306", "db_sampleApps", "root", "");
            Statement state = kon.getConnection().createStatement();
            String query = "DELETE FROM `t_user` WHERE id_user = '"+id+"'";
            System.out.println(query);
            int result = state.executeUpdate(query);
            if(result > 0) {
//             Success
               loadAllUser();
            }else {
//             Failed
               System.err.println("GAGAL DELETE DATA");
            }
            kon.closeConnection();
        } catch (SQLException ex) {
            System.err.println("GAGAL DELETE :" + ex);
        }
    }
    
    public void onUpdate(String id){
        int index = view.getTable().getSelectedRow();
        RegisterController registerController = new RegisterController(lists.get(index), new RegisterView(),true);
        registerController.show();
    }
}
