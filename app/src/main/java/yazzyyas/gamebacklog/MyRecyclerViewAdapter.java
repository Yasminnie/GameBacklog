package yazzyyas.gamebacklog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Game> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<Game> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = mClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game game = mData.get(position);

        holder.title.setText(game.getTitle());
        holder.platform.setText(game.getPlatform());
        holder.status.setText(game.getNotes());
        holder.date.setText(game.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setmData(List<Game> mData) {
        this.mData = mData;
        notifyDataSetChanged(); // zegt tegen UI dat je items kan updaten in recyclerview
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView platform;
        TextView status;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Title);
            platform = itemView.findViewById(R.id.platform);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                mClickListener.onItemClick(view, getAdapterPosition());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position) throws ExecutionException, InterruptedException;
    }

}
