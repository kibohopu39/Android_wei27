package com.example.android_wei27;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mesg;
    private MyAsyncTask myAsyncTask_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mesg=findViewById(R.id.mesg);
    }

    public void test1(View view) {
        mesg.setText("");
        myAsyncTask_1=new MyAsyncTask();
        myAsyncTask_1.execute("ya","sd","yd");//-->傳進來的是剛剛的第一個Void,後面加點點點代表不定個數，骨子裡是陣列-->
        //背景可以傳網址、
    }

    public void test2(View view) {
        if (myAsyncTask_1!=null){
            Log.v("wei","status:"+myAsyncTask_1.getStatus().name());//證明真的在背景中，不然不能按
            if (!myAsyncTask_1.isCancelled()){
                myAsyncTask_1.cancel(true);//而且按一下不見得會把所有on系列的所有動作停止
            }
        }
    }






    private class MyAsyncTask extends AsyncTask<String,Object,String>{//傳遞出去的是第二個參數-->onprogress()
        //override裡的那些on系列可以用append、setText等等方法傳訊息到前景-->不需要用handler
        private int total,i;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v("wei","onPre");
            mesg.append("start...\n");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.v("wei","onPos"+result);
            mesg.append(result);
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            Log.v("wei","onPro"+values[0]);
            mesg.append(values[1]+"-->"+values[0]+"%\n");
        }

        @Override
        protected void onCancelled(String aVoid) {
            super.onCancelled(aVoid);
            mesg.append(aVoid);
            Log.v("wei","onCan_2");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.v("wei","onCan_1");
        }



        @Override
        protected String doInBackground(String... names) {
            Log.v("wei","doinback");
            total=names.length;
            for (String name:names
                 ) {
                i++;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("wei",name);
                //mesg.append();
                publishProgress((int)Math.ceil(i*100/total),name);
            }
            if (isCancelled()){
                return "Canceled";
            }else{
                return "Over";
            }

        }
    }
}
