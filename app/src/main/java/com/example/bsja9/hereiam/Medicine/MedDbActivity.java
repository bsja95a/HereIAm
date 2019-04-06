package com.example.bsja9.hereiam.Medicine;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.example.bsja9.hereiam.R;

public class MedDbActivity extends AppCompatActivity implements medicineAdapter.onMedicineClickListener {

    private RecyclerView mRecyclerView;
    private medicineAdapter mAdapter;
    private Cursor mCursor;
    private MedicineDataSource meDataSource;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_db);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.medicineListNew);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        meDataSource = new MedicineDataSource(this);
        meDataSource.open();
        updateUI();

        add = findViewById(R.id.addNewMedicine);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_medicine();
            }
        });

    }

    private void updateUI() {
        mCursor = meDataSource.findAll();
        if(mAdapter == null){
            mAdapter = new medicineAdapter(mCursor,this);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.swapCursor(mCursor);
        }
    }

    private void add_medicine() {
        Intent intent = new Intent(this,addMedicine.class);
        intent.setAction(Intent.ACTION_INSERT);
        startActivity(intent);
    }

    @Override
    public void onMedicineClick(dbNew medicine) {
    }
    private ItemTouchHelper.SimpleCallback itemTouchCallback() {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                dbNew medicine = ((medicineAdapter.mViewHolder) viewHolder).getMedicine();
                meDataSource.delete(medicine.getId());
                Cursor cursor = meDataSource.findAll();
                mAdapter.swapCursor(cursor);
                mAdapter.notifyDataSetChanged();
            }
        };}


    protected void onResume() {
        super.onResume();
        meDataSource.open();
        updateUI();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mCursor != null && !mCursor.isClosed()) mCursor.close();
        meDataSource.close();
    }
}
