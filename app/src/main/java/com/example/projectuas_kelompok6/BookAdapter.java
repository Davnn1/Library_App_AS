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

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ImageViewHolder> {
    private Context context;
    private List<Product> productList;

    // Constructor
    public BookAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookdata, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Product product = productList.get(position);
        Picasso.get().load(product.getImage()).into(holder.imageView);
        holder.judul.setText(product.getJudul());
        holder.kategori.setText(product.getKategori() + ", " + product.getTahun());
        holder.tahun.setText(product.getPenerbit() + " - " + product.getPengarang());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView judul, tahun, kategori;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImageProduct);
            judul = itemView.findViewById(R.id.txt_judul);
            kategori = itemView.findViewById(R.id.txt_category);
            tahun = itemView.findViewById(R.id.txt_tahun);
        }
    }
}