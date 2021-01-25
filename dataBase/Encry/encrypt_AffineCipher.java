
















public class AffineCipher extends Application {

    static int number;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Affine project ");
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);

        GridPane gridPane1 = new GridPane();

        Scene scene = new Scene(gridPane1);

        GridPane gridpane3 = new GridPane();
        GridPane gridpane2 = new GridPane();

        gridPane1.add(gridpane3, 0, 3);
        gridPane1.add(gridpane2, 0, 1);

        Label l2 = new Label("SECURITY AFFINE PROJECT ");
        gridPane1.add(l2, 0, 0);
        l2.setStyle("-fx-font: normal bold 50px 'serif'; -fx-text-fill: firebrick; -fx-padding:30px");

        Label l5 = new Label("enter the message");
        gridpane2.add(l5, 0, 0);
        l5.setStyle("-fx-font: normal bold 40px 'serif'; -fx-text-fill: firebrick");

        Label l3 = new Label("enter the M value ");
        gridpane2.add(l3, 0, 1);
        l3.setStyle("-fx-font: normal bold 40px 'serif'; -fx-text-fill: firebrick");

        Label l4 = new Label("enter the key value ");
        gridpane2.add(l4, 0, 2);
        l4.setStyle("-fx-font: normal bold 40px 'serif'; -fx-text-fill: firebrick");

        TextField message_txtField = new TextField();
        gridpane2.add(message_txtField, 1, 0);
        message_txtField.setFont(Font.font("Bold", 20));

        TextField M_value = new TextField();
        gridpane2.add(M_value, 1, 1);
        M_value.setFont(Font.font("Bold", 15));

        TextField key_value = new TextField();
        gridpane2.add(key_value, 1, 2);
        key_value.setFont(Font.font("Bold", 15));

        Label showMessage_label = new Label("");
        showMessage_label.setStyle("-fx-font: normal bold 50px 'serif'; -fx-text-fill: firebrick; -fx-padding:30px");
        gridPane1.add(showMessage_label, 0, 2);

        Button encryptButton = new Button("encrypt");
        gridpane3.add(encryptButton, 3, 1);
        encryptButton.setStyle(" -fx-text-fill: white; -fx-background-color: royalblue; -fx-padding:15px 50px; -fx-font-size:17px;-fx-font: normal bold 20px 'serif'");

        Button decryptButton = new Button("decrypt");
        gridpane3.add(decryptButton, 4, 1);
        decryptButton.setStyle(" -fx-text-fill: white; -fx-background-color: red; -fx-padding:15px 50px; -fx-font-size:17px;-fx-font: normal bold 20px 'serif'");

        encryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String cipherText = AffineCipher.encryptionMessage(message_txtField.getText(), M_value.getText(), key_value.getText());
                showMessage_label.setText(cipherText);

            }
        });

        decryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String plainText = AffineCipher.decryptionMessage(message_txtField.getText(), M_value.getText(), key_value.getText());
                showMessage_label.setText(plainText);
            }
        });

        gridpane3.setHgap(50);
        gridPane1.setHgap(50);
        gridpane2.setHgap(50);

        gridpane3.setVgap(50);
        gridpane3.setVgap(50);
        gridPane1.setVgap(50);

        gridPane1.setAlignment(Pos.CENTER);
        // gridPane1.setStyle("-fx-background-color: #004km2j");

        primaryStage.setScene(scene);

        //gridPane1.setStyle("-fx-background-image: url(\"background.png\");");
        primaryStage.show();
        gridPane1.setStyle("-fx-background-color:khaki");
    }

    /////////////////////////
    //encryption method 
    public static String encryptionMessage(String Msg, String m, String s) {
        String cipher = "";
        int new_m = Integer.parseInt(m);
        int new_s = Integer.parseInt(s);

        for (int i = 0; i < Msg.length(); i++) {
            
                 cipher = cipher + (char) ((((new_m * (Msg.charAt(i) - 97)) + new_s) % 26) + 97);
                
            

           

            // x++;

            /* while (x < number) {
                x++;
            }*/
        }

        return cipher;
    }

    //decryption method 
    public static String decryptionMessage(String CTxt, String m, String s) {
        int Msg = 0;
        String plain = "";
        int new_m = Integer.parseInt(m);
        int new_s = Integer.parseInt(s);

        // calculate multiblicative inverse 
        int a_inv = 0;
        int flag = 0;

        for (int i = 0; i < 26; i++) {
            flag = (new_m * i) % 26;
            if (flag == 1) {
                a_inv = i;
                System.out.println("inverse of M " + i);
            }
        }
        for (int i = 0; i < CTxt.length(); i++) {

            if ((CTxt.charAt(i) - 97) < new_s) {

                // Msg = (((a_inv * (((CTxt.charAt(i) - 97) - new_s)) * -1) - 26) + 97);
                Msg = (((a_inv * ((CTxt.charAt(i) - 97) - new_s)) + 26));

                while (Msg < 0) {
                    Msg += 26;
                }

                /*while (Msg > 122) {
                    Msg = Msg - 26;
                }*/
                Msg = Msg + 97;
                plain = plain + (char) Msg;

            } else {

                Msg = ((a_inv * (((CTxt.charAt(i) - 97) - new_s)) % 26) + 97);

                plain = plain + (char) Msg;

            }

        }

        return plain;
    }

    public static void main(String[] args) {

        launch(args);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of language : ");
        number = Integer.parseInt(sc.next());
        System.out.println("number is :" + number);
        /*System.out.println("Encrypted Message is : "
                + encryptionMessage(message, "7", "10"));
        System.out.println("Decrypted Message is: "
                + decryptionMessage(encryptionMessage(message, "7", "10"), "7", "10"));*/
        sc.close();

    }

}
