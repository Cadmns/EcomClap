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

import techlab.digital.com.ecommclap.R;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;

@SuppressLint("ValidFragment")
public class AddNewOrRepeatBottomSheet extends BottomSheetDialogFragment {
Button Add_new_btn,Repeat_last;
String option_selected;
onOptionSelected onOptionSelected;
ProductListingsModeResponse data_object;
int position;
String number_quantity;
    @SuppressLint("ValidFragment")
    public AddNewOrRepeatBottomSheet(Context mtx, ProductListingsModeResponse response_object,int mposition,String quantity){
        data_object = response_object;
        position = mposition;
        number_quantity = quantity;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onOptionSelected = (AddNewOrRepeatBottomSheet.onOptionSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }





    @SuppressLint("RestrictedApi")
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_or_repeat_sheet, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Repeat_last = view.findViewById(R.id.Repeat_last);
        Add_new_btn = view.findViewById(R.id.Add_new_btn);

        Repeat_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option_selected = "repeat";
                onOptionSelected.selected_option_is(option_selected,data_object,position,number_quantity);
                dialog.dismiss();
            }
        });
        Add_new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option_selected = "add";
                onOptionSelected.selected_option_is(option_selected,data_object,position,number_quantity);
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




    public interface onOptionSelected {
        public void selected_option_is(String option,ProductListingsModeResponse response_data,int data_position,String quantity);
    }

}
