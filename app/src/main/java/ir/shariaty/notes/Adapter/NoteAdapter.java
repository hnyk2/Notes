package ir.shariaty.notes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import ir.shariaty.notes.Model.Note;
import ir.shariaty.notes.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private OnNoteListener mOnNoteListener;

    private Context mContext;
    private List<Note> mNots;

    private FirebaseUser firebaseUser;

    public NoteAdapter(Context mContext, List<Note> mNots,OnNoteListener onNoteListener) {
        this.mContext = mContext;
        this.mNots = mNots;
        this.mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.note_layout, parent, false);
        return new NoteAdapter.ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteAdapter.ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Note note = mNots.get(position);

        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.date.setText(note.getDate());

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show popup menu
                PopupMenu popup = new PopupMenu(mContext,holder.buttonViewOption);
                popup.inflate(R.menu.options_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                //handle edit click
                                break;
                            case R.id.delete:
                                //handle delete click
                                break;
                        }
                        return true;
                    }
                });

                //displaying the popup
                popup.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mNots.size();
    }

    @Override

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

    }

    // Insert a new item to the RecyclerView on a predefined position

    public void insert(int position, Note data) {

        mNots.add(position, data);

        notifyItemInserted(position);

    }

    // Remove a RecyclerView item containing a specified Data object

    public void remove(Note data) {
        int position = mNots.indexOf(data);
        mNots.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnNoteListener onNoteListener;
        public CardView cv;
        public TextView title;
        public TextView description;
        public TextView date;
        public TextView buttonViewOption;

        public ViewHolder(@NonNull View itemView ,OnNoteListener onNoteListener) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.save_title);
            description = (TextView) itemView.findViewById(R.id.save_description);
            date = (TextView) itemView.findViewById(R.id.save_date);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            this.onNoteListener=onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
