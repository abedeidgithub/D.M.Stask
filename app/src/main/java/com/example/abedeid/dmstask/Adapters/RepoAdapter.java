package com.example.abedeid.dmstask.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abedeid.dmstask.MainActivity;
import com.example.abedeid.dmstask.R;
import com.example.abedeid.dmstask.obj.Repo;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by abed eid on 09/05/2019.
 */

public class RepoAdapter extends  RecyclerView.Adapter<RepoAdapter.MyViewHolder> {


        List<Repo> RepoList;
        Context context;

public RepoAdapter( List<Repo> RepoList, Context context) {
        this.RepoList = RepoList;
        this.context = context;
        }

@Override
public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType ==1) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false));

    } else {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item_green, parent, false));


    }
        }

@Override
public void onBindViewHolder(final MyViewHolder holder, int position) {
    int type = getItemViewType(position);
       Repo CurrentPost = RepoList.get(position);
        holder.name.setText(CurrentPost.getFullName());
        holder.des.setText(CurrentPost.getDescription());
        holder.username.setText(CurrentPost.getOwner().getLogin());
        holder.Rep_url=CurrentPost.getHtmlUrl();
        holder.Owen_URL=CurrentPost.getOwner().getHtmlUrl();
    if(CurrentPost.getFork()==Boolean.FALSE){
      holder.card_view.setCardBackgroundColor(ContextCompat.getColor(this.context,R.color.green));
    }


}
    @Override
    public int getItemViewType(int position) {


        Repo post = RepoList.get(position);

        if (post.getFork()== true) {
            return 1;
        } else {
            return 0;
        }
     }

    @Override
    public int getItemCount() {
        return RepoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView name,des,username;
    CardView card_view;
        String Rep_url,Owen_URL,url;
    public MyViewHolder(final View itemView) {
        super(itemView);
        card_view=(CardView)itemView.findViewById(R.id.card_view);
        name = (TextView) itemView.findViewById(R.id.name);
        des = (TextView) itemView.findViewById(R.id.des);
        username = (TextView) itemView.findViewById(R.id.username);
        card_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder adb = new AlertDialog.Builder(itemView.getContext());
                final CharSequence items[] = new CharSequence[] {"Repo URL", "Owner URL"};
                adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                        url =Rep_url;
                        }else if(i==1){
                            url = Owen_URL;
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        context.startActivity(intent);
                    }
                });
                adb.setTitle("choose  one URL ?");
                adb.show();

                return false;
            }
        });
    }
}
}