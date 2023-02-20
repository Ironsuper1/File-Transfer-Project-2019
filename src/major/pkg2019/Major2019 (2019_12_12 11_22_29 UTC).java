/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package major.pkg2019;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import javafx.stage.FileChooser;
import java.io.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.net.*;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JLayeredPane;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Optional;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Circle;

/**
 *
 * @author Drumil Sevak
 */
public class Major2019 extends Application {
    public static String s;
    public String yt;
    public int port =1234;
    public String SelectionADD; 
    private static ServerSocket ss;
    //private static int port = 1783;
    private static Socket x;
    static String file;
    static String host;
    static int read;
    private static final String DEFAULT_GATEWAY = "Default Gateway";
    public Boolean Quit = true;
    Thread ye = new Thread();
    public int e =1;
    int Dbyte;

    
    @Override
    public void start(Stage primaryStage) throws UnknownHostException {
        Pane root3 = new Pane();
        Pane root = new Pane();
        Pane root2 = new Pane();
        root.setStyle("-fx-background-color: lightslategray");

        
        
        Scene scene = new Scene(root, 600, 200);

        Scene scene2 = new Scene(root2, 600, 200);
        Scene Settscene = new Scene(root3, 600, 200);
        Button btn = new Button();
        btn.setText("Send");
        btn.setShape(new Circle(1.5));
        btn.setMinSize(100, 100);
        btn.setMaxSize(3,3);
        btn.setLayoutX(115.0);
        btn.setLayoutY(50.0);
        //btn.setPrefHeight(68.0);
        //btn.setPrefWidth(87.0);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            FileChooser chooser = new FileChooser();

            @Override
            public void handle(ActionEvent event) {
                Quit = true;
		File Selection = chooser.showOpenDialog(primaryStage);
		SelectionADD= Selection.getAbsolutePath();
                ye = new Thread(() -> {
                
                try {
                    Server();
		} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
                e=2;
                });
                ye.start();
                
            primaryStage.setScene(scene2);
           
            }
        });
        Button btn1 = new Button();
        btn1.setText("Receive");
        btn1.setLayoutX(400.0);
        btn1.setLayoutY(50.0);
        btn1.setShape(new Circle(1.5));
        btn1.setMinSize(100, 100);
        btn1.setMaxSize(3,3);
        //btn1.setPrefHeight(68.0);
        //btn1.setPrefWidth(87.0);
        btn1.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
           TextInputDialog InputName = new TextInputDialog();
           InputName.setTitle("Enter Name");
           InputName.setHeaderText("Enter the Name of the file and the Extention suffix");
           Optional<String> name = InputName.showAndWait();
           if (name.isPresent()){
               
               yt=name.get();
            }
           if(name.isPresent()){
          new Thread(() -> {
            try {
                Client();
                
            } catch (IOException e1) {
            e1.printStackTrace();
	}
          }).start();
            primaryStage.setScene(scene2);
           }
           
           
           }});
        Button btn2 = new Button();
        btn2.setText("Cancel");
        btn2.setLayoutX(275);
        btn2.setLayoutY(120);
        btn2.setShape(new Circle(1.5));
        btn2.setMinSize(75, 75);
        btn2.setMaxSize(3,3);
        btn2.setOnAction(new EventHandler<ActionEvent>(){
        @Override
            public void handle(ActionEvent event){
            primaryStage.setScene(scene);
            
            ye.stop();
            }
        });
        Button Settings = new Button();
        Settings.setText("Settings");
        Settings.setLayoutX(275);
        Settings.setLayoutY(140);
        Settings.setShape(new Circle(1.5));
        Settings.setMinSize(60, 60);
        Settings.setMaxSize(3,3);
        Settings.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
        primaryStage.setScene(Settscene);
        
        }});
        Button Port = new Button();
        Port.setText("Change Port");
        Port.setLayoutX(105);
        Port.setLayoutY(50);
        Port.setPrefHeight(50);
        Port.setPrefWidth(100);
        Port.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
           TextInputDialog Inputport = new TextInputDialog();
           Inputport.setTitle("Enter Name");
           Inputport.setHeaderText("Enter the Name of the file and the Extention suffix");
           Optional<String> name = Inputport.showAndWait();
           if (name.isPresent()){
               
                String PortS=name.get();
                port = Integer.parseInt(PortS);
            }
        }});
        
        Button Back = new Button();
        Back.setText("Back");
        Back.setLayoutX(400);
        Back.setLayoutY(50);
        Back.setPrefHeight(50);
        Back.setPrefWidth(100);
        Back.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            primaryStage.setScene(scene);
        }});
        ProgressBar PGBar = new ProgressBar();
        
        //PGBar.prefWidthProperty().bind(root2.widthProperty().subtract(20));
        root3.getChildren().add(Port);
        root3.getChildren().add(Back);
        root.getChildren().add(Settings);
        root2.getChildren().add(btn2);
        root2.getChildren().add(PGBar); 
        root.getChildren().add(btn);
        root.getChildren().add(btn1);
        primaryStage.setTitle("ApplicationMajorTest");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        launch(args);  
    }


    
    public void Server() throws IOException {
    
    ServerSocket servsock = new ServerSocket(port);
    File myFile = new File(SelectionADD);
    while (true) {
      Socket sock = servsock.accept();
      byte[] mybytearray = new byte[(int) myFile.length()];
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
      bis.read(mybytearray, 0, mybytearray.length);
      OutputStream os = sock.getOutputStream();
      os.write(mybytearray, 0, mybytearray.length);
      System.out.println(mybytearray);
      os.flush();
      sock.close();
    }
    }
   
public void Client() throws IOException {
    System.out.println(port);
    String er = InetAddress.getLocalHost().getHostAddress();
    Socket socket = new Socket(er, port);
    byte[] mybytearray = new byte[1024];
    InputStream is = socket.getInputStream();
    System.out.println(mybytearray);

    FileOutputStream fos = new FileOutputStream(yt);
    BufferedOutputStream bos = new BufferedOutputStream(fos);

    while ((Dbyte = is.read(mybytearray)) > 0){
        bos.write(mybytearray, 0, Dbyte);
    }
    bos.flush();
    bos.close();
    socket.close();
   
	
	
	
}
    
    
    
    
}

 



