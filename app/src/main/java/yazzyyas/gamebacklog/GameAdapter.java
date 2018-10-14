package yazzyyas.gamebacklog;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import yazzyyas.gamebacklog.database.Games;

public class GameAdapter extends RecyclerView.Adapter<GameViewHolder> {

    private List<Game> games;
    final private GameClickListener gameClickListener;

    public interface GameClickListener {
        void gameOnClick (int i);
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder gameViewHolder, int i) {
        Game game = games.get(i);
        gameViewHolder.cardView

    }

    @Override
    public int getItemCount() {
        return games.size();
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

        TextView title = this.cardView.

    }
}
