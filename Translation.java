import java.util.*;
import java.lang.*;
import java.io.FileWriter;
import java.io.IOException;

/*
* An explanation of the functions
* Convert - Converts a 32 bit binary string input into a floating point. Requires the BinToDec function
* to work. The input must be 32 bit binary String, no more no less. The output is a String containing the floating
* point translation
*
* BinToDec - A middleman function for the convert function. You don't need to call it in the final product,
* Convert does that for you.
*
* Save to File - Saves a String Parameter to a file called result.txt\
*
* convH2B - Converts an 8 digit HEX String input into a 32 bit binary String. Can handle upper or lower case letters
*
* IMPORTANT TO NOTE: The input for convert must be correct or it will result in an error. You will be in charge of error
* handling. All of the functions product a correct output already.
*
* If the input is HEX, call the convH2B Function, then plug it's output into the parameter of Convert. Convert's output
* should give you a String. If the input is a 32 bit binary, just put it into convert. Both Convert and convH2B only
* take in String. Make sure the program doesn't accept wrong inputs, or at least handles errors correctly.
*
* Thank you for reading
* */

public class Translation {

    static void SavetoFile(String line)
    {
        try {
            FileWriter myWriter = new FileWriter("result.txt");
            myWriter.write(line);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    static String convH2B (String hex){
        String binary = "";
        int x;
        char hexDigit; //to represent each digit of the hex

        hex = hex.toUpperCase();

        HashMap<Character, String> conv = new HashMap<>();
        conv.put('0', "0000");
        conv.put('1', "0001");
        conv.put('2', "0010");
        conv.put('3', "0011");
        conv.put('4', "0100");
        conv.put('5', "0101");
        conv.put('6', "0110");
        conv.put('7', "0111");
        conv.put('8', "1000");
        conv.put('9', "1001");
        conv.put('A', "1010");
        conv.put('B', "1011");
        conv.put('C', "1100");
        conv.put('D', "1101");
        conv.put('E', "1110");
        conv.put('F', "1111");

        for (x = 0; x < hex.length(); x++) {
            hexDigit = hex.charAt(x);

            if (conv.containsKey(hexDigit))
                binary += conv.get(hexDigit);
        }

        return binary;
    }
    static double BinToDec(String binary, int len)
    {
        int pt = binary.indexOf('.');

        if (pt == -1)
            pt = len;

        double intDec = 0, fracDec = 0, twos = 1;

        // Convert integral part of binary to decimal
        // equivalent
        for(int i = pt - 1; i >= 0; i--)
        {
            intDec += (binary.charAt(i) - '0') * twos;
            twos *= 2;
        }

        // Convert fractional part of binary to
        // decimal equivalent
        twos = 2;
        for(int i = pt + 1; i < len; i++)
        {
            fracDec += (binary.charAt(i) - '0') / twos;
            twos *= 2.0;
        }

        // Add both integral and fractional part
        return intDec + fracDec;
    }

    static String Convert(String bin)
    {
        int i;
        if(bin.equals("10000000000000000000000000000000"))
        {
            return "-0.0";
        }
        if(bin.equals("00000000000000000000000000000000"))
        {
            return "0.0";
        }
        if(bin.equals("11111111100000000000000000000000"))
        {
            return "Negative Infinity";
        }
        if(bin.equals("01111111100000000000000000000000"))
        {
            return "Positive Infinity";
        }
        char[] p = new char[8];
        char[] f = new char[152];
        Boolean sign=false;//false is positive true is negative
        StringBuilder sb = new StringBuilder();
        int neg = Integer.parseInt(Character.toString(bin.charAt(0)),10);
        //Character.toString(bin.charAt(0));
        if(neg==1){
            sign = true;

        }
        int j = 0;
        for(i=1;i<9;i++)
        {

            p[j] = bin.charAt(i);
            j++;
        }
        if(new String(p).equals("11111111"))
        {

           if(Integer.parseInt(Character.toString(bin.charAt(9)),10)==1)
           {
               return "qNaN";
           }
           else
               {
                   return "sNaN";
               }
        }
        f[0] = '1';
        j=1;
        int power = Integer.parseInt(new String(p),2) - 127;
        //System.out.println("Power: "+power);
        int pow = power+1;
        int len = 0;
        if (power <-126 && !sign )
        {
            return "denormalized";
        }
        else if(power<24&&power>-1)
         {
            for(i=9;i<32;i++)
            {
                if (pow==j)
               {
                    f[j] = '.';
                    j++;
                    i--;
               }

                else{
                f[j] = bin.charAt(i);
                j++;}
            }

            len = 25;
        }
        else if (power <= -1)
        {
            f[0]='.';
            len++;
            power++;
           while (power<=-1)
           {
               f[len]='0';
               power++;
               len++;
           }
            f[len]='1';
            len++;
            for(i=9;i<32;i++)
            {
                    f[len] = bin.charAt(i);
                    len++;
            }
        }
        else if (power >= 24)
        {
            f[len]='1';
            len++;
            for(i=9;i<32;i++)
            {
                f[len] = bin.charAt(i);
                len++;
            }
            power = power-23;
            //System.out.println(new String(f));
            while(power>0)
            {
                f[len] = 0;
                //System.out.println("Power:"+power+" Length:"+len);
                power--;
                len++;
            }
            //System.out.println(new String(f));
        }
        //System.out.println(new String(p)+"\n"+new String(f)+"\n"+power);
        //System.out.println(BinToDec(new String(f),len));

        double fractional = BinToDec(new String(f),len);
        j=0;


        String out = new String();
        if(sign) {
             out = "-"+Double.toString(fractional);
        }
        else
            {
                 out = Double.toString(fractional);
            }
        //System.out.println(out);//fractional+"x10^"+power);
        return out;
    }

    public static void main(String args[])
    {
        //1111110 exp -1
        //1111111100000000000000000000000
        //00000100000000000000000000000000
        //00000000001010000000000000000000 denormalized
        //11111111101010000000000000000000 sNaN
        //11111111111010000000000000000000 qNaN
        //10000000000000000000000000000000 -0.0
        //00000000000000000000000000000000 0.0
        String bin = "01000001010100101000000000000000";
        String S = Convert(bin);
        SavetoFile(S);
        System.out.println(S);
        //System.out.println(convH2B("0FDcD000")); Test translation
    }
}
