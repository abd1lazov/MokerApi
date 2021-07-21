package com.example.mokerapi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mokerapi.R;
import com.example.mokerapi.data.MokerModel;
import com.example.mokerapi.databinding.ListMokerBinding;
import com.example.mokerapi.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MokerAdapter extends RecyclerView.Adapter<MokerAdapter.ViewHolder> {

    private final List<MokerModel> mokerlist = new ArrayList<>();
    private Callback callback;
    private MokerAdapter adapter;

    public MokerAdapter(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListMokerBinding itemView = ListMokerBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(itemView, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.onBind(mokerlist.get(position));
    }

    @Override
    public int getItemCount() {
        return mokerlist.size();
    }

    public void addItems(List<MokerModel> items) {
        mokerlist.clear();
        mokerlist.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ListMokerBinding listMokerBinding;
        private Callback callback;


        public ViewHolder(ListMokerBinding listMokerBinding, Callback callback) {
            super(listMokerBinding.getRoot());
            this.listMokerBinding = listMokerBinding;
            this.callback = callback;
        }

        public void onBind(MokerModel mokerModel) {
            listMokerBinding.textTitle.setText(mokerModel.getTitle());
            listMokerBinding.textContent.setText(mokerModel.getContent());
            listMokerBinding.textUser.setText("" + mokerModel.getUser());
            listMokerBinding.textGroup.setText("" + mokerModel.getGroup());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.Mokerclick(mokerModel);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setMessage("Do you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Toast.makeText(v.getRootView().getContext(), "clicked", Toast.LENGTH_SHORT).show();

                                    RetrofitBuilder.getInstance().deleteById(mokerModel.getId()).enqueue(new retrofit2.Callback<MokerModel>() {
                                        @Override
                                        public void onResponse(Call<MokerModel> call, Response<MokerModel> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<MokerModel> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                    dialog.dismiss();
                                }

                            });

                    builder.create().show();
                    return true;
                }
            });
        }
    }

    public interface Callback {
        void Mokerclick(MokerModel mokerModel);
    }
}
