package techlab.digital.com.ecommclap.activity.schedule_products;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.ScheduleCartAdapter;
import techlab.digital.com.ecommclap.model.VirtualCartSingleton;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponsetTwo;
import techlab.digital.com.ecommclap.utility.SessionManager;

public class ScheduledCartActivity extends AppCompatActivity implements ScheduleCartAdapter.InterfaceListener {
    List<ProductListingsModeResponsetTwo> my_final_cart_item = new ArrayList<>();
    LinearLayout noCartItem;
    SessionManager sessionManager;
    RecyclerView mRecyclerView;
    TextView placeOrder;
    VirtualCartSingleton tmp = VirtualCartSingleton.getInstance( );
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_cart);
        mRecyclerView = findViewById(R.id.recycler_cart);
        placeOrder = findViewById(R.id.btn_placeorder);
        noCartItem = findViewById(R.id.noCartItem);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ItemsSchedulerActivity.class);
                startActivity(intent);
            }
        });
        my_final_cart_item = tmp.getmFinalList_for_cart();

        setAdapter(my_final_cart_item);
    }

    ScheduleCartAdapter mAdapter;
    public void setAdapter(List<ProductListingsModeResponsetTwo> mFinalList){
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ScheduleCartAdapter(getApplicationContext(), mFinalList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.mCallback = (ScheduleCartAdapter.InterfaceListener) this;
    }
    @Override
    public void onRemoveItemClick(int position,int ids,int size_of_list) {

        if(size_of_list!=0)
        {
            placeOrder.setVisibility(View.VISIBLE);
        }
        else
        {
            placeOrder.setVisibility(View.GONE);
            noCartItem.setVisibility(View.VISIBLE);
        }
        }

    public void removeAt(int position) {
        mAdapter.removeItem(position);
    }

}
