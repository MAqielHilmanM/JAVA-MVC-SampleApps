
import controller.LoginController;
import model.UserModel;
import view.LoginView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author maqielhm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        LoginController controller = new LoginController(new LoginView(), new UserModel());
        controller.showView();
    }
    
}
