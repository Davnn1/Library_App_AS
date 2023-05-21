package com.example.projectuas_kelompok6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ImageViewHolder>{
    Context context;
    List<Product> productList;

    //generate constructor
    public OrderAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.orderdata,null);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.judul.setText(productList.get(position).getJudul());
        holder.peminjam.setText(productList.get(position).getPeminjam());
        holder.tglpinjam.setText(productList.get(position).getRent()+" - "+productList.get(position).getRetun());
        holder.denda.setText("Denda = Rp."+productList.get(position).getDenda());
        holder.status.setText(productList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView judul, peminjam, tglpinjam, denda, status;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView= itemView.findViewById(R.id.rv);
            judul =itemView.findViewById(R.id.txt_judul);
            peminjam=itemView.findViewById(R.id.txt_peminjam);
            tglpinjam=itemView.findViewById(R.id.txt_tanggal);
            denda=itemView.findViewById(R.id.txt_denda);
            status= itemView.findViewById(R.id.txt_status);
        }
    }
}
