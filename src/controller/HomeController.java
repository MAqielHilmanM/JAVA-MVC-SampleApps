/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.HomeModel;
import view.HomeView;

/**
 *
 * @author maqielhm
 */
public class HomeController {
    public HomeView view;
    public HomeModel model;

    public HomeController(HomeView view, HomeModel model) {
        this.view = view;
        this.model = model;
        
        view.setWelcome(model.getUser());
    }
    
    public void updateView(){
        view.show();
    }
    
    
}
