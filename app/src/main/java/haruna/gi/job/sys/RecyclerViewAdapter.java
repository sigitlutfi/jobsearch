package haruna.gi.job.sys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import haruna.gi.job.Login;
import haruna.gi.job.Main;
import haruna.gi.job.R;

/**
 * Created by sulistiyanto on 07/12/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Result> results;

    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id";
    public static final String TAG_EMAIL = "email";

    public RecyclerViewAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.textViewNPM.setText(result.getNama_p());
        holder.textViewNama.setText(result.getAlamat_p());
        holder.textViewKelas.setText(result.getBidang());
        holder.textViewSesi.setText(result.getDeskripsi());
        holder.textViewGaji.setText("Gaji : "+result.getGaji());
        holder.textViewPendidikan.setText(result.getPendidikan());
        holder.textViewNotelp_p.setText(result.getNotelp_p());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textNama_p) TextView textViewNPM;
        @BindView(R.id.textAlamat_p) TextView textViewNama;
        @BindView(R.id.textBidang) TextView textViewKelas;
        @BindView(R.id.textDeskripsi) TextView textViewSesi;
        @BindView(R.id.textGaji) TextView textViewGaji;
        @BindView(R.id.textNotelp_p) TextView textViewNotelp_p;
        @BindView(R.id.textPendidikan) TextView textViewPendidikan;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String npm = textViewNPM.getText().toString();
            String nama = textViewNama.getText().toString();
            String kelas = textViewKelas.getText().toString();
            String sesi = textViewSesi.getText().toString();

        }
    }
}
