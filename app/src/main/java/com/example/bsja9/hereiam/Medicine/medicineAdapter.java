package com.example.bsja9.hereiam.Medicine;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bsja9.hereiam.R;

/**
 * Created by bsja9 on 10/04/2018.
 */

public class medicineAdapter extends RecyclerView.Adapter<medicineAdapter.mViewHolder> {
    private Cursor cursor;
    private onMedicineClickListener onMedicineClickListener;

    public  interface onMedicineClickListener {
        void onMedicineClick(dbNew medicine);
    }

    public medicineAdapter(Cursor cursor, onMedicineClickListener onMedicineClickListener){
        this.cursor = cursor;
        this.onMedicineClickListener = onMedicineClickListener;
    }
    @Override

    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_med_new, parent, false);
        return new mViewHolder(itemView);

    }
    @Override

    public void onBindViewHolder(mViewHolder holder, int position) {
        cursor.moveToPosition(position);

        dbNew medicine = new dbNew();
        medicine.setId(cursor.getInt(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_ID)));
        medicine.setName(cursor.getString(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_NAME)));
        medicine.setDose(cursor.getString(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_DOSE)));
        medicine.setTimesPerDay(cursor.getString(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_TIMES)));
        medicine.setComments(cursor.getString(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_COMMENTS)));
        holder.bind(medicine);
    }
    @Override
    public int getItemCount() {
        return (cursor == null ? 0 : cursor.getCount());
    }
    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }
    class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private dbNew medicine;
        private final TextView name;
        private final TextView dose;
        private final TextView time;
        private final TextView comments;

        mViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.med_name);
            dose = view.findViewById(R.id.med_dose);
            time = view.findViewById(R.id.med_days);
            comments = view.findViewById(R.id.med_comments);
            view.setOnClickListener(this);
        }

        public dbNew getMedicine(){return medicine;}

        void bind(final dbNew medicine) {
            this.medicine = medicine;
            name.setText(medicine.getName());
            dose.setText(medicine.getDose());
            time.setText(medicine.getTimesPerDay());
            comments.setText(medicine.getComments());
            }


        @Override
        public void onClick(View view) {
            cursor.moveToPosition(getAdapterPosition());
            dbNew medicine = new dbNew();
            medicine.setId(cursor.getInt(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_ID)));
            medicine.setName(cursor.getString(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_NAME)));
            medicine.setDose(cursor.getString(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_DOSE)));
            medicine.setTimesPerDay(cursor.getString(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_TIMES)));
            medicine.setComments(cursor.getString(cursor.getColumnIndex(dbNewContract.medicineEntry.COLUMN_NAME_COMMENTS)));

            onMedicineClickListener.onMedicineClick(medicine);
        }
    }
    }