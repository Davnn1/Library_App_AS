package com.example.projectuas_kelompok6;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HistoryFragment extends Fragment {
    
    RecyclerView recyclerView;
    List<Product> productList = new ArrayList<>();
    EditText awal, akhir;
    TextView total;
    ImageButton Search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.history_fragment, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        SpannableString title = new SpannableString(getString(R.string.history));
        title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, title.length(), 0);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(title);

        recyclerView = view.findViewById(R.id.rv);
        awal = view.findViewById(R.id.tgl1);
        akhir = view.findViewById(R.id.tgl2);
        Search = view.findViewById(R.id.cari);
        total = view.findViewById(R.id.total);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
        isiTgl(awal);
        isiTgl(akhir);
        displayData();
        cariData();
        addOnItemClickListener(recyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Product product   = productList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", product.getId());
                bundle.putString("judul", product.getJudul());
                bundle.putString("peminjam", product.getPeminjam());
                bundle.putString("rent_at", product.getRent());
                bundle.putString("return_at", product.getRetun());
                bundle.putString("denda", product.getDenda());
                bundle.putString("status", product.getStatus());
                Intent intent = new Intent(getActivity(), OrderBookActivity.class);
                intent.putExtra("order", bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private void isiTgl(EditText tgl){
        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(tgl);
            }
        });
    }

    private void showDateDialog(EditText tgl) {
        Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                String formattedDate = String.format(Locale.US, "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                tgl.setText(formattedDate);
            }
        };

        if (tgl == awal) {
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateSetListener, currentYear, currentMonth, currentDay);
            dialog.show();
        } else if (tgl == akhir) {
            String tanggalMulaiStr = awal.getText().toString();
            if (!tanggalMulaiStr.isEmpty()) {
                Calendar minDate = Calendar.getInstance();
                String[] tanggalMulaiArr = tanggalMulaiStr.split("-");
                int tahunMulai = Integer.parseInt(tanggalMulaiArr[0]);
                int bulanMulai = Integer.parseInt(tanggalMulaiArr[1]) - 1;
                int hariMulai = Integer.parseInt(tanggalMulaiArr[2]);
                minDate.set(tahunMulai, bulanMulai, hariMulai + 1); // Set minimum date to one day after tanggalMulai
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateSetListener, currentYear, currentMonth, currentDay);
                dialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                dialog.show();
            } else if(tanggalMulaiStr.isEmpty()){
                Toast.makeText(getActivity(),"Select Start Date First!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayData() {
        String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/view_kembali.php";

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
                total.setText("Total buku yang dipinjam = "+jsonArray.length());
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(new OrderAdapter(getActivity(), productList));
        }, Throwable::printStackTrace);
        Volley.newRequestQueue(getActivity()).add(strReq);
    }

    private void cariData(){
        Search.setOnClickListener(view -> {
            String url = AppConfig.IP_SERVER + "/UAS_LIBRARY/sorting_data.php";
            StringRequest strReq = new StringRequest(Request.Method.POST, url, responses -> {
                productList.clear();
                try {
                    JSONObject jsonObj = new JSONObject(responses);
                    Toast.makeText(getActivity(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = jsonObj.getJSONArray("data");
                    Log.d("TAG", "data length: " + jsonArray.length());
                    Product product;
                    productList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
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
                    total.setText("Total buku yang dipinjam = "+jsonArray.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(new OrderAdapter(getActivity(), productList));
            }, Throwable::printStackTrace) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("awal", awal.getText().toString());
                    params.put("akhir", akhir.getText().toString());
                    return params;
                }
            };
            Volley.newRequestQueue(getActivity()).add(strReq);
        });
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