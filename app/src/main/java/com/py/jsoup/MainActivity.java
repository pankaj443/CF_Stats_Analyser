package com.py.jsoup;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        TextView textView;
        EditText editText;
        Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
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
            Document doc;
            String username = String.valueOf(editText.getText());
            username = username.replaceAll("\\s+","");
            ArrayList<String> questions = new ArrayList<>();

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

                for (Element table : doc.select(".status-frame-datatable")) {
                    for (Element row : table.select("tr:contains(Accepted)")) {




                        String number = row.text().substring(0,7);
                        questions.add(number);


                        String level = row.text().substring(27+username.length(),30+username.length());

                       level=level.replace("#","");
                        level = level.replaceAll("\\s+","");
                        Log.i("MSGNM",level);


                        switch (level)
                        {
                            case "A":a++;break;
                            case "B":b++;break;
                            case "C":c++;break;
                            case "D":d++;break;
                            case "E":k++;break;
                            case "F":f++;break;



                        }



                         }
                    }

                 }

                 title += "A ::"+ a +"\nB ::" +b+ "\nC::"+ c +"\nD ::" +d+ "\nE ::"+ k +"\nF ::" +f;
                Log.i("MSGNM",title);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return title;
        }


        @Override
        protected void onPostExecute(String result) {
            //if you had a ui element, you could display the title
            ((TextView)findViewById (R.id.textView)).setText (result);
        }
    }
}
