package Maxtree;

import Detection.Launch;
import Maxtree.BTree;
import application.UI;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static javafx.application.Application.launch;

public class test {

    public static void main(String[] args) throws IOException {
        Launch launchs=new Launch();
        BTree bTree=new BTree();
        HashMap<String,Double>simi=launchs.start("BubbleSort");
        Object[][]kth=bTree.findkth(simi);
        for(int i=0;i<5;i++){
            System.out.println(kth[i][0]+": "+kth[i][1]);
            String filePath = Launch.DefaultPath + "\\" +kth[i][0];
            File file = new File(filePath);
            FileInputStream in = new FileInputStream(file);
            InputStreamReader ipr = new InputStreamReader(in, "GBK");
            BufferedReader bf = new BufferedReader(ipr);
            String str="";
            while ((str = bf.readLine()) != null) {
                System.out.println(str);
            }
        }

    }
}
