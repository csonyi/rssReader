package hu.elte.projekteszkozok.rssreader;

import java.util.List;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<String> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public TextView pubDate;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            title =  v.findViewById(R.id.title);
            desc =  v.findViewById(R.id.desc);
            pubDate = v.findViewById(R.id.pubDate);
        }
    }

    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public RecyclerViewAdapter(List<String> myDataset) {
        values = myDataset;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String name = values.get(position);
        holder.title.setText("Title: " + name);
        holder.title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.desc.setText("Desc: " + name);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
