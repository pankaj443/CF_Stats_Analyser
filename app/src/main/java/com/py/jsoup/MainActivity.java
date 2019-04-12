package com.py.jsoup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {
    String item;
        TextView textView;
        EditText editText;
        Button button;
        ListView lv;
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> questionsa = new ArrayList<>();ArrayList<String> questionsb = new ArrayList<>();
    ArrayList<String> questionsc = new ArrayList<>();
    ArrayList<String> questionsd = new ArrayList<>();
    ArrayList<String> questionse = new ArrayList<>();
    ArrayList<String> questionsf = new ArrayList<>();
    Button pie;
    int a = 0,b=0,c=0,d=0,k=0,f=0;
    int total;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> categories = new ArrayList<String>();
        categories.add("All Levels");
        categories.add("Level A");
        categories.add("Level B");
        categories.add("Level C");
        categories.add("Level D");
        categories.add("Level E");
        categories.add("Level F");

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 item = parent.getItemAtPosition(position).toString();
               if (position == 2){arrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        simple_list_item_1,
                        questionsb);

               }else if (position == 1){arrayAdapter = new ArrayAdapter<String>(
                       getApplicationContext(),
                       simple_list_item_1,
                       questionsa);

                   }else if (position == 3){arrayAdapter = new ArrayAdapter<String>(
                       getApplicationContext(),
                       simple_list_item_1,
                       questionsc);

                   }else if (position == 4){arrayAdapter = new ArrayAdapter<String>(
                       getApplicationContext(),
                       simple_list_item_1,
                       questionsd);

                   }else if (position == 5){arrayAdapter = new ArrayAdapter<String>(
                       getApplicationContext(),
                       simple_list_item_1,
                       questionse);

                  }else if (position == 6){arrayAdapter = new ArrayAdapter<String>(
                       getApplicationContext(),
                       simple_list_item_1,
                       questionsf);

                  }if (position == 0){arrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        simple_list_item_1,
                        questions);

                    }
                lv.setAdapter(arrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,categories);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        pie = (Button)findViewById(R.id.pie);
        pie.setAlpha(0);
        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this,pieActivity.class);
                intent.putExtra("a",String.valueOf(a));
                intent.putExtra("b",String.valueOf(b));
                intent.putExtra("c",String.valueOf(c));
                intent.putExtra("d",String.valueOf(d));
                intent.putExtra("k",String.valueOf(k));
                intent.putExtra("f",String.valueOf(f));

                startActivity(intent);

            }
        });
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
        private ProgressDialog dialog;


        public MyTask() {
            dialog = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Getting Data, please wait.");
            dialog.show();
        }

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
                               if (element.text().substring(0,1).equals("A")){
                                   questionsa.add(element.text());
                               }else if (element.text().substring(0,1).equals("B")){
                                   questionsb.add(element.text());
                               }else if (element.text().substring(0,1).equals("C")){
                                   questionsc.add(element.text());
                               }else if (element.text().substring(0,1).equals("D")){
                                   questionsd.add(element.text());
                               }else if (element.text().substring(0,1).equals("E")){
                                   questionse.add(element.text());
                               }else if (element.text().substring(0,1).equals("F")){
                                   questionsf.add(element.text());
                               }
                           }

                            String level = element.text().substring(0,1);

                            level = level.replace("#", "");
                            level = level.replaceAll("\\s+", "");


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
               // int total=0;
               // total = a+b+c+d+k+f;
                //((TextView)findViewById (R.id.textView1)).setText ("TOTAL::"+String.valueOf(total));



            } catch (IOException e) {
                e.printStackTrace();
            }
            return title;
        }


        @Override
        protected void onPostExecute(String result) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            pie.setAlpha(1);


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
