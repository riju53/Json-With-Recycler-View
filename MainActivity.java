package com.example.rijunath.json_with_recycler_view;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    String url="http://220.225.80.177/onlineShoppingapp/Show.asmx/GetProduct?catid=4";
    ArrayList<Product_Setget> arrayList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=(RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        Data da=new Data();
        da.execute("Data");
    }
    public class Data extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            JSONParser jp =new JSONParser();
            JSONObject jobj=jp.getJsonFromURL(url);
            try {
                JSONArray jarr=jobj.getJSONArray("Products");
                arrayList=new ArrayList<Product_Setget>();
                for (int i=0;i<jarr.length();i++){
                    JSONObject job=jarr.getJSONObject(i);
                    String Product_Id=job.getString("Product_Id");
                    String Category_Id=job.getString("Category_Id");
                    String Product_Image=job.getString("Product_Image");

                    Product_Setget ps=new Product_Setget();
                    ps.setProduct_Image(Product_Id);
                    ps.setCategory_Id(Category_Id);
                    ps.setProduct_Image(Product_Image);
                    arrayList.add(ps);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            rv.setAdapter(new Myadp());
            super.onPostExecute(s);
        }
    }
    public class Myholder extends RecyclerView.ViewHolder{
        TextView pid,cid;
        WebView imid;
        public Myholder(View itemView) {
            super(itemView);
            pid=(TextView)itemView.findViewById(R.id.pid);
            cid=(TextView)itemView.findViewById(R.id.cid);
            imid=(WebView)itemView.findViewById(R.id.imid);
        }
    }
    public class Myadp extends RecyclerView.Adapter<Myholder>{

        @Override
        public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inf=getLayoutInflater();
            View v=inf.inflate(R.layout.second, parent, false);
            Myholder holder=new Myholder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(Myholder holder, int position) {
            Product_Setget ps=new Product_Setget();
            ps=arrayList.get(position);
            holder.pid.setText(ps.getProduct_Id());
            holder.cid.setText(ps.getCategory_Id());
            holder.imid.loadUrl(ps.getProduct_Image());
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}
