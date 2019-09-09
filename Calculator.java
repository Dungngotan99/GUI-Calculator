import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Calculator extends JFrame implements ActionListener{
    // Declare all components
    double result =0;
    String sub = "";
    ArrayList<Double> arrInt = new ArrayList<Double>();
    ArrayList<String> arrString = new ArrayList<String>();

    JTextArea area1;
    JTextArea area2;

    JButton plus, minus, product, divide, enter, clear;
    JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b_dot,b_comma;
    JPanel paneN, paneS, paneE, paneW, paneC;

    //the View
    public Calculator(){
        setTitle("Calculator");
        setLayout(new BorderLayout());

        setupButton();
        setupPanel();
        addButton();

        area1 = new JTextArea();
        area2 = new JTextArea();
        paneN.add(area1,0);
        paneN.add(area2,1);

        addListener();

        setBounds(200,200,300,300);
        setVisible(true);
    }
    public void setupButton(){
        // declare all number buttons
        b0 = new JButton("0");
        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");
        b_dot = new JButton(".");
        b_comma = new JButton(",");

        //declare all operator buttons
        plus = new JButton("+");
        minus = new JButton("-");
        product = new JButton("*");
        divide = new JButton("/");

        //declare all output button
        enter = new JButton("=");
        clear = new JButton("AC");
    }
    public void setupPanel(){
        paneS = new JPanel(new GridLayout(1,1,15,5));
        paneE = new JPanel(new GridLayout(1,1,5,15));
        paneW = new JPanel(new GridLayout(4,1,5,10));
        paneC = new JPanel(new GridLayout(4,3,5,5));
        paneN = new JPanel(new GridLayout(2,1,5,5));

        add(paneE, BorderLayout.EAST);
        add(paneS, BorderLayout.SOUTH);
        add(paneW, BorderLayout.WEST);
        add(paneC, BorderLayout.CENTER);
        add(paneN, BorderLayout.NORTH);
    }
    public void addButton(){
        // add number to paneC
        paneC.add(b7,0);
        paneC.add(b8,1);
        paneC.add(b9,2);
        paneC.add(b4,3);
        paneC.add(b5,4);
        paneC.add(b6,5);
        paneC.add(b1,6);
        paneC.add(b2,7);
        paneC.add(b3,8);
        paneC.add(b0,9);
        paneC.add(b_dot,10);
        paneC.add(b_comma,11);

        // add operators to paneW
        paneW.add(plus,0);
        paneW.add(minus,1);
        paneW.add(product,2);
        paneW.add(divide,3);

        //add command to paneE
        paneE.add(clear);

        // add command to paneS
        paneS.add(enter);
    }
    public void addListener(){
        plus.addActionListener(this);
        minus.addActionListener(this);
        product.addActionListener(this);
        divide.addActionListener(this);

        b0.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        b_dot.addActionListener(this);
        b_comma.addActionListener(this);

        enter.addActionListener(this);
        clear.addActionListener(this);
    }

    //the Controller
    public void actionPerformed(ActionEvent e){
        String string = e.getActionCommand();
        //return the string of the label of that button
        //Every time I clicked on the button, actionPerformed() is called
        if (string.equals("AC")) Clear();
        else if (string.equals("+")) addArray("+");
        else if (string.equals("-")) addArray("-");
        else if (string.equals("*")) addArray("*");
        else if (string.equals("/")) addArray("/");
        else if (string.equals("=")){
            arrInt.add(Double.valueOf(sub));
            area2.append(String.valueOf(Calculate()));
        }
        else{
            area1.append(string);
            sub = sub + string;
        }
    }

    //the Model
    public void addArray(String operator){
        area1.append(operator);
        arrInt.add(Double.valueOf(sub));
        arrString.add(operator);
        sub = "";
    }
    public double Calculate(){
        ReduceProduct();
        ReduceDivide();

        result = arrInt.get(0);
        arrInt.remove(0);
        for (int i=0; i<arrString.size(); i++){
            if (arrString.get(i).equals( "+")) result = result + arrInt.get(i);
            else if (arrString.get(i).equals("-")) result = result - arrInt.get(i);
            else if (arrString.get(i).equals( "*")) result = result * arrInt.get(i);
            else if (arrString.get(i).equals( "/")) result = result + arrInt.get(i);
        }
        return result;
    }
    public void Clear(){
        area1.setText("");
        area2.setText("");
        sub = "";
        result = 0;
        arrInt = new ArrayList<Double>();
        arrString = new ArrayList<String>();
    }
    public void ReduceProduct(){
        int countProduct = 0;
        for (int i=0; i<arrString.size(); i++){
            if (arrString.get(i).equals("*")) countProduct++;
        }
        while (true){
            if (countProduct == 0) break;
            int i = arrString.indexOf("*");
            double a = arrInt.get(i)*arrInt.get(i+1);
            arrInt.add(i,a);
            arrInt.remove(i+1);
            arrInt.remove(i+1);
            arrString.remove(i);
            countProduct--;
        }
    }
    public void ReduceDivide(){
        int countDivide = 0;
        for (int i=0; i<arrString.size(); i++){
            if (arrString.get(i).equals("/")) countDivide++;
        }
        while (true){
            if (countDivide == 0) break;
            int i = arrString.indexOf("/");
            double a = arrInt.get(i)/arrInt.get(i+1);
            arrInt.add(i,a);
            arrInt.remove(i+1);
            arrInt.remove(i+1);
            arrString.remove(i);
            countDivide--;
        }
    }

    //main function
    public static void main(String agrs[]){
        new Calculator();
    }
}