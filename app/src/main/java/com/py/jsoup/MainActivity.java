package com.py.jsoup;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {

        TextView textView;
        EditText editText;
        Button button;
        ListView lv;
    ArrayList<String> questions = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        lv = (ListView) findViewById(R.id.dynamic);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                new MyTask().execute();
            }
        });


    }
    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String title ="";
            Document doc,document;
            String username = String.valueOf(editText.getText());
            username = username.replaceAll("\\s+","");


            try {
                int a = 0,b=0,c=0,d=0,k=0,f=0;
                    String pages = "";
                doc = Jsoup.connect("https://codeforces.com/submissions/"+ username ).get();
                for (Element e : doc.select(".pagination"))
                {
                    for (Element element : doc.select(".page-index"))
                   pages = element.text();
                }
                Log.i("MGSNM",pages);

                for (int count = 1; count <= Integer.parseInt(pages); count++)
                { doc = Jsoup.connect("https://codeforces.com/submissions/"+username+"/page/" + count).get();

                for (Element table : doc.select(".status-frame-datatable"))
                    //String number = row.text().substring(0, 7);
                    //questions.add(number);
                    for (Element row : table.select("tr:contains(Accepted)"))
                        for (Element element : row.select("[href*=/contest/]")) {
                            Log.i("MSGNM", String.valueOf(element.text()));

                           if (element.text().substring(0,1).equals("A")||element.text().substring(0,1).equals("B")||element.text().substring(0,1).equals("C")||element.text().substring(0,1).equals("D")||element.text().substring(0,1).equals("E")||element.text().substring(0,1).equals("F")){
                               questions.add(element.text());
                           }

                            String level = element.text().substring(0,1);

                            level = level.replace("#", "");
                            level = level.replaceAll("\\s+", "");
                            Log.i("MSGNM", level);


                            switch (level) {
                                case "A":
                                    a++;
                                    break;
                                case "B":
                                    b++;
                                    break;
                                case "C":
                                    c++;
                                    break;
                                case "D":
                                    d++;
                                    break;
                                case "E":
                                    k++;
                                    break;
                                case "F":
                                    f++;
                                    break;



                            }
                        }

                 }

                title += "A ::"+ a +"\nB ::" +b+ "\nC::"+ c +"\nD ::" +d+ "\nE ::"+ k +"\nF ::" +f;



            } catch (IOException e) {
                e.printStackTrace();
            }
            return title;
        }


        @Override
        protected void onPostExecute(String result) {
            //if you had a ui element, you could display the title
            ((TextView)findViewById (R.id.textView)).setText (result);
            arrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    simple_list_item_1,
                    questions);

            lv.setAdapter(arrayAdapter);
        }
    }
}
