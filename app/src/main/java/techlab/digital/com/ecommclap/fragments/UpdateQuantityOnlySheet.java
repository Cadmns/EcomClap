package techlab.digital.com.ecommclap.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.adapter.ProductListingsAdapter;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.utility.SessionManager;

@SuppressLint("ValidFragment")
public class UpdateQuantityOnlySheet  extends BottomSheetDialogFragment {
        Spinner mVariations;
        Dialog mdialog;
        Context context;

        onQuantityUpdateOnly onQuantityUpdateOnly;
        /*Buttons to update Quantity Only*/
        Button m_done;
        ElegantNumberButton update_quantity_only_elegent;
        ProductListingsModeResponse data_response;
        String number_quantity;
        int item_position;
        ProductListingsAdapter.ViewHolder _holder;


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                onQuantityUpdateOnly = (onQuantityUpdateOnly)activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
            }
        }

        @SuppressLint("ValidFragment")
        public UpdateQuantityOnlySheet(Context mtx, ProductListingsModeResponse response, String quantity, int position, ProductListingsAdapter.ViewHolder holder){
            context = mtx;
            data_response = response;
            number_quantity = quantity;
            _holder = holder;
            item_position = position;
        }


        @SuppressLint("RestrictedApi")
        public void setupDialog(final Dialog dialog, int style) {
            super.setupDialog(dialog, style);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.update_quantity_only, null);
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(false);
            m_done =  view.findViewById(R.id.m_done_btn);
            update_quantity_only_elegent = view.findViewById(R.id.update_quantity_only_elegent);
            update_quantity_only_elegent.setNumber(number_quantity);



            /*handling buttton event to update quantiyt only*/
            m_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*do sometyhing like callbaqck*/
                    onQuantityUpdateOnly.update_quantity_only(update_quantity_only_elegent.getNumber(),data_response,item_position,_holder);
                    dialog.dismiss();

                }
            });





            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();
            if (behavior != null && behavior instanceof BottomSheetBehavior) {
                ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        String state = "";

                        switch (newState) {
                            case BottomSheetBehavior.STATE_DRAGGING: {
                                state = "DRAGGING";
                                break;
                            }
                            case BottomSheetBehavior.STATE_SETTLING: {
                                state = "SETTLING";
                                break;
                            }
                            case BottomSheetBehavior.STATE_EXPANDED: {
                                state = "EXPANDED";
                                break;
                            }
                            case BottomSheetBehavior.STATE_COLLAPSED: {
                                state = "COLLAPSED";
                                break;
                            }
                            case BottomSheetBehavior.STATE_HIDDEN: {
                                dismiss();
                                state = "HIDDEN";
                                break;
                            }
                        }

//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });
            }
        }


        public interface onQuantityUpdateOnly {
            public void update_quantity_only(String quantity, ProductListingsModeResponse data, int position, ProductListingsAdapter.ViewHolder holder);
        }


}
