package haruna.gi.job.sys;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import haruna.gi.job.R;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolder> {
    private Context context;
    private List<Result> results;

    public RecyclerView_Adapter(Context context,List<Result> results){
        this.context = context;
        this.results = results;
    }
    @NonNull
    @Override
    public RecyclerView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pekerjaan, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_Adapter.ViewHolder holder, int position) {
       // Result result = results.get(position);
   //     holder.tnama_p.setText(result.getNama_p());
     //   holder.talamat_p.setText(result.getNama_p());
       /// holder.deskripsi.setText(result.getNama_p());
     //   holder.tgaji.setText(result.getGaji());
       // holder.tnotelp_p.setText(result.getNama_p());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       /* @BindView(R.id.nama_p) TextView tnama_p;
        @BindView(R.id.alamat_p) TextView talamat_p;
        @BindView(R.id.posisi) TextView tposisi;
        @BindView(R.id.deskripsi) TextView deskripsi;
        @BindView(R.id.notelp_p) TextView tnotelp_p;
        @BindView(R.id.gaji) TextView tgaji;
*/
        public ViewHolder(View itemView){
            super(itemView);
           // ButterKnife.bind(this, itemView);
        }
    }
}
