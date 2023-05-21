package com.example.projectuas_kelompok6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    RecyclerView recyclerView;
    List<Product> productList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        SpannableString title = new SpannableString(getString(R.string.order));
        title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, title.length(), 0);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(title);

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
        displayData();

        addOnItemClickListener(recyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Product product   = productList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", product.getId());
                bundle.putString("judul", product.getJudul());
                bundle.putString("peminjam", product.getPeminjam());
                bundle.putString("rent_at", product.getRent());
                bundle.putString("return_at", product.getRetun());
                bundle.putString("denda", product.getDenda());
                bundle.putString("status", product.getStatus());
                Intent intent = new Intent(getActivity(), OrderBookActivity.class);
                intent.putExtra("dataorder", bundle);
                startActivity(intent);
            }
        });
        return view;
    }


    private void performSearch(String query) {
        String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/search_rent.php?query=" + query;

        StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {
            productList.clear();
            try {
                JSONObject jsonObj = new JSONObject(responses);
                Toast.makeText(getActivity(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                Log.d("TAG", "data length: " + jsonArray.length());
                Product product;
                productList.clear();
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject item = jsonArray.getJSONObject(i);

                    product = new Product();
                    product.setId(item.getString("id"));
                    product.setJudul(item.getString("judul"));
                    product.setPeminjam(item.getString("peminjam"));
                    product.setRent(item.getString("rent_at"));
                    product.setRetun(item.getString("return_at"));
                    product.setDenda(item.getString("denda"));
                    product.setStatus(item.getString("status"));

                    productList.add(product);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(new OrderAdapter(getActivity(), productList));
        }, Throwable::printStackTrace);
        Volley.newRequestQueue(getActivity()).add(strReq);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Panggil metode pencarian dengan query yang dimasukkan
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Panggil metode pencarian dengan query yang berubah saat diketikkan
                performSearch(newText);
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_tambah) {
            Intent intent = new Intent(getActivity(), OrderBookActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void displayData() {
        String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/rent/view_rent.php";

        StringRequest strReq = new StringRequest(Request.Method.GET, url, responses -> {
            productList.clear();
            try {
                JSONObject jsonObj = new JSONObject(responses);
                Toast.makeText(getActivity(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonObj.getJSONArray("data");
                Log.d("TAG", "data length: " + jsonArray.length());
                Product product;
                productList.clear();
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject item = jsonArray.getJSONObject(i);

                    product = new Product();
                    product.setId(item.getString("id"));
                    product.setJudul(item.getString("judul"));
                    product.setPeminjam(item.getString("peminjam"));
                    product.setRent(item.getString("rent_at"));
                    product.setRetun(item.getString("return_at"));
                    product.setDenda(item.getString("denda"));
                    product.setStatus(item.getString("status"));

                    productList.add(product);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(new OrderAdapter(getActivity(), productList));
        }, Throwable::printStackTrace);
        Volley.newRequestQueue(getActivity()).add(strReq);
    }

    public abstract class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;

        public RecyclerViewItemClickListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && gestureDetector.onTouchEvent(e)) {
                onItemClick(rv, childView, rv.getChildAdapterPosition(childView), rv.getChildItemId(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        public abstract void onItemClick(RecyclerView recyclerView, View view, int position, long id);
    }


    public void addOnItemClickListener(RecyclerView recyclerView, final OnItemClickListener listener) {
        recyclerView.addOnItemTouchListener(new BooksFragment.RecyclerViewItemClickListener(recyclerView.getContext()) {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
                listener.onItemClick(view, position);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}