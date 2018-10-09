package yazzyyas.gamebacklog;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;

public class GameAdapter extends RecyclerView.Adapter<GameViewHolder> {
    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder gameViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void swapList (List<Game> newList) {
        games = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}

class GameViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cardView)
    CardView cardView;

    public GameViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
