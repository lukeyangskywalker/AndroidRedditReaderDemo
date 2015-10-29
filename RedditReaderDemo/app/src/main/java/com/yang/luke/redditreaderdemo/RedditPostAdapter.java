package com.yang.luke.redditreaderdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by luke on 10/28/2015.
 */
public class RedditPostAdapter extends BaseAdapter {
    private static final String P1TAG = "VeryImportantMessage";
    private Bitmap bitmap;
    Context context;
    private static LayoutInflater inflater = null;
    private List<RedditPost> list;
    public RedditPostAdapter(MainActivity mainActivity, List<RedditPost> theList){
        context=mainActivity;
        list = theList;
        // remove the first element of the list, which is the title of the sub reddit
        list.remove(0);
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //RedditPost rp=new RedditPost();
        View rowView;
        rowView = inflater.inflate(R.layout.reddit_list, null);
        TextView title=(TextView) rowView.findViewById(R.id.textView1);
        TextView author=(TextView) rowView.findViewById(R.id.textView2);
        TextView date = (TextView) rowView.findViewById(R.id.textView3);
        ImageView image =(ImageView) rowView.findViewById(R.id.Image);

        // now set all field into view
        title.setText(list.get(position).getTitle());
        author.setText("by " + list.get(position).getAuthor());
        date.setText(list.get(position).getCreationTime().toString());

        //TODO: thumbnail for each post is wrong! this is a bug!!
        new LoadThumbnail().execute(list.get(position).getImgThumbnail());
        image.setImageBitmap(bitmap);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Test.makeText(context, "You Clicked " + list.get(position).getTitle(), Test.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

    /**
     * load the thumbnail using asyncTask
     */
    public class LoadThumbnail extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                Bitmap bp = null;
                Log.i(P1TAG, "Loading thumbnail : " + params[0]);
                if (params[0] != null){
                    bp = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());
                }
                return bp;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bp) {
            bitmap = bp;
            super.onPostExecute(bitmap);
        }
    }
}
