import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class s40231120_detector {
    static int edit_distance(String s1, String s2)
    {
        int length_of_string_1=s1.length();
        int length_of_string_2=s2.length();
        int [][]dp_array=new int[2][length_of_string_1+1];
        for (int i=0;i<=length_of_string_1;i++)
        {
            dp_array[0][i]=i;
        }

        for (int i=1;i<=length_of_string_2;i++){
            for (int j=0;j<=length_of_string_1;j++)
            {
                if (j == 0)
                    dp_array[i % 2][j] = i;

                else if(s1.charAt(j-1)==s2.charAt(i-1))
                {
                    dp_array[i % 2][j] = dp_array[(i - 1) % 2][j - 1];
                }
                else {
                    dp_array[i % 2][j] = 1 + Math.min(dp_array[(i - 1) % 2][j],
                            Math.min(dp_array[i % 2][j - 1],
                                    dp_array[(i - 1) % 2][j - 1]));
                }
            }
        }
        return dp_array[length_of_string_2%2][length_of_string_1];
    }
    static String source_code_preprocessing(String a,String[] keywords)
    {
        a=a.trim();
        a=a.replaceAll("\\p{P}", " ");
        a = a.replaceAll("[^a-z_A-Z]", " ");
        a=a.toLowerCase();
        for(int i=0;i< keywords.length;i++)
        {
            a.replace(keywords[i],"" );
        }
        a = a.replaceAll("\\b\\w{1,4}\\b\\s?", " ");
        a=a.trim().replaceAll(" +", " ");
        HashMap<String, Integer> map = new HashMap<String ,Integer>();

        String[] words = a.split(" ");
        a="";
        for (String word : words) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            }
            else
                map.put(word, 1);
        }
        for (Map.Entry<String, Integer> entry :
                map.entrySet()) {
            if (entry.getValue() == 1)
                a=a+ entry.getKey()+" ";
        }
        String [] arr = a.split(" ");
        Arrays.sort(arr, (String o1, String o2) -> o1.compareToIgnoreCase(o2));
        a="";
        for (String arr1 : arr) {
            a=a+arr1+" ";
        }
        return a;

    }
    static String preprocessing(String a)
    {
        a=a.trim();
        a=a.replaceAll("\\p{P}", " ");
        a = a.replaceAll("[^a-z_A-Z]", " ");

        a=a.toLowerCase();
        a = a.replaceAll("\\b\\w{1,3}\\b\\s?", " ");
        a=a.trim().replaceAll(" +", " ");
        String [] arr = a.split(" ");
        Arrays.sort(arr);
        StringBuilder ans=new StringBuilder(arr[0]);
        ans.append(" ");
        String prev=arr[0];
        for (String arr1 : arr) {
            if(prev.compareTo(arr1)!=0) {
                ans.append(arr1);
                ans.append(" ");
            }
            prev=arr1;
        }
        a=ans.toString();
        return a;
    }
    public static void main(String[] args) throws IOException {

        //long startTime = System.currentTimeMillis();
        String []keywords={"import","#include","namespace","bufferedreader","Stringbuilder","printf","scanf","java","class","static","range","throw","break","continue","int ","double ","string","&&","||","[]","char","int main","static","main","boolean","case","++","--"};
        String []reference_keywords={"wikipedia","http","https","th ed.","rd ed.","edition","author","journal","et al.","retrieved"};



        String pathname_1=args[0];
        String pathname_2=args[1];


        File file = new File(pathname_1);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s, file1 = "";
        boolean source_code_flag=false;
        int count_lines_with_code=0;

        while ((s = br.readLine()) != null) {
            file1 = file1 + s;
        }
        for(int i=0;i<keywords.length;i++)
        {
            if(file1.contains(keywords[i]))
                count_lines_with_code++;
        }

        if(count_lines_with_code>=7) source_code_flag=true;
        file = new File(pathname_2);
        br = new BufferedReader(new FileReader(file));
        String file2 = "";
        while ((s = br.readLine()) != null) {
            file2 = file2 + s;
        }


        if(source_code_flag==false)
        {
            boolean reference_flag_f1=false,reference_flag_f2=false;
            for(int i=0;i<reference_keywords.length;i++)
            {
                if(file1.toLowerCase().contains(reference_keywords[i]))
                    reference_flag_f1=true;
                if(file2.toLowerCase().contains(reference_keywords[i]))
                    reference_flag_f2=true;

            }
            if(reference_flag_f1==true &&reference_flag_f2==true)
            {
                System.out.println("0");
            }
            else {
                file1 = preprocessing(file1);
                file2 = preprocessing(file2);
                //System.out.println(edit_distance(file1,file2)+" "+file1.length());
                double percentage=(double)edit_distance(file1, file2) / Math.max(file1.length(), file2.length());
                //System.out.println(percentage);
                if (percentage<0.5 )
                    System.out.println("1");
                else System.out.println("0");
            }
        }
        else {
            file1=source_code_preprocessing(file1,keywords);
            file2=source_code_preprocessing(file2,keywords);
            if (Math.max(file1.length(), file2.length())/2 > edit_distance(file1, file2))
                System.out.println("1");
            else System.out.println("0");
        }

    }
}

