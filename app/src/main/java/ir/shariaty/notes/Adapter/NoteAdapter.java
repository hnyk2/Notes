package ir.shariaty.notes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.shariaty.notes.Model.Note;
import ir.shariaty.notes.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private static final String TAG = "Id";
    private OnNoteListener mOnNoteListener;

    private Context mContext;
    private List<Note> mNotes;

    public NoteAdapter(Context mContext, List<Note> mNotes,OnNoteListener onNoteListener) {
        this.mContext = mContext;
        this.mNotes = mNotes;
        this.mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.note_layout, parent, false);
        return new NoteAdapter.ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteAdapter.ViewHolder holder, final int position) {

        final Note note = mNotes.get(position);

        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.date.setText(note.getDate());


    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

    }

    // Insert a new item to the RecyclerView on a predefined position

    public void insert(int position, Note data) {

        mNotes.add(position, data);

        notifyItemInserted(position);

    }

    // Remove a RecyclerView item containing a specified Data object

    public void remove(Note data) {
        int position = mNotes.indexOf(data);
        mNotes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mNotes.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnNoteListener onNoteListener;
        public CardView cv;
        public TextView title;
        public TextView description;
        public TextView date;

        public ViewHolder(@NonNull View itemView ,OnNoteListener onNoteListener) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.save_title);
            description = (TextView) itemView.findViewById(R.id.save_description);
            date = (TextView) itemView.findViewById(R.id.save_date);
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
